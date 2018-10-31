# RadTree

Prácticas de Sistemas de Información 2018-19

- Diego Royo Meneses (<740388@unizar.es>)
- Alonso Muñoz García (<745016@unizar.es>)
- Gregorio Largo Mayor (<746621@unizar.es>)

## Pasos para crear un proyecto Eclipse Java EE

Descarga de Eclise Java EE:
https://www.eclipse.org/downloads/packages/release/2018-09/r/eclipse-ide-java-ee-developers

1. Pestaña servers de abajo, elegir Tomcat v9.0 server y para "Server name" RadTree
1. Elegir la carpeta donde esta instalado ApacheTomcat y elegir el JRE (yo elegi 1.8)
1. Finish
1. En la pestaña project explorer, new > dynamic web project
1. Nombre RadTree, next
1. Abajo del todo cambiar default output folder a WEB-INF/classes
1. Finish
1. Borrar la carpeta WebContent creada (con todo su contenido de dentro)
1. Volver a la pestaña servers del paso 1 y click derecho a RadTree > start
1. El servidor deberia aparecer en localhost:8080/RadTree
