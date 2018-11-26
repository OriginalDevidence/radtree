# Documento de despliegue de la aplicación

Servicio elegido: [Amazon AWS](https://aws.amazon.com/es/)

*TODO: En concreto, los servicios S3, EC2...?*

- Petición de cuenta AWS Educate [aquí](https://aws.amazon.com/es/education/awseducate/) con posterior aprobación de Amazon
- A partir de ahí se pueden acceder a los servicios de Amazon:
- 1. Accediendo al portal de estudiantes
- 2. Seleccionar AWS account (aceptar el aviso)
- 3. Seleccionar AWS console en la parte derecha

## Pasos seguidos

### **EC2:** Creación de máquina virtual

1. Selección de servicio EC2 (región US-east N. Virginia, no podía elegir otra)
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

### Instalación de **Apache Tomcat** en la máquina virtual

Seguido el tutorial encontrado en la [siguiente web](https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-8-on-ubuntu-16-04):

1. Instalación de JDK 1.8 **(diferente al del tutorial)**: `sudo apt-get update` y `sudo apt-get install openjdk-8-jdk`
2. Creación del grupo y usuario para Tomcat: `sudo groupadd tomcat` y `sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat`
3. Descarga del servidor Tomcat **(versión 9.0.13, última disponible 26/11/18)**: `cd /tmp` y `curl -O http://ftp.cixug.es/apache/tomcat/tomcat-9/v9.0.13/bin/apache-tomcat-9.0.13.tar.gz`
4. Instalación del servidor: `sudo mkdir /opt/tomcat` y `sudo tar xzvf /tmp/apache-tomcat-9*tar.gz -C /opt/tomcat --strip-components=1`
5. Permisos de acceso para el usuario creado anteriormente:
   - Cambiar a la carpeta de instalación: `cd /opt/tomcat`
   - Cambiar el dueño del directorio de instalación para que sea el grupo `tomcat` creado anteriormente: `sudo chgrp -R tomcat /opt/tomcat`
   - Permisos de acceso al directorio `conf` de Apache: `sudo chmod -R g+r conf` y `sudo chmod g+x conf`
   - Cambiar el dueño de varios directorios de Tomcat al grupo `tomcat`: `sudo chown -R tomcat webapps/ work/ temp/ logs/`
6. Creación de servicio Tomcat de `systemd`:
   - Obtener la carpeta de instalación de Java: `sudo update-java-alternatives -l`
     - Devuelve una tabla donde nos interesa lo siguiente:
        | java-1.8.0-openjdk-amd64 | 1081 | */usr/lib/jvm/java-1.8.0-openjdk-amd64* |
        |---------------------------|------|----------------------------------------|
     - Variable `JAVA_HOME` debería tomar el valor `/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre` (con `/jre` al final), que será usada más adelante
   - Creación del servicio, archivo `tomcat.service` en `/etc/systemd/system` con el siguiente contenido:
        ```
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
7. Configuración de las reglas del firewall para permitir tráfico por el puerto `8080`: `sudo ufw allow 8080`
   - También permitido el tráfico por el puerto `8080` en la configuración de la instancia de la VM (ver arriba)

A partir de aquí, si el servicio está operativo (`sudo systemctl status tomcat`) es posible acceder a la web desde `http://<ip_vm>:8080/docs/` _(Nota: es posible que la IP cambie, es posible que la máquina virtual no esté encendida, etc.)_

**TODO: mirar pasos 7 y 8 de la guía para ver si hace falta hacer algo más**