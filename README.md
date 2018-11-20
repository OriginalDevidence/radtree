# RadTree

Prácticas de Sistemas de Información 2018-19

- Diego Royo Meneses (<740388@unizar.es>)
- Alonso Muñoz García (<745016@unizar.es>)
- Gregorio Largo Mayor (<746621@unizar.es>)

## Mapeo de las páginas

- /
- /registrar
- /iniciar-sesion
- /perfil _(recibe por POST el alias de usuario)_
- /perfil/editar
- /noticias
  - /noticias/ver _(recibe por POST el ID de contenido)_
- /preguntas
  - /preguntas/ver _(recibe por POST el ID de contenido)_
- /retos
  - /retos/ver _(recibe por POST el ID de contenido)_
- /quienes-somos
- /clasificacion
- /gestion-contenido
  - /gestion-contenido/crear-noticia
  - /gestion-contenido/editar-noticia _(recibe por POST el ID de contenido)_
  - /gestion-contenido/crear-pregunta
  - /gestion-contenido/editar-pregunta _(recibe por POST el ID de contenido)_
  - /gestion-contenido/crear-reto
  - /gestion-contenido/editar-reto _(recibe por POST el ID de contenido)_
- /error-interno

## Pasos para crear un proyecto Eclipse Java EE

Descarga de Eclise Java EE:
https://www.eclipse.org/downloads/packages/release/2018-09/r/eclipse-ide-java-ee-developers

1. Elegir como workspace la carpeta "webapps" de la instalación de Apache Tomcat
1. Pestaña servers de abajo, elegir Tomcat v9.0 server y para "Server name" RadTree
1. Elegir la carpeta donde esta instalado ApacheTomcat y elegir el JRE (yo elegi 1.8)
1. Finish
1. En la pestaña project explorer, new > dynamic web project
1. Nombre RadTree, next
1. Abajo del todo cambiar default output folder a WEB-INF/classes
1. Finish
1. Borrar la carpeta WebContent creada (con todo su contenido de dentro)
1. Volver a la pestaña servers del paso 1 y click derecho a RadTree > properties
1. Darle al boton "Switch location" (en "Location" deberia aparecer /Servers/RadTree.server)
1. Apply and close
1. En la pestaña project explorer, doble click a RadTree.server de la carpeta Servers
1. Elegir "Use Tomcat installation" del menu Server Locations
1. Guardar y cerrar la pestaña
1. Volver a la pestaña servers y click derecho a RadTree > start
1. El servidor deberia aparecer en localhost:8080/RadTree
