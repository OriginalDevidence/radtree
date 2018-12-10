# Documento de despliegue de la aplicación

Servicio elegido: [Amazon AWS](https://aws.amazon.com/es/)

En concreto, los servicios EC2 (creación de máquinas virtuales) y RDS (creación de bases de datos relacionales) para la instalación del servidor Tomcat y la base de datos MySQL, respectivamente.

- Petición de cuenta AWS Educate [aquí](https://aws.amazon.com/es/education/awseducate/) con posterior aprobación de Amazon
- A partir de ahí se pueden acceder a los servicios de Amazon:
- 1. Accediendo al portal de estudiantes
- 2. Seleccionar AWS account (aceptar el aviso)
- 3. Seleccionar AWS console en la parte derecha

## Pasos seguidos

Resumen de los pasos seguidos:

1. EC2: Creación de máquina virtual
   - Instalación de Apache Tomcat en la máquina virtual
   - Instalación de la aplicación web en el servidor Tomcat y puesta a punto
1. RDS: Creación de la base de datos MySQL
1. Obtención del dominio radtree.ml

### **EC2:** Creación de máquina virtual

1. Selección de servicio [EC2](https://console.aws.amazon.com/ec2/) (región US-east N. Virginia, no podía elegir otra)
2. Creación de una nueva VM, opciones elegidas:
- **Amazon Machine Image (AMI):** Ubuntu Server 18.04 LTS (HVM), SSD Volume Type - ami-0ac019f4fcb7cb7e6
- **Instance Type:** t2.micro (Variable ECUs, 1 vCPUs, 2.5 GHz, Intel Xeon Family, 1 GiB memory, EBS only)
- **Instance Details:** por defecto
- **Storage:** 8gb general-purpose SSD (gp2)
- **Tags:** Creado tag con clave-valor (Name, RadTree)
- **Security group:** Configurado acceso SSH (puerto 22) solo con la IP de mi equipo, acceso servidor Tomcat (puerto 8080) desde cualquier sitio.
3. Creación de par de claves pública-privada para acceso seguro (almacenado archivo con clave privada .pem)
4. Lanzar la VM

**Nota:** Cada vez que se reinicia la VM su IP cambia, así que hay que consultarla cada vez

- **Conexión con la VM mediante SSH:** `ssh -i <clave_privada>.pem ubuntu@<ip_vm>`
  - Si no es posible conectar hay una web con ayuda [aquí](https://docs.aws.amazon.com/es_es/AWSEC2/latest/UserGuide/AccessingInstancesLinux.html)
- **Envío de archivos:** `scp <origen> <destino>` 
  - **Ejemplo para envíar a la VM:** `scp -i <clave_privada>.pem <archivo_local> [-r (recursivo, para directorios)] ubuntu@<ip_vm>:<directorio_descarga>`
    - Directorios útiles: `/home/ubuntu` (home) o `/opt/tomcat` (carpeta instalación Tomcat)
  - Para descargar archivos de la VM es de la misma forma

#### Instalación de **Apache Tomcat** en la máquina virtual

Seguido el tutorial encontrado en la [siguiente web](https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-8-on-ubuntu-16-04):

1. Instalación de JDK 1.8 **(diferente al del tutorial)**: `sudo apt-get update` y `sudo apt-get install openjdk-8-jdk`
1. Creación del grupo y usuario para Tomcat: `sudo groupadd tomcat` y `sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat`
1. Descarga del servidor Tomcat **(versión 9.0.13, última disponible 26/11/18)**: `cd /tmp` y `curl -O http://ftp.cixug.es/apache/tomcat/tomcat-9/v9.0.13/bin/apache-tomcat-9.0.13.tar.gz`
1. Instalación del servidor: `sudo mkdir /opt/tomcat` y `sudo tar xzvf /tmp/apache-tomcat-9*tar.gz -C /opt/tomcat --strip-components=1`
1. Permisos de acceso para el usuario creado anteriormente:
   - Cambiar a la carpeta de instalación: `cd /opt/tomcat`
   - Cambiar el dueño del directorio de instalación para que sea el grupo `tomcat` creado anteriormente: `sudo chgrp -R tomcat /opt/tomcat`
   - Permisos de acceso al directorio `conf` de Apache: `sudo chmod -R g+r conf` y `sudo chmod g+x conf`
   - Cambiar el dueño de varios directorios de Tomcat al grupo `tomcat`: `sudo chown -R tomcat webapps/ work/ temp/ logs/`
1. Creación de servicio Tomcat de `systemd`:
   - Obtener la carpeta de instalación de Java: `sudo update-java-alternatives -l`
     - Devuelve una tabla donde nos interesa lo siguiente:
     
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
        Environment='CATALINA_OPTS=-Xms128M -Xmx256M -server -XX:+UseParallelGC'
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
      - Modificadas las opciones de memoria `-Xms128M -Xmx256M`
   - Recarga del daemon de systemd: `sudo systemctl daemon-reload`
   - Arranque del servicio creado: `sudo systemctl start tomcat`
   - Comprobación de que el servicio está operativo: `sudo systemctl status tomcat`
   - Cuando se comprueba que el servicio está operativo, hacer que este arranque al iniciar la VM: `sudo systemctl enable tomcat`
1. Configuración de las reglas del firewall para poder acceder al servidor web desde el puerto `80`:
   - Redirección del tráfico del puerto `80` al `8080`: `sudo su` y `iptables -A PREROUTING -t nat -p tcp --dport 80 -j REDIRECT --to-port 8080`
   - También permitido el tráfico por el puerto `80` en la configuración de la instancia de la VM (ver arriba)

   - A partir de aquí, si el servicio está operativo (`sudo systemctl status tomcat`) es posible acceder al servidor desde `http://<ip_vm>:8080`

1. Creados usuarios para las aplicaciones manager-gui y admin-gui: `sudo pico /opt/tomcat/conf/tomcat-users.xml`
    ```xml
    <tomcat-users . . .>
        <user username="admin" password="password" roles="manager-gui,admin-gui"/>
    </tomcat-users>
    ```
1. Permitido acceso a la aplicación de management desde cualquier IP: `sudo nano /opt/tomcat/webapps/manager/META-INF/context.xml` (comentar la restricción de IP)
    ```xml
    <Context antiResourceLocking="false" privileged="true" >
      <!--<Valve className="org.apache.catalina.valves.RemoteAddrValve"
            allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />-->
    </Context>
    ```

A partir de aquí solo falta instalar nuestra aplicación web en Tomcat y deshabilitar el resto de aplicaciones que venían por defecto empleando la aplicación de management

#### Instalación del servidor SMTP Postfix en la máquina virtual

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

#### Instalación de la aplicación web en el servidor Tomcat y puesta a punto

**TODO: explicar**

exportado war, copiadas carpetas menos src y web-inf/classes, modificado login.properties, manager para deshabilitar el resto y mover radtree a root, activar, reinicio del servidor y comprobar que conecta

### **RDS:** Creación de la base de datos MySQL

1. Acceso a la consola de Amazon AWS, selección del servicio [RDS](https://console.aws.amazon.com/rds/)
1. Creación de nueva base de datos, opciones elegidas:
   - **License model:** general-public-license
   - **DB engine:** MySQL community edition 8.0.11
   - **DB instance class:** db.t2.micro - 1 vCPU, 1GiB RAM
   - **Allocated storage:** 20 GiB
   - **DB instance identifier:** radtree
     - _Creado usuario master, consultar para obtener sus credenciales_
   - **VPC:** Por defecto
   - **Public availability:** yes
   - **Database:** Nombre radtree, port 3306, 8.0
   - **Opciones de backup:** 7 dias, sin preferencia, copy tags to snapshots
   - **Log de errores:** sin publicar a la nube (Amazon CloudWatch Logs)
   - **Maintenance:** Auto minor version upgrade
   - **Deletion protection:** yes
1. Una vez creada es posible conectarse, consultar para los detalles

**TODO: usuario root + radtree + configuración puertos**

### Obtención del dominio **radtree.ml**

1. Obtenido a través de la plataforma [freenom](https://www.freenom.com/es/index.html)
1. Petición del domino radtree.ml
1. Uso de DNS para redirección a la IP de la VM creada con EC2
1. Registro para la confirmación del pedido