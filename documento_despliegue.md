# Documento de despliegue de la aplicación

Práctica 4 de Sistemas de Información. Universidad de Zaragoza. 12 de diciembre de 2018.

- Autores:
  - Diego Royo (740388@unizar.es)
  - Alonso Muñoz (745016@unizar.es)
  - Gregorio Largo (746621@unizar.es)

Servicio elegido: [Amazon AWS](https://aws.amazon.com/es/)

En concreto, los servicios EC2 (creación de máquinas virtuales) para la instalación del servidor Tomcat y servidor de correo Postfix y servicio RDS (creación de bases de datos relacionales) para la base de datos MySQL, respectivamente.

- Petición de cuenta AWS Educate [aquí](https://aws.amazon.com/es/education/awseducate/) con posterior aprobación de Amazon
- A partir de ahí se pueden acceder a los servicios de Amazon:
   - 1. Accediendo al portal de estudiantes
   - 2. Seleccionar AWS account (aceptar el aviso)
   - 3. Seleccionar AWS console en la parte derecha

## Pasos seguidos

Resumen de los pasos seguidos:

1. **RDS:** Creación de la base de datos MySQL
1. **EC2:** Creación de máquina virtual
   - 1.1. Instalación de Apache Tomcat en la máquina virtual
   - 1.2. Instalación del servidor SMTP Postfix en la máquina virtual
   - 1.3. Instalación de la aplicación web en el servidor Tomcat y puesta a punto
1. Obtención del dominio radtree.ml

### 1. **RDS:** Creación de la base de datos MySQL

1. Acceso a la consola de Amazon AWS, selección del servicio [RDS](https://console.aws.amazon.com/rds/)
1. Creación de nueva base de datos, opciones elegidas:
   - **License model:** general-public-license
   - **DB engine:** MySQL community edition 8.0.11
   - **DB instance class:** db.t2.micro - 1 vCPU, 1GiB RAM
   - **Allocated storage:** 20 GiB
   - **DB instance identifier:** radtree
   - _Creado usuario master, consultar el fichero **credenciales.txt** proporcionado para ver sus credenciales._
   - **VPC:** Por defecto
   - **Public availability:** yes
   - **Database:** Nombre radtree, port 3306, 8.0
   - **Opciones de backup:** 7 dias, sin preferencia, copy tags to snapshots
   - **Log de errores:** sin publicar a la nube (Amazon CloudWatch Logs)
   - **Maintenance:** Auto minor version upgrade
   - **Deletion protection:** yes
1. Configurado el firewall para permitir todo el tráfico por el puerto 3306 para esta instancia.
1. Una vez creada la instancia, es posible acceder a ella mediante herramientas como, por ejemplo, DBeaver.
1. Posteriormente, se han creado las tablas en la base de datos empleando el código SQL existente en el Anexo I. Además, se han insertado varios datos de prueba empleando el código SQL existente en el Anexo II.

Con estos pasos la base de datos queda totalmente operativa. Más adelante se explica cómo configurar la aplicación web para que realice la conexión la capa de datos.

### 2. **EC2:** Creación de máquina virtual

1. Selección de servicio [EC2](https://console.aws.amazon.com/ec2/) (región US-east N. Virginia, ya que no es posible elegir otra con el plan de estudiante)
2. Creación de una nueva VM, opciones elegidas:
    - **Amazon Machine Image (AMI):** Ubuntu Server 18.04 LTS (HVM), SSD Volume Type - ami-0ac019f4fcb7cb7e6
    - **Instance Type:** t2.micro (Variable ECUs, 1 vCPUs, 2.5 GHz, Intel Xeon Family, 1 GiB memory, EBS only)
    - **Instance Details:** por defecto
    - **Storage:** 8gb general-purpose SSD (gp2)
    - **Tags:** Creado tag con clave-valor (Name, RadTree)
    - **Security group:** Configurado el firewall para la instancia de la VM, ver configuración más adelante (paso 7 del apartado 1.1)
3. Creación de par de claves pública-privada para acceso seguro (almacenado archivo con clave privada .pem). La clave pública crea almacenada automáticamente en la máquina virtual.
4. A partir de aquí es posible lanzar la máquina virtual accediendo a la consola de Amazon.

- **Conexión con la VM mediante SSH:** `ssh -i <clave_privada>.pem ubuntu@<ip_vm>`
  - Si no es posible conectar existe una web con ayuda [aquí](https://docs.aws.amazon.com/es_es/AWSEC2/latest/UserGuide/AccessingInstancesLinux.html)
- **Envío de archivos:** `scp -i <clave_privada>.pem <origen> <destino>` 
  - **Ejemplo de envío a la VM:** `scp -i <clave_privada>.pem  [-r] <fichero_local> ubuntu@<ip_vm>:<directorio_descarga>`
  - Directorios útiles: `/home/ubuntu` (home) o `/opt/tomcat` (carpeta instalación Tomcat)

#### 2.1. Instalación de **Apache Tomcat** en la máquina virtual

Seguido el tutorial encontrado en la [siguiente web](https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-8-on-ubuntu-16-04):

1. Instalación de JDK 1.8 **(diferente al del tutorial)**: `sudo apt-get update` y `sudo apt-get install openjdk-8-jdk`
1. Creación del grupo y usuario para Tomcat: `sudo groupadd tomcat` y `sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat`
1. Descarga del servidor Tomcat **(versión 9.0.13, última disponible el 26/11/18)**: `cd /tmp` y `curl -O http://ftp.cixug.es/apache/tomcat/tomcat-9/v9.0.13/bin/apache-tomcat-9.0.13.tar.gz`
1. Instalación del servidor: `sudo mkdir /opt/tomcat` y `sudo tar xzvf /tmp/apache-tomcat-9*tar.gz -C /opt/tomcat --strip-components=1`
1. Permisos de acceso para el usuario creado anteriormente:
   - Cambiar a la carpeta de instalación: `cd /opt/tomcat`
   - Cambiar el dueño del directorio de instalación para que sea el grupo `tomcat` creado anteriormente: `sudo chgrp -R tomcat /opt/tomcat`
   - Permisos de acceso al directorio `conf` de Apache: `sudo chmod -R g+r conf` y `sudo chmod g+x conf`
   - Cambiar el dueño de varios directorios de Tomcat al grupo `tomcat`: `sudo chown -R tomcat webapps/ work/ temp/ logs/`
1. Creación de servicio Tomcat de `systemd`:
   - Obtener la carpeta de instalación de Java: `sudo update-java-alternatives -l`
     - Devuelve una tabla donde interesa el siguiente valor:
       - java-1.8.0-openjdk-amd64 1081 _**/usr/lib/jvm/java-1.8.0-openjdk-amd64**_
     - Variable `JAVA_HOME` debería tomar el valor `/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre` (con `/jre` al final), que será usada más adelante
   - Creación del servicio, archivo `tomcat.service` en `/etc/systemd/system` con el siguiente contenido:
        ```bash
        [Unit]
        Description=Apache Tomcat Web Application Container
        After=network.target

        [Service]
        Type=forking

        Environment=JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre
        Environment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid
        Environment=CATALINA_HOME=/opt/tomcat
        Environment=CATALINA_BASE=/opt/tomcat
        Environment='CATALINA_OPTS=-Xms1024M -Xmx2048M -server -XX:+UseParallelGC'
        Environment='JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'

        ExecStart=/opt/tomcat/bin/startup.sh
        ExecStop=/opt/tomcat/bin/shutdown.sh

        User=tomcat
        Group=tomcat
        UMask=0007
        RestartSec=10
        Restart=always

        [Install]
        WantedBy=multi-user.target
        ```
      - Modificada la variable `JAVA_HOME` con el valor especificado anteriormente
      - Modificadas las opciones de memoria `-Xms1024M -Xmx2048M`
   - Recarga del daemon de systemd: `sudo systemctl daemon-reload`
   - Arranque del servicio creado: `sudo systemctl start tomcat`
   - Comprobación de que el servicio está operativo: `sudo systemctl status tomcat`
   - Cuando se comprueba que el servicio está operativo, hacer que este arranque al iniciar la VM: `sudo systemctl enable tomcat`

1. Configuración de las reglas del firewall:
   - Todos los puertos abiertos, a excepción de:
     - Puerto 22 (SSH)
     - Puerto 25 (SMTP)
     - Puerto 80 (HTTP)
     - Puerto 3306 (MySQL)
     - Todo el tráfico ICMP (para poder hacer `ping` y comprobar que está viva) 
   - Accedeso al servidor web desde el puerto `80`:
     - Redirección del tráfico del puerto `80` al `8080`: `sudo su` y `iptables -A PREROUTING -t nat -p tcp --dport 80 -j REDIRECT --to-port 8080`
     - También permitido el tráfico por el puerto `80` en la configuración de la instancia de la VM (ver arriba)
     - A partir de aquí, si el servicio está operativo (`sudo systemctl status tomcat`) es posible acceder al servidor desde `http://<ip_vm>`

1. Creados usuarios para las aplicaciones manager-gui y admin-gui: `sudo pico /opt/tomcat/conf/tomcat-users.xml`
    ```xml
    <tomcat-users . . .>
        <user username="<user>" password="<password>" roles="manager-gui,admin-gui"/>
    </tomcat-users>
    ```
    - _Nota: Las credenciales se encuentran en el fichero entregado **credenciales.txt**_.
    - De esta forma será posible acceder con estos credenciales a la aplicación de management de Tomcat (`http://radtree.ml/manager/html`)
1. Permitido acceso a la aplicación de management desde cualquier IP: `sudo nano /opt/tomcat/webapps/manager/META-INF/context.xml` (comentar la restricción de IP)

    ```xml
    <Context antiResourceLocking="false" privileged="true" >
      <!--<Valve className="org.apache.catalina.valves.RemoteAddrValve"
            allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />-->
    </Context>
    ```

A partir de aquí solo falta instalar nuestra aplicación web en Tomcat y deshabilitar el resto de aplicaciones que venían por defecto empleando la aplicación de management.

#### 2.2. Instalación del servidor SMTP Postfix en la máquina virtual

Preparación de la máquina virtual según el [siguiente tutorial](https://elprespufferfish.net/blog/aws,mail/2015/09/03/mail-server-ec2.html):

1. Editado fichero /etc/hosts para que el host conozca su nombre: radtree.ml

Seguido el tutorial encontrado en la [siguiente web](https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-postfix-as-a-send-only-smtp-server-on-ubuntu-14-04):

1. Instalación de Postfix: `sudo apt-get install mailutils`
   - Elegida configuración por defecto *Internet Site*.
   - Para System Mail name se ha utilizado la dirección del sitio web: radtree.ml
1. Configuración de Postfix: `sudo nano /etc/postfix/main.cf`
   - Modificada configuración para solamente enviar emails desde el servidos en el que está funcionando:
   ```
   inet_interfaces = localhost
   ```
   - Reinicio del servicio Postfix: `sudo service postfix restart`

Con esto la VM queda preparada para poder enviar emails al ejecutar un servlet del código Java, si se disponen de las bibliotecas `mail.jar` y `activation.jar` proporcionadas.

#### 2.3. Instalación de la aplicación web en el servidor Tomcat y puesta a punto

1. Subida de todos los archivos a través del comando `scp` mencionado anteriormente:
    - Archivo .war de la aplicación: Colocado en `/opt/tomcat/webapps/ROOT.war`.
    - Carpetas `/jsp`, `/images`, `/common`, `/plugin-frameworks` y `/WEB-INF/lib` de la aplicación, que contienen los ficheros esenciales para el correcto funcionamiento, además de las bibliotecas empleadas por esta. Temporalmente colocados en `/home/ubuntu`.
1. Arranque del servidor Tomcat. De esta forma, al detectar el fichero `ROOT.war` crea la aplicación web `webapps/ROOT`. Posteriormente se pueden copiar todas las carpetas mencionadas en el paso 1 a la carpeta `webapps/ROOT` que acaba de ser creada.
1. Cambiado el dueño de todos los ficheros de esta carpeta al usuario `tomcat`: `cd /opt/tomcat/webapps/ROOT` y `sudo chown -R tomcat *`
1. Conexión con la base de datos: creado un fichero con nombre `login.properties` en el directorio `WEB-INF/classes/sistinfo/capadatos/jdbc` con el siguiente contenido:
  ```
  db_url=jdbc:mysql://<url_base_datos>/radtree?useSSL=false
  user=<usuario>
  password=<password>
  ```

  _Nota: estos datos están incluidos en el fichero **credenciales.txt** entregado._

5. Inicio de sesión en la aplicación manager de Tomcat: `http://<ip_vm>/manager/html`. Se han desactivado todas las aplicaciones menos la instalada anteriormente.
6. Reinicio del servidor Tomcat: `sudo systemctl restart tomcat`

Con esto el servidor web queda totalmente operativo y solamente falta obtener el servicio DNS. Para iniciar sesión como usuario administrador en la aplicación web se puede consultar el documento de primeros pasos incluido, y sus credenciales también están disponibles en el fichero **credenciales.txt** entregado.

### 3. Obtención del dominio **radtree.ml**

1. Obtención a través de la plataforma [freenom](https://www.freenom.com/es/index.html)
1. Petición del domino radtree.ml
1. Uso de DNS para redirección a la IP de la VM creada con EC2
1. Registro para la confirmación del pedido

Con todos estos pasos ya es posible acceder a la aplicación a través del enlace http://radtree.ml

## Anexo I: Creación de la base de datos

Para la creación de tablas se ha empleado el siguiente código SQL:

```SQL
/* Crear BD y seleccionarla */
CREATE DATABASE IF NOT EXISTS radtree CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE radtree;

/* Creación de tablas */
CREATE TABLE IF NOT EXISTS Usuario (
  idUsuario           BIGINT        PRIMARY KEY AUTO_INCREMENT,
  alias               VARCHAR(20)   UNIQUE NOT NULL,
  nombre              VARCHAR(50)   NOT NULL,
  apellidos           VARCHAR(100)  NOT NULL,
  fechaNacimiento     DATE          NOT NULL,
  /* La longitud máxima de un email válido es de 254 caracteres */
  email               VARCHAR(254)  UNIQUE NOT NULL,
  emailVerificado     BOOLEAN       NOT NULL,
  /* Hash de la contraseña (PBKDF2), 256 bits */
  passwordHash        BLOB          NOT NULL,
  tipoUsuario         ENUM ('ADMINISTRADOR', 'CREADOR', 'PARTICIPANTE')
                                    NOT NULL,
  /* No guardamos la foto de perfil ya que no se almacena en la base de datos y podemos acceder con el idUsuario */
  puntuacion          DOUBLE        NOT NULL
);

/* Token de verificación de correo electrónico */
CREATE TABLE IF NOT EXISTS Token (
  token               VARCHAR(255)  PRIMARY KEY NOT NULL,
  idUsuario           BIGINT        UNIQUE NOT NULL,
  FOREIGN KEY(idUsuario) REFERENCES Usuario(idUsuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Contenido (
  idContenido         BIGINT        PRIMARY KEY AUTO_INCREMENT,
  idAutor             BIGINT        NOT NULL,
  numVisitas          BIGINT        NOT NULL,
  fechaRealizacion    DATETIME      NOT NULL,
  estado              ENUM('PENDIENTE', 'VALIDADO', 'BORRADO')
                                    NOT NULL,
  FOREIGN KEY(idAutor) REFERENCES Usuario(idUsuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Noticia (
  idContenido         BIGINT        PRIMARY KEY,
  titulo              VARCHAR(200)  NOT NULL,
  cuerpo              VARCHAR(2000) NOT NULL,
  url                 VARCHAR(200)  NOT NULL,
  /* No guardamos la imagen ya que se no se guarda en la base de datos y se puede acceder a ella con el idContenido */
  FOREIGN KEY(idContenido) REFERENCES Contenido(idContenido) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Reto (
  idContenido         BIGINT        PRIMARY KEY,
  titulo              VARCHAR(100)  NOT NULL,
  cuerpo              VARCHAR(500)  NOT NULL,
  FOREIGN KEY(idContenido) REFERENCES Contenido(idContenido) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Pregunta (
  idContenido         BIGINT        PRIMARY KEY,
  enunciado           VARCHAR(200)  NOT NULL,
  FOREIGN KEY(idContenido) REFERENCES Contenido(idContenido) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Respuesta (
  idRespuesta         BIGINT        PRIMARY KEY AUTO_INCREMENT,
  idPregunta		      BIGINT        NOT NULL,
  enunciado           VARCHAR(200)  NOT NULL,
  correcta            BOOLEAN       NOT NULL,
  FOREIGN KEY(idPregunta) REFERENCES Pregunta(idContenido) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Comentario (
  idComentario        BIGINT        PRIMARY KEY AUTO_INCREMENT,
  idAutor             BIGINT        NOT NULL,
  idContenido         BIGINT        NOT NULL,
  cuerpo              VARCHAR(300)  NOT NULL,
  fecha               DATETIME      NOT NULL,
  respuestaDe         BIGINT,
  FOREIGN KEY(idAutor) REFERENCES Usuario(idUsuario) ON DELETE CASCADE,
  FOREIGN KEY(idContenido) REFERENCES Contenido(idContenido) ON DELETE CASCADE,
  FOREIGN KEY(respuestaDe) REFERENCES Comentario(idComentario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Contesta (
  idUsuario           BIGINT,
  idRespuesta         BIGINT,
  respuesta           BOOLEAN       NOT NULL,
  PRIMARY KEY(idUsuario, idRespuesta),
  FOREIGN KEY(idUsuario) REFERENCES Usuario(idUsuario) ON DELETE CASCADE,
  FOREIGN KEY(idRespuesta) REFERENCES Respuesta(idRespuesta) ON DELETE CASCADE
);
```

## Anexo II: Inserción de datos de prueba

Se han insertado unos datos mínimos de prueba para poder comprobar rápidamente el correcto funcionamiento de la base:

```SQL
USE radtree;

INSERT INTO Usuario VALUES
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'jperez@gmail.com', FALSE, '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'ElArbolCaido', 'Isidoro', 'Chavez Linares', '1945-02-023', 'isidoro45@hotmail.com', FALSE, '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'GreenGlobal', 'Francisco Miguel', 'García Calvo', '1998-07-08', 'franCalvo@yahoo.es', TRUE, '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'CREADOR', 0.0),
(NULL, 'Ramita64', 'Alejandro', 'Martinez Ruiz ', '1961-05-25', 'Alex_Ruiz@hotmail.com', FALSE, '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'LaRataDelReciclaje', 'Sergio', 'Ruperez Torres', '1980-06-28', 'sergioTorres@gmail.com', FALSE, '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'AlonsoElVerde', 'Fernando', 'Alonso Díaz', '1975-03-30', 'ferAlonso@yahoo.es', TRUE, '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'ADMINISTRADOR', 0.0),
(NULL, 'ElInsectoEcologico', 'Victor', 'Pérez Peñasco', '1999-01-25', 'victor1999@unizar.es', TRUE, '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'CREADOR', 0.0);

INSERT INTO Contenido VALUES
(NULL, 1, 0, '2018-01-01', 'VALIDADO'),
(NULL, 2, 0, '2018-01-02', 'VALIDADO'),
(NULL, 1, 0, '2018-01-03', 'VALIDADO'),
(NULL, 3, 0, '2018-01-04', 'VALIDADO'),
(NULL, 4, 0, '2018-01-05', 'VALIDADO'),
(NULL, 1, 0, '2018-01-01', 'VALIDADO'),
(NULL, 2, 0, '2018-01-02', 'VALIDADO'),
(NULL, 1, 0, '2018-01-03', 'VALIDADO'),
(NULL, 3, 0, '2018-01-04', 'VALIDADO'),
(NULL, 4, 0, '2018-01-05', 'VALIDADO'),
(NULL, 2, 0, '2018-01-06', 'VALIDADO');

INSERT INTO Noticia VALUES
(1, 'Los bosques secundarios tienen vidas cortas', 'Los bosques secundarios solo duran un promedio de 20 años. El hallazgo presenta un problema importante para la política de restauración a gran escala, que a menudo se enfoca en compromisos para restaurar un cierto número de hectáreas para un año determinado. Pero los beneficios de la restauración dependen de los bosques que persisten. Según muestra una investigación, se tarda mucho más de 20 años para que un bosque secundario absorba grandes cantidades de carbono o proporcione un hábitat para muchas especies forestales.', 'https://www.sciencedaily.com/releases/2018/10/181002102900.htm'),
(2, 'Aragón, la región más afectada por la construcción de embalses que están paralizados', 'Según denuncia Ecologistas en Acción en su último informe, la Comunidad es puntera tanto en "luchas" como en proyectos que se han hecho y carecen de utilidad hoy. Biscarrués, Yesa y Almudévar son algunos ejemplos de estas guerras que siguen abiertas.
  Una problemática que se extiende a lo largo de toda la geografía española y en la que, según denuncia Ecologistas en Acción, la Comunidad "es puntera tanto en luchas como en proyectos que se han hecho y carecen de utilidad" hoy. ', 'https://www.heraldo.es/noticias/aragon/2018/10/03/aragon-region-mas-afectada-por-construccion-embalses-que-estan-paralizados-1269480-300.html'),
(3, 'El aire limpio es solo para los ricos', 'En cada ciudad, los más bajos niveles de renta se suelen concentrar en ciertos barrios, no necesariamente alejados del centro. Así, en Madrid, en la zona sur viven más familias de reducido nivel socioeconómico que en la zona norte. En París, la división no responde al eje norte-sur, sino este-oeste.
Una razón de peso para construir viviendas más caras y proyectar hermosas urbanizaciones es la calidad del aire, determinada en buena parte por la dirección de los vientos dominantes. Y así es como se diseñan las ciudades. En Madrid, al norte, más cerca de la sierra, se concentran las grandes rentas, mientras que en el sur, la industria, el tráfico y la gran densidad de población
 sumen a algunos barrios periféricos en una atmósfera irrespirable. ', 'https://elpais.com/sociedad/2018/12/04/actualidad/1543943928_728516.html');

INSERT INTO Reto VALUES
(4, '¿Serías capaz de mejorar el aire de tu ciudad?', 'Te proponemos un reto que consiste en que durante una semana acudas en transporte público,
andando, en bici o otro tipo de transporte electrico, a tu lugar de trabajo o a clase. De esta forma no solo mejoraras el aire de tu ciudad si no que tambien contribuirás a tu salud física.'),
(5, 'Reciclar es necesario', 'Debes de pensar que tu aportación al planeta con esta actividad es mínima, pero de esta forma se obtienen una gran cantidad de materias primas. Serías
  capaz de aplicar estas medidas en tu hogar o en tu lugar de trabajo, y de esta forma contribuir en el planeta.'),
(6, 'Deberías colaborar para limpiar el aire que respiras.', 'Cada vez esta mas contaminado el aire que se respira principalmente en las ciudades. La contaminación del aire nos convierte en fumadores pasivos de forma constante.
  Para colaborar en la solución a este gran problema se te plantea el reto de evitar las emisiones a tu entorno, utilizar el transporte publico o los vehiculos ecologicos es una gran ayuda para mejorar el entorno.');

INSERT INTO Pregunta VALUES
(7, '¿Cuántos árboles se deforestan por minuto en el amazonas?'),
(8, '¿Cuántas toneladas de residuos se pierden por un mal reciclado en un año en España?'),
(9, '¿Cuanto tarda en degradarse una toallita de papel en el mar?'),
(10, '¿Cuántas toneladas de residuos plasticos flotan a la deriva por nuestros mares?'),
(11, '¿Cuales de estos productos se degradan en menos de 8 semanas?');

INSERT INTO Respuesta VALUES
(NULL, 7, 'Cada minuto se deforesta media hectárea, y se repuebla el doble de lo deforestado.', FALSE),
(NULL, 7, 'Cada minuto se deforestan diez hectáreas.', TRUE),
(NULL, 7, 'El mismo número de árboles que se platan en españa en todo un mes.', FALSE),
(NULL, 7, 'Tantos árboles como se necesitaban para costruir un galeón.', FALSE),
(NULL, 7, 'Cada minuto se deforestan el equivalente en superficie a 25 campos de fútbol.', FALSE),
(NULL, 8, 'El equivalente en peso a 100 barcos de carga.', FALSE),
(NULL, 8, 'El 45 % de toneladas de las que llegan a reciclarse de forma correcta.', TRUE),
(NULL, 8, 'Unas 1000 toneladas al mes, lo que equivale a 12000 al año.', FALSE),
(NULL, 8, 'El 10 % de toneladas de las que llegan a reciclarse de forma correcta.', FALSE),
(NULL, 8, 'El equivalente en toneladas de cereal consumidos en un año en españa.', FALSE),
(NULL, 9, 'Unas 14 horas es el tiempo que tarda en degradarse una toallita de papel.', FALSE),
(NULL, 9, 'De tres a cuatro semanas es el tiempo que tarda en degradarse.', TRUE),
(NULL, 9, 'En torno a unos cincuenta días si el agua es salada y unos cuarenta en agua dulce.', FALSE),
(NULL, 9, 'Son unos 200 años el tiempo que necesita una toallita de papel.', TRUE),
(NULL, 10, 'En torno a 20.000 toneladas.', FALSE),
(NULL, 10, 'En total son 135.000 toneladas, de las cuales el 70% proceden de los países más desarrollados.', FALSE),
(NULL, 10, 'En torno a 100.000 toneladas, de las cuales la mayoría son bolsas de plástico.', FALSE),
(NULL, 10, 'El peso de la estatua de la la libertad, unas 27.500 toneladas.', FALSE),
(NULL, 10, 'Unas 275.000 toneladas, compuestas por todo tipo de emboltorios platicos.', TRUE),
(NULL, 10, 'Son 350.000 toneladas de plasticos repartidas en 5.000.000 de metros cuadrados.', FALSE),
(NULL, 10, 'En torno a 45.000 toneladas.', FALSE),
(NULL, 10, '190.000 toneladas, que causan 35% de las muertes de la fauna maritima.', FALSE),
(NULL, 10, 'En torno a 220.000 toneladas.', FALSE),
(NULL, 10, 'Un millón de toneladas de planticos, que flotan a la deriva en pequeños trozos.', FALSE),
(NULL, 11, 'Toallitas de papel.', TRUE),
(NULL, 11, 'Un cartón de leche.', FALSE),
(NULL, 11, 'Un trozo de cuerda', FALSE),
(NULL, 11, 'Un periódico.', TRUE),
(NULL, 11, 'Un rollo de papel higiénico.', TRUE),
(NULL, 11, 'La peladura de un plátano.', FALSE),
(NULL, 11, 'Un folio de papel.', FALSE),
(NULL, 11, 'Una colilla de tabaco.', FALSE),
(NULL, 11, 'Una cáscara de pipa.', FALSE),
(NULL, 11, 'Un pañuelo.', TRUE),
(NULL, 11, 'Una bolsa de plástico.', FALSE),
(NULL, 11, 'El hilo del sedal de pesca.', FALSE);
```