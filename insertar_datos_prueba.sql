USE sist_info_web;

/* Todos los usuarios tienen como contraseña "clave" */
INSERT INTO Usuario VALUES
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'hola@email.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'hola@email.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'hola@email.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'hola@email.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'hola@email.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'hola@email.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0),
(NULL, 'JuanEcologico28', 'Juan', 'Pérez Peñasco', '2000-01-01', 'hola@email.com', '$31$1000$AAAAAAAAAAAAAAAAAAAAAGLPiriGp7lXdLflFuOuAyk', 'PARTICIPANTE', 0.0);

INSERT INTO Contenido VALUES
(NULL, 1, 0, '2000-01-01', 'PENDIENTE'),
(NULL, 2, 0, '2000-01-02', 'PENDIENTE'),
(NULL, 1, 0, '2000-01-03', 'PENDIENTE'),
(NULL, 3, 0, '2000-01-04', 'PENDIENTE'),
(NULL, 4, 0, '2000-01-05', 'PENDIENTE'),
(NULL, 2, 0, '2000-01-06', 'PENDIENTE');

INSERT INTO Noticia VALUES
(1, 'Titulo', 'Cuerpo', 'url'),
(2, 'Titulo', 'Cuerpo', 'url');

INSERT INTO Reto VALUES
(3, 'Titulo', 'Cuerpo'),
(4, 'Titulo', 'Cuerpo');

INSERT INTO Pregunta VALUES
(5, 'enunciado'),
(6, 'enunciado');

/* 5 respuestas para la priemra pregunta y 5 respuestas para la segunda */
INSERT INTO Respuesta VALUES
(NULL, 5, 'enunciado', FALSE),
(NULL, 5, 'enunciado', TRUE),
(NULL, 5, 'enunciado', FALSE),
(NULL, 5, 'enunciado', FALSE),
(NULL, 5, 'enunciado', FALSE),
(NULL, 6, 'enunciado', FALSE),
(NULL, 6, 'enunciado', TRUE),
(NULL, 6, 'enunciado', FALSE),
(NULL, 6, 'enunciado', FALSE),
(NULL, 6, 'enunciado', FALSE);

/* modificar cuerpo solamente, el resto ya esta */
INSERT INTO Comentario VALUES
(NULL, 1, 1, 'cuerpo', 0, '2000-02-01', NULL),
(NULL, 2, 1, 'cuerpo', 0, '2000-02-02', 1),
(NULL, 3, 1, 'cuerpo', 0, '2000-02-03', 2),
(NULL, 2, 1, 'cuerpo', 0, '2000-02-04', NULL),
(NULL, 3, 1, 'cuerpo', 0, '2000-02-05', NULL),
(NULL, 1, 2, 'cuerpo', 0, '2000-02-06', NULL),
(NULL, 1, 2, 'cuerpo', 0, '2000-02-07', NULL),
(NULL, 2, 2, 'cuerpo', 0, '2000-02-08', 1);

/* no hace falta cambiar nada aqui */
INSERT INTO Contesta VALUES
(1, 1, FALSE),
(1, 2, FALSE),
(1, 3, FALSE),
(1, 4, FALSE),
(1, 5, FALSE),
(1, 6, FALSE),
(1, 7, FALSE),
(1, 8, FALSE),
(1, 9, FALSE),
(1, 10, FALSE),
(2, 1, TRUE),
(2, 2, TRUE),
(2, 3, TRUE),
(2, 4, TRUE),
(2, 5, TRUE),
(2, 6, TRUE),
(2, 7, TRUE),
(2, 8, TRUE),
(2, 9, TRUE),
(2, 10, TRUE);