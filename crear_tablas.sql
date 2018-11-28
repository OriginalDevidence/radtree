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
  /* Hash de la contraseña (PBKDF2), 256 bits */
  passwordHash        BLOB          NOT NULL,
  tipoUsuario         ENUM ('ADMINISTRADOR', 'CREADOR', 'PARTICIPANTE')
                                    NOT NULL,
  /* No guardamos la foto de perfil ya que no se almacena en la base de datos y podemos acceder con el idUsuario */
  puntuacion          DOUBLE        NOT NULL
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