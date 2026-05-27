CREATE DATABASE IF NOT EXISTS star_critic;
USE star_critic;



-- ENTIDAD SUPERTIPO USUARIO_REGISTRADO
CREATE TABLE usuario_registrado (
    ID_usuario_registrado INT AUTO_INCREMENT,
    nombre_usuario        VARCHAR(200) NOT NULL,
    correo_electronico    VARCHAR(254) NOT NULL,
    fecha_creacion        DATE         NOT NULL,
    nombre                VARCHAR(50)  NOT NULL,
    apellido1             VARCHAR(50)  NOT NULL,
    apellido2             VARCHAR(50)  DEFAULT NULL,
    rol                   ENUM('ADMINISTRADOR','ESTANDAR') NOT NULL,
    contrasenha           VARCHAR(60)  NOT NULL,
    baneado               BOOLEAN      NOT NULL,   
    PRIMARY KEY (ID_usuario_registrado)
) ENGINE=InnoDB;
-- ENTIDAD SUBTIPO CRITICO
CREATE TABLE critico(
    ID_critico            INT,
    certificacion         VARCHAR(512) NOT NULL,
    estado_certificacion  ENUM('NO_SOLICITADA', 'PENDIENTE','ACEPTADA','RECHAZADA'),
    PRIMARY KEY (ID_critico)
) ENGINE=InnoDB;
-- ENTIDAD LISTA
CREATE TABLE lista_usuario (
    ID_usuario_registrado INT,
    nombre_lista          VARCHAR(200),
    fecha_creacion        DATE NOT NULL,
    PRIMARY KEY (ID_usuario_registrado, nombre_lista)
) ENGINE=InnoDB;
 
-- ENTIDAD SUPERTIPO CONTENIDO
-- origen: OMDB/RAWG (catalogo externo) o LOCAL (contenido propio gestionado por admin)
-- destacado/oculto: flags de gestion editorial del administrador
CREATE TABLE contenido(
    ID_contenido    INT AUTO_INCREMENT,
    fecha           DATE NOT NULL,
    origen          ENUM('OMDB','RAWG','LOCAL') NOT NULL DEFAULT 'LOCAL',
    destacado       BOOLEAN      NOT NULL DEFAULT FALSE,
    oculto          BOOLEAN      NOT NULL DEFAULT FALSE,
    titulo          VARCHAR(255) NOT NULL,
    sinopsis        VARCHAR(3000)NOT NULL,
    poster_key      VARCHAR(255) DEFAULT NULL,
    tipo_contenido  ENUM('PELICULA','SERIE','VIDEOJUEGO') NOT NULL,
    PRIMARY KEY (ID_contenido)
) ENGINE=InnoDB;

-- ENTIDAD SUBTIPO (contenido) CONTENIDO AUDIOVISUAL
-- ID_Api permite NULL para contenido LOCAL (no esta en OMDb)
CREATE TABLE contenido_audiovisual (
    ID_contenido_audiovisual INT,
    ID_Api                   VARCHAR(12) UNIQUE,
    PRIMARY KEY (ID_contenido_audiovisual)
) ENGINE=InnoDB;

-- ENTIDAD SUBTIPO VIDEOJUEGO
-- ID_Api permite NULL para contenido LOCAL (no esta en RAWG)
CREATE TABLE videojuego(
    ID_videojuego INT,
    ID_Api        INT UNIQUE,
    PRIMARY KEY (ID_videojuego)
) ENGINE=InnoDB;

-- ENTIDAD ETIQUETA EDITORIAL (curada por admin)
CREATE TABLE etiqueta_editorial (
    ID_etiqueta INT AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID_etiqueta),
    CONSTRAINT uq_etiqueta_nombre UNIQUE (nombre)
) ENGINE=InnoDB;

-- RELACION N:M entre contenido y etiqueta editorial
CREATE TABLE contenido_etiqueta (
    ID_contenido INT,
    ID_etiqueta  INT,
    PRIMARY KEY (ID_contenido, ID_etiqueta)
) ENGINE=InnoDB;
 
-- RELACIÓN Visita ENTRE USUARIO_REGISTRADO y CONTENIDO
CREATE TABLE contenido_usuario (
    ID_usuario_registrado INT,
    ID_contenido          INT,
    fecha_visita          DATE NOT NULL,
    num_visitas           INT  NOT NULL DEFAULT 1,
    PRIMARY KEY (ID_usuario_registrado, ID_contenido),
    CONSTRAINT chk_contenido_usuario_num_visitas
        CHECK (num_visitas >= 0)
) ENGINE=InnoDB;
 
-- RELACIÓN Posee ENTRE LISTA y CONTENIDO
CREATE TABLE lista_contenido(
    ID_usuario_registrado INT,
    nombre_lista          VARCHAR(200),
    ID_contenido          INT,
    PRIMARY KEY (ID_usuario_registrado, nombre_lista, ID_contenido)
) ENGINE=InnoDB;
 
-- ENTIDAD SUPERTIPO CRITICA
CREATE TABLE critica(
    ID_critica            INT AUTO_INCREMENT,
    puntuacion            INT           NOT NULL,
    descripcion           VARCHAR(3000) NOT NULL,
    ID_usuario_registrado INT           NOT NULL,
    PRIMARY KEY (ID_critica),
    CONSTRAINT chk_critica_puntuacion
        CHECK (puntuacion BETWEEN 0 AND 100)
) ENGINE=InnoDB;
 
-- ENTIDAD ASPECTO
CREATE TABLE aspecto(
    ID_aspecto     INT AUTO_INCREMENT,
    nombre         VARCHAR(150) NOT NULL,
    tipo_contenido ENUM('AUDIOVISUAL','VIDEOJUEGO','AMBOS') NOT NULL,
    PRIMARY KEY (ID_aspecto)
) ENGINE=InnoDB;
 
-- ENTIDAD SUBTIPO CRITICA_AUDIOVISUAL
CREATE TABLE critica_audiovisual(
    ID_critica_audiovisual   INT,
    ID_aspecto               INT NOT NULL,
    ID_contenido_audiovisual INT NOT NULL,
    PRIMARY KEY (ID_critica_audiovisual)
) ENGINE=InnoDB;
 
-- ENTIDAD SUBTIPO CRITICA_VIDEOJUEGO
CREATE TABLE critica_videojuego(
    ID_critica_videojuego INT,
    ID_aspecto            INT NOT NULL,
    ID_videojuego         INT NOT NULL,
    PRIMARY KEY (ID_critica_videojuego)
) ENGINE=InnoDB;
 
-- ENTIDAD MENSAJE
CREATE TABLE mensaje (
    ID_mensaje      INT AUTO_INCREMENT,
    ID_remitente    INT           NOT NULL,
    ID_destinatario INT           NOT NULL,
    asunto          VARCHAR(200)  DEFAULT NULL,
    contenido       VARCHAR(3000) NOT NULL,
    fecha_envio     DATETIME      NOT NULL,
    leido           BOOLEAN       NOT NULL DEFAULT FALSE,
    fecha_lectura   DATETIME      DEFAULT NULL,
    PRIMARY KEY (ID_mensaje),
    CONSTRAINT chk_mensaje_lectura_coherente
        CHECK (fecha_lectura IS NULL OR fecha_lectura >= fecha_envio)
) ENGINE=InnoDB;



-- FK SUPERTIPO USUARIO_REGISTRADO A CRITICO
ALTER TABLE critico
    ADD CONSTRAINT fk_usuario_registrado_critico
    FOREIGN KEY (ID_critico) REFERENCES  usuario_registrado(ID_usuario_registrado)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK SUPERTIPO USUARIO_REGISTRADO EN LISTA
ALTER TABLE lista_usuario
    ADD CONSTRAINT fk_lista_usuario_registrado
    FOREIGN KEY (ID_usuario_registrado) REFERENCES usuario_registrado(ID_usuario_registrado)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK SUBTIPO CONTENIDO_AUDIOVISUAL
ALTER TABLE contenido_audiovisual
    ADD CONSTRAINT fk_contenido_audiovisual_contenido
    FOREIGN KEY (ID_contenido_audiovisual) REFERENCES contenido(ID_contenido)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK SUBTIPO VIDEOJUEGO
ALTER TABLE videojuego
    ADD CONSTRAINT fk_videojuego_contenido
    FOREIGN KEY (ID_videojuego) REFERENCES contenido(ID_contenido)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FKs DE CONTENIDO_USUARIO
ALTER TABLE contenido_usuario
    ADD CONSTRAINT fk_contenido_usuario
    FOREIGN KEY (ID_usuario_registrado) REFERENCES usuario_registrado(ID_usuario_registrado)
    ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE contenido_usuario
    ADD CONSTRAINT fk_usuario_contenido
    FOREIGN KEY (ID_contenido) REFERENCES contenido(ID_contenido)
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- FKs DE LISTA_CONTENIDO
ALTER TABLE lista_contenido
    ADD CONSTRAINT fk_lista_contenido_lista_usuario
    FOREIGN KEY (ID_usuario_registrado, nombre_lista) REFERENCES lista_usuario(ID_usuario_registrado, nombre_lista)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE lista_contenido
    ADD CONSTRAINT fk_lista_contenido_contenido
    FOREIGN KEY (ID_contenido) REFERENCES contenido(ID_contenido)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK CRITICA a USUARIO_REGISTRADO
ALTER TABLE critica
    ADD CONSTRAINT fk_critica_usuario_registrado
    FOREIGN KEY (ID_usuario_registrado) REFERENCES usuario_registrado(ID_usuario_registrado)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK CRITICA_AUDIOVISUAL a CRITICA
ALTER TABLE critica_audiovisual
    ADD CONSTRAINT fk_critica_audiovisual_critica
    FOREIGN KEY (ID_critica_audiovisual) REFERENCES critica(ID_critica)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK CRITICA_AUDIOVISUAL a ASPECTO
ALTER TABLE critica_audiovisual
    ADD CONSTRAINT fk_critica_audiovisual_aspecto
    FOREIGN KEY (ID_aspecto) REFERENCES aspecto(ID_aspecto)
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- FK CRITICA_AUDIOVISUAL a CONTENIDO_AUDIOVISUAL
ALTER TABLE critica_audiovisual
    ADD CONSTRAINT fk_critica_audiovisual_contenido_audiovisual
    FOREIGN KEY (ID_contenido_audiovisual) REFERENCES contenido_audiovisual(ID_contenido_audiovisual)
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- FK CRITICA_VIDEOJUEGO a CRITICA
ALTER TABLE critica_videojuego
    ADD CONSTRAINT fk_critica_videojuego_critica
    FOREIGN KEY (ID_critica_videojuego) REFERENCES critica(ID_critica)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK CRITICA_VIDEOJUEGO a ASPECTO
ALTER TABLE critica_videojuego
    ADD CONSTRAINT fk_critica_videojuego_aspecto
    FOREIGN KEY (ID_aspecto) REFERENCES aspecto(ID_aspecto)
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- FK CRITICA_VIDEOJUEGO a VIDEOJUEGO
ALTER TABLE critica_videojuego
    ADD CONSTRAINT fk_critica_videojuego_videojuego
    FOREIGN KEY (ID_videojuego) REFERENCES videojuego(ID_videojuego)
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- FKs DE CONTENIDO_ETIQUETA
ALTER TABLE contenido_etiqueta
    ADD CONSTRAINT fk_contenido_etiqueta_contenido
    FOREIGN KEY (ID_contenido) REFERENCES contenido(ID_contenido)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE contenido_etiqueta
    ADD CONSTRAINT fk_contenido_etiqueta_etiqueta
    FOREIGN KEY (ID_etiqueta) REFERENCES etiqueta_editorial(ID_etiqueta)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK del remitente (administrador)
ALTER TABLE mensaje
    ADD CONSTRAINT fk_mensaje_remitente
    FOREIGN KEY (ID_remitente) REFERENCES usuario_registrado(ID_usuario_registrado)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- FK del destinatario
ALTER TABLE mensaje
    ADD CONSTRAINT fk_mensaje_destinatario
    FOREIGN KEY (ID_destinatario) REFERENCES usuario_registrado(ID_usuario_registrado)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- Correo electronico y nombre de usuario son valores unicos
ALTER TABLE usuario_registrado
    ADD CONSTRAINT uq_usuario_registrado_nombre  UNIQUE (nombre_usuario),
    ADD CONSTRAINT uq_usuario_registrado_correo  UNIQUE (correo_electronico);

ALTER TABLE aspecto
 ADD CONSTRAINT uq_aspecto_nombre_tipo UNIQUE (nombre, tipo_contenido);


-- -----------------------------------------------------------------
-- TRIGGERS (definidos despues de crear todas las tablas)
-- -----------------------------------------------------------------
DELIMITER $$

-- TRIGGER 0: Listas por defecto al crear un usuario
DROP TRIGGER IF EXISTS trg_lista_default_post_insert_usuario$$

CREATE TRIGGER trg_lista_default_post_insert_usuario
AFTER INSERT ON usuario_registrado
FOR EACH ROW
BEGIN
    INSERT INTO lista_usuario (ID_usuario_registrado, nombre_lista, fecha_creacion) VALUES
        (NEW.ID_usuario_registrado, 'Favoritos',       CURDATE()),
        (NEW.ID_usuario_registrado, 'Pendientes',      CURDATE()),
        (NEW.ID_usuario_registrado, 'Vistos/Jugados',  CURDATE()),
        (NEW.ID_usuario_registrado, 'Reseñados',       CURDATE());
END$$

-- TRIGGER 1: Criticas a peliculas / series -> lista "Reseñados"
DROP TRIGGER IF EXISTS trg_critica_audiovisual_to_resenados$$

CREATE TRIGGER trg_critica_audiovisual_to_resenados
AFTER INSERT ON critica_audiovisual
FOR EACH ROW
BEGIN
    INSERT IGNORE INTO lista_contenido (ID_usuario_registrado, nombre_lista, ID_contenido)
    SELECT c.ID_usuario_registrado,
           'Reseñados',
           NEW.ID_contenido_audiovisual
    FROM   critica c
    JOIN   lista_usuario lu
           ON lu.ID_usuario_registrado = c.ID_usuario_registrado
          AND lu.nombre_lista = 'Reseñados'
    WHERE  c.ID_critica = NEW.ID_critica_audiovisual;
END$$

-- TRIGGER 2: Criticas a videojuegos -> lista "Reseñados"
DROP TRIGGER IF EXISTS trg_critica_videojuego_to_resenados$$

CREATE TRIGGER trg_critica_videojuego_to_resenados
AFTER INSERT ON critica_videojuego
FOR EACH ROW
BEGIN
    INSERT IGNORE INTO lista_contenido (ID_usuario_registrado, nombre_lista, ID_contenido)
    SELECT c.ID_usuario_registrado,
           'Reseñados',
           NEW.ID_videojuego
    FROM   critica c
    JOIN   lista_usuario lu
           ON lu.ID_usuario_registrado = c.ID_usuario_registrado
          AND lu.nombre_lista = 'Reseñados'
    WHERE  c.ID_critica = NEW.ID_critica_videojuego;
END$$

DELIMITER ;


-- ASPECTOS PREDEFINIDOS
-- Aspecto compartido por todos los contenidos
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('General', 'AMBOS');
-- Aspectos compartidos entre peliculas/series y videojuegos
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Historia', 'AMBOS');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Banda sonora', 'AMBOS');
-- Aspectos exclusivos de peliculas y series (audiovisual)
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Interpretacion', 'AUDIOVISUAL');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Direccion', 'AUDIOVISUAL');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Fotografia', 'AUDIOVISUAL');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Guion', 'AUDIOVISUAL');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Efectos especiales', 'AUDIOVISUAL');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Ritmo', 'AUDIOVISUAL');
-- Aspectos exclusivos de videojuegos
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Jugabilidad', 'VIDEOJUEGO');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Graficos', 'VIDEOJUEGO');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Duracion', 'VIDEOJUEGO');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Multijugador', 'VIDEOJUEGO');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Dificultad', 'VIDEOJUEGO');
INSERT INTO aspecto (nombre, tipo_contenido) VALUES ('Diseño de niveles', 'VIDEOJUEGO');

-- =================================================================
-- DATOS DE PRUEBA
-- Todos los usuarios registrados tienen la contrasenha "password"
-- (hash BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy)
-- =================================================================



-- -----------------------------------------------------------------
-- USUARIO_REGISTRADO (supertipo)
--   ID 4         -> ADMINISTRADOR
--   ID 5..9      -> CRITICO (con certificacion)
--   ID 10..25    -> ESTANDAR
--   Algunos baneados para probar el flag
-- -----------------------------------------------------------------
INSERT INTO usuario_registrado (ID_usuario_registrado, nombre_usuario, correo_electronico, fecha_creacion,
                                nombre, apellido1, apellido2, rol, contrasenha, baneado) VALUES
 (4,  'admin',         'admin@starcritic.test',      '2024-01-15', 'Sara',     'Admin',     'Maestre',   'ADMINISTRADOR', '$2b$10$q.0lBkQ4JlWa2Bw73Vp20eEiwwLVWiq5O6r9Cyor0lQihAmEmN5Sq', FALSE),
    (5,  'criticfilm',    'film.critic@starcritic.test','2024-02-03', 'Roger',    'Ebert',     'Vargas',    'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (6,  'criticgames',   'games.critic@starcritic.test','2024-02-10','Pauline',  'Kael',      'Romero',    'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (7,  'cinephile',     'cinephile@starcritic.test',  '2024-03-01', 'Andre',    'Bazin',     NULL,        'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (8,  'gamerpro',      'gamerpro@starcritic.test',   '2024-03-12', 'Hideo',    'Kojima',    'Saito',     'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (9,  'reviewer42',    'reviewer42@starcritic.test', '2024-04-04', 'Linda',    'Morgan',    'Nieto',     'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (10, 'jesus.santos',  'jesus@starcritic.test',      '2024-04-20', 'Jesus',    'Santos',    'Baquero',   'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (11, 'maria.lopez',   'maria.lopez@starcritic.test','2024-05-05', 'Maria',    'Lopez',     'Ruiz',      'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (12, 'pedro.garcia',  'pedro.garcia@starcritic.test','2024-05-12','Pedro',    'Garcia',    'Soto',      'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (13, 'ana.martin',    'ana.martin@starcritic.test', '2024-05-23', 'Ana',      'Martin',    'Vega',      'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (14, 'lucas.fdez',    'lucas.fdez@starcritic.test', '2024-06-01', 'Lucas',    'Fernandez', 'Pena',      'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (15, 'sofia.ruiz',    'sofia.ruiz@starcritic.test', '2024-06-09', 'Sofia',    'Ruiz',      'Cano',      'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (16, 'diego.alonso',  'diego.alonso@starcritic.test','2024-06-18','Diego',    'Alonso',    NULL,        'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (17, 'carla.serrano', 'carla.serrano@starcritic.test','2024-07-02','Carla',   'Serrano',   'Ibanez',    'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (18, 'pablo.molina',  'pablo.molina@starcritic.test','2024-07-15','Pablo',    'Molina',    'Bravo',     'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE),
    (19, 'irene.vidal',   'irene.vidal@starcritic.test','2024-07-28', 'Irene',    'Vidal',     'Romero',    'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (20, 'javier.lara',   'javier.lara@starcritic.test','2024-08-05', 'Javier',   'Lara',      'Ortiz',     'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (21, 'noelia.crespo', 'noelia.crespo@starcritic.test','2024-08-19','Noelia',  'Crespo',    'Marin',     'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (22, 'tomas.iglesias','tomas.iglesias@starcritic.test','2024-09-01','Tomas',  'Iglesias',  'Rey',       'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE),
    (23, 'alba.suarez',   'alba.suarez@starcritic.test','2024-09-14', 'Alba',     'Suarez',    'Diaz',      'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (24, 'raul.hernandez','raul.hernandez@starcritic.test','2024-10-02','Raul',   'Hernandez', 'Mora',      'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE),
    (25, 'elena.torres',  'elena.torres@starcritic.test','2024-10-21', 'Elena',   'Torres',    'Gomez',     'ESTANDAR',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', FALSE);



-- -----------------------------------------------------------------
-- CONTENIDO (supertipo): 15 audiovisuales + 10 videojuegos
-- titulo/sinopsis/poster_key/tipo_contenido son NOT NULL para todo contenido
-- -----------------------------------------------------------------
INSERT INTO contenido (ID_contenido, fecha, origen, destacado, titulo, sinopsis, poster_key, tipo_contenido) VALUES
    (1,  '1994-09-23', 'OMDB', TRUE,  'The Shawshank Redemption',         'Andy Dufresne, condenado por un crimen que no cometio, mantiene la esperanza dentro de la prision de Shawshank.', '', 'PELICULA'),
    (2,  '1972-03-24', 'OMDB', TRUE,  'The Godfather',                    'La saga de la familia Corleone bajo el liderazgo del patriarca Vito.',                                          '', 'PELICULA'),
    (3,  '2008-07-18', 'OMDB', FALSE, 'The Dark Knight',                  'Batman se enfrenta al Joker en una Gotham al borde del caos.',                                                  '', 'PELICULA'),
    (4,  '2003-12-17', 'OMDB', FALSE, 'The Lord of the Rings: The Return of the King', 'Frodo y Sam se acercan al Monte del Destino mientras los reinos del bien preparan su ultima defensa.',     '', 'PELICULA'),
    (5,  '1994-07-06', 'OMDB', FALSE, 'Forrest Gump',                     'La extraordinaria vida de Forrest Gump, testigo de momentos clave del siglo XX.',                              '', 'PELICULA'),
    (6,  '1957-04-10', 'OMDB', FALSE, '12 Angry Men',                     'Doce miembros de un jurado deliberan sobre un caso de homicidio en una sala caldeada.',                          '', 'PELICULA'),
    (7,  '1993-12-15', 'OMDB', FALSE, 'Schindler''s List',                'Oskar Schindler salva a mas de mil judios polacos durante el Holocausto.',                                       '', 'PELICULA'),
    (8,  '2014-11-07', 'OMDB', FALSE, 'Interstellar',                     'Un grupo de astronautas atraviesa un agujero de gusano en busca de un nuevo hogar para la humanidad.',          '', 'PELICULA'),
    (9,  '1990-09-19', 'OMDB', FALSE, 'Goodfellas',                       'Cronica del ascenso y caida de Henry Hill en la mafia neoyorquina.',                                              '', 'PELICULA'),
    (10, '1975-11-19', 'OMDB', FALSE, 'One Flew Over the Cuckoo''s Nest', 'Randle McMurphy desafia a la enfermera Ratched y al sistema de un hospital psiquiatrico.',                       '', 'PELICULA'),
    (11, '2008-01-20', 'OMDB', TRUE,  'Breaking Bad',                     'Walter White, profesor de quimica con cancer, se convierte en fabricante de metanfetamina.',                      '', 'SERIE'),
    (12, '2011-04-17', 'OMDB', FALSE, 'Game of Thrones',                  'Distintas casas nobles luchan por el Trono de Hierro mientras una amenaza se acerca desde el Norte.',             '', 'SERIE'),
    (13, '2001-09-09', 'OMDB', FALSE, 'Band of Brothers',                 'La Easy Company del 506 Regimiento Paracaidista en la Segunda Guerra Mundial.',                                  '', 'SERIE'),
    (14, '2019-05-06', 'OMDB', FALSE, 'Chernobyl',                        'Reconstruccion del accidente nuclear de Chernobyl y de los esfuerzos para contenerlo.',                            '', 'SERIE'),
    (15, '1989-07-05', 'OMDB', FALSE, 'Seinfeld',                         'Comedia de situacion sobre la vida cotidiana de Jerry Seinfeld y sus amigos en Nueva York.',                       '', 'SERIE'),
    (16, '2013-09-17', 'RAWG', FALSE, 'Grand Theft Auto V',               'Tres delincuentes se cruzan en Los Santos en una ambiciosa accion de mundo abierto.',                              '', 'VIDEOJUEGO'),
    (17, '2011-04-19', 'RAWG', TRUE,  'Portal 2',                         'Continuacion del puzzle en primera persona con la pistola de portales y GLaDOS.',                                  '', 'VIDEOJUEGO'),
    (18, '2007-10-10', 'RAWG', FALSE, 'Portal',                           'Puzzle en primera persona ambientado en el centro de pruebas Aperture Science.',                                  '', 'VIDEOJUEGO'),
    (19, '2013-03-05', 'RAWG', FALSE, 'Tomb Raider',                      'Lara Croft sobrevive a un naufragio y se convierte en aventurera en la isla de Yamatai.',                          '', 'VIDEOJUEGO'),
    (20, '2018-10-26', 'RAWG', TRUE,  'Red Dead Redemption 2',            'Arthur Morgan recorre la decadencia de la banda de Van der Linde en el oeste americano.',                          '', 'VIDEOJUEGO'),
    (21, '2007-08-21', 'RAWG', FALSE, 'BioShock',                         'Exploracion de la ciudad submarina distopica de Rapture y su ideologia objetivista.',                              '', 'VIDEOJUEGO'),
    (22, '2011-11-11', 'RAWG', FALSE, 'The Elder Scrolls V: Skyrim',      'RPG de mundo abierto sobre el ultimo Sangre de Dragon en la provincia nordica de Skyrim.',                          '', 'VIDEOJUEGO'),
    (23, '2012-09-18', 'RAWG', FALSE, 'Borderlands 2',                    'Shooter saqueador con humor en el planeta Pandora.',                                                                '', 'VIDEOJUEGO'),
    (24, '2007-10-09', 'RAWG', FALSE, 'Team Fortress 2',                  'Shooter por equipos con nueve clases muy distintas y estetica caricaturesca.',                                       '', 'VIDEOJUEGO'),
    (25, '2010-01-26', 'RAWG', FALSE, 'Mass Effect 2',                    'El comandante Shepard recluta una tripulacion para una mision suicida contra los Recolectores.',                   '', 'VIDEOJUEGO');

-- -----------------------------------------------------------------
-- CONTENIDO LOCAL (ejemplos): obras que no estan en OMDb/RAWG
-- titulo, sinopsis, poster_key y tipo_contenido siguen siendo obligatorios
-- poster_key se rellena al subirlo a Cloudflare R2 (vacio en datos de prueba)
-- -----------------------------------------------------------------
INSERT INTO contenido (ID_contenido, fecha, origen, destacado, oculto,
                       titulo, sinopsis, poster_key, tipo_contenido) VALUES
    (26, '2023-05-12', 'LOCAL', FALSE, FALSE,
     'El Cortometraje de la Esquina',
     'Cortometraje independiente sobre una panaderia que cobra vida por las noches.',
     '', 'PELICULA'),
    (27, '2024-03-08', 'LOCAL', TRUE, FALSE,
     'Pixel Heroes',
     'Aventura indie en pixel art con mecanicas roguelike. Desarrollado por un estudio espanol de tres personas.',
     '', 'VIDEOJUEGO');

-- Los registros LOCAL tambien viven en su subtipo, pero con ID_Api = NULL
INSERT INTO contenido_audiovisual (ID_contenido_audiovisual, ID_Api) VALUES (26, NULL);
INSERT INTO videojuego (ID_videojuego, ID_Api) VALUES (27, NULL);

-- CONTENIDO_AUDIOVISUAL (peliculas y series con ID de OMDb/IMDb)
INSERT INTO contenido_audiovisual (ID_contenido_audiovisual, ID_Api) VALUES
    (1,  'tt0111161'),
    (2,  'tt0068646'),
    (3,  'tt0468569'),
    (4,  'tt0167260'),
    (5,  'tt0109830'),
    (6,  'tt0050083'),
    (7,  'tt0108052'),
    (8,  'tt0816692'),
    (9,  'tt0099685'),
    (10, 'tt0073486'),
    (11, 'tt0903747'),
    (12, 'tt0944947'),
    (13, 'tt0185906'),
    (14, 'tt7366338'),
    (15, 'tt0098904');

-- VIDEOJUEGO (con ID de RAWG)
INSERT INTO videojuego (ID_videojuego, ID_Api) VALUES
    (16, 3498),
    (17, 4200),
    (18, 13536),
    (19, 5286),
    (20, 28),
    (21, 4286),
    (22, 5679),
    (23, 802),
    (24, 11859),
    (25, 32);

-- -----------------------------------------------------------------
-- LISTA_USUARIO: cada usuario registrado tiene 1 o 2 listas
-- -----------------------------------------------------------------
INSERT INTO lista_usuario (ID_usuario_registrado, nombre_lista, fecha_creacion) VALUES
    (4,  'Favoritas administracion', '2024-01-20'),
    (5,  'Top 10 cine clasico',      '2024-02-10'),
    (5,  'Series imprescindibles',   '2024-02-15'),
    (6,  'Imprescindibles indie',    '2024-02-20'),
    (6,  'Pendientes de jugar',      '2024-03-01'),
    (7,  'Cine de autor',            '2024-03-15'),
    (7,  'Mejores guiones',          '2024-03-22'),
    (8,  'Juegos GOTY',              '2024-03-30'),
    (8,  'Mundos abiertos',          '2024-04-05'),
    (9,  'Documentales y dramas',    '2024-04-15'),
    (10, 'Mis favoritas',            '2024-05-01'),
    (10, 'Por ver',                  '2024-05-02'),
    (11, 'Pelis para llorar',        '2024-05-10'),
    (12, 'Recomendaciones amigos',   '2024-05-18'),
    (13, 'Maratones de fin de semana','2024-05-25'),
    (14, 'Aventuras epicas',         '2024-06-05'),
    (15, 'Cine de los 90',           '2024-06-12'),
    (16, 'Top thrillers',            '2024-06-22'),
    (17, 'Series que enganchan',     '2024-07-05'),
    (18, 'Mi backlog',               '2024-07-18'),
    (19, 'Para jugar en pareja',     '2024-08-01'),
    (20, 'Joyas ocultas',            '2024-08-08'),
    (21, 'Clasicos imprescindibles', '2024-08-22'),
    (22, 'Cosas pendientes',         '2024-09-05'),
    (23, 'Pelis indie',              '2024-09-18'),
    (24, 'Juegos de rol',            '2024-10-05'),
    (25, 'Maraton navidad',          '2024-10-25');

-- -----------------------------------------------------------------
-- LISTA_CONTENIDO: contenidos asignados a las listas
-- -----------------------------------------------------------------
INSERT INTO lista_contenido (ID_usuario_registrado, nombre_lista, ID_contenido) VALUES
    (4,  'Favoritas administracion', 1),
    (4,  'Favoritas administracion', 8),
    (4,  'Favoritas administracion', 16),
    (5,  'Top 10 cine clasico', 1),
    (5,  'Top 10 cine clasico', 2),
    (5,  'Top 10 cine clasico', 6),
    (5,  'Top 10 cine clasico', 7),
    (5,  'Top 10 cine clasico', 9),
    (5,  'Top 10 cine clasico', 10),
    (5,  'Series imprescindibles', 11),
    (5,  'Series imprescindibles', 12),
    (5,  'Series imprescindibles', 13),
    (5,  'Series imprescindibles', 14),
    (6,  'Imprescindibles indie', 17),
    (6,  'Imprescindibles indie', 18),
    (6,  'Pendientes de jugar', 20),
    (6,  'Pendientes de jugar', 22),
    (6,  'Pendientes de jugar', 25),
    (7,  'Cine de autor', 2),
    (7,  'Cine de autor', 7),
    (7,  'Cine de autor', 9),
    (7,  'Mejores guiones', 5),
    (7,  'Mejores guiones', 11),
    (8,  'Juegos GOTY', 16),
    (8,  'Juegos GOTY', 20),
    (8,  'Juegos GOTY', 22),
    (8,  'Juegos GOTY', 25),
    (8,  'Mundos abiertos', 16),
    (8,  'Mundos abiertos', 20),
    (8,  'Mundos abiertos', 22),
    (9,  'Documentales y dramas', 7),
    (9,  'Documentales y dramas', 14),
    (10, 'Mis favoritas', 3),
    (10, 'Mis favoritas', 8),
    (10, 'Mis favoritas', 17),
    (10, 'Por ver', 4),
    (10, 'Por ver', 12),
    (11, 'Pelis para llorar', 5),
    (11, 'Pelis para llorar', 7),
    (11, 'Pelis para llorar', 10),
    (12, 'Recomendaciones amigos', 1),
    (12, 'Recomendaciones amigos', 11),
    (12, 'Recomendaciones amigos', 22),
    (13, 'Maratones de fin de semana', 12),
    (13, 'Maratones de fin de semana', 13),
    (13, 'Maratones de fin de semana', 15),
    (14, 'Aventuras epicas', 4),
    (14, 'Aventuras epicas', 19),
    (14, 'Aventuras epicas', 22),
    (15, 'Cine de los 90', 1),
    (15, 'Cine de los 90', 5),
    (15, 'Cine de los 90', 9),
    (16, 'Top thrillers', 3),
    (16, 'Top thrillers', 9),
    (17, 'Series que enganchan', 11),
    (17, 'Series que enganchan', 12),
    (17, 'Series que enganchan', 14),
    (18, 'Mi backlog', 16),
    (18, 'Mi backlog', 18),
    (18, 'Mi backlog', 23),
    (19, 'Para jugar en pareja', 17),
    (19, 'Para jugar en pareja', 23),
    (19, 'Para jugar en pareja', 24),
    (20, 'Joyas ocultas', 6),
    (20, 'Joyas ocultas', 21),
    (21, 'Clasicos imprescindibles', 2),
    (21, 'Clasicos imprescindibles', 6),
    (21, 'Clasicos imprescindibles', 10),
    (22, 'Cosas pendientes', 8),
    (22, 'Cosas pendientes', 14),
    (23, 'Pelis indie', 5),
    (23, 'Pelis indie', 7),
    (24, 'Juegos de rol', 22),
    (24, 'Juegos de rol', 25),
    (25, 'Maraton navidad', 1),
    (25, 'Maraton navidad', 4),
    (25, 'Maraton navidad', 13);

-- -----------------------------------------------------------------
-- CONTENIDO_USUARIO: visitas (usuario, contenido, fecha, num)
-- -----------------------------------------------------------------
INSERT INTO contenido_usuario (ID_usuario_registrado, ID_contenido, fecha_visita, num_visitas) VALUES
    (4,  1,  '2024-02-01', 4),
    (4,  8,  '2024-02-10', 2),
    (4,  16, '2024-03-01', 1),
    (5,  1,  '2024-02-15', 7),
    (5,  2,  '2024-02-16', 5),
    (5,  6,  '2024-02-18', 3),
    (5,  9,  '2024-02-25', 6),
    (5,  11, '2024-03-02', 8),
    (5,  12, '2024-03-05', 4),
    (6,  16, '2024-03-10', 5),
    (6,  17, '2024-03-12', 9),
    (6,  18, '2024-03-13', 6),
    (6,  20, '2024-03-20', 3),
    (6,  22, '2024-03-25', 4),
    (7,  2,  '2024-04-01', 3),
    (7,  7,  '2024-04-05', 4),
    (7,  9,  '2024-04-10', 5),
    (7,  14, '2024-04-12', 2),
    (8,  16, '2024-04-15', 12),
    (8,  20, '2024-04-18', 8),
    (8,  22, '2024-04-22', 7),
    (8,  25, '2024-04-28', 3),
    (9,  7,  '2024-05-02', 1),
    (9,  10, '2024-05-04', 2),
    (9,  14, '2024-05-08', 4),
    (10, 1,  '2024-05-15', 3),
    (10, 3,  '2024-05-16', 5),
    (10, 8,  '2024-05-20', 2),
    (10, 17, '2024-05-22', 4),
    (11, 5,  '2024-05-25', 6),
    (11, 7,  '2024-05-26', 1),
    (11, 10, '2024-05-30', 2),
    (12, 1,  '2024-06-02', 3),
    (12, 11, '2024-06-05', 5),
    (12, 22, '2024-06-08', 2),
    (13, 12, '2024-06-12', 4),
    (13, 13, '2024-06-15', 3),
    (13, 15, '2024-06-18', 6),
    (14, 4,  '2024-06-22', 5),
    (14, 19, '2024-06-25', 4),
    (14, 22, '2024-07-01', 7),
    (15, 1,  '2024-07-05', 2),
    (15, 5,  '2024-07-08', 1),
    (15, 9,  '2024-07-10', 3),
    (16, 3,  '2024-07-15', 5),
    (16, 9,  '2024-07-18', 2),
    (17, 11, '2024-07-22', 9),
    (17, 12, '2024-07-25', 6),
    (17, 14, '2024-08-01', 3),
    (18, 16, '2024-08-05', 4),
    (18, 18, '2024-08-08', 2),
    (18, 23, '2024-08-12', 1),
    (19, 17, '2024-08-15', 5),
    (19, 24, '2024-08-18', 3),
    (20, 6,  '2024-08-22', 1),
    (20, 21, '2024-08-25', 4),
    (21, 2,  '2024-09-01', 2),
    (21, 10, '2024-09-05', 3),
    (22, 8,  '2024-09-10', 4),
    (22, 14, '2024-09-12', 1),
    (23, 5,  '2024-09-18', 2),
    (24, 22, '2024-10-01', 6),
    (24, 25, '2024-10-05', 4),
    (25, 4,  '2024-10-22', 5),
    (25, 13, '2024-10-26', 2);

-- -----------------------------------------------------------------
-- CRITICA (supertipo): se inserta primero, luego subtipos
--   Aspectos AUDIOVISUAL  -> IDs 1,2,3,4,5,6,7,8,9
--   Aspectos VIDEOJUEGO   -> IDs 1,2,3,10,11,12,13,14,15
-- -----------------------------------------------------------------
INSERT INTO critica (ID_critica, puntuacion, descripcion, ID_usuario_registrado) VALUES
    (1,  95, 'Una obra maestra del cine penitenciario, narrativa impecable.',                 5),
    (2,  92.5, 'La direccion de Darabont y la fotografia construyen una atmosfera unica.',      5),
    (3,  98, 'Coppola entrega la mejor pelicula de gangsters jamas filmada.',                 5),
    (4,  90, 'Marlon Brando ofrece una interpretacion para la historia.',                     7),
    (5,  96.5, 'Nolan reinventa el genero superheroe con este thriller moderno.',               5),
    (6,  94, 'Heath Ledger eleva la pelicula a otro nivel con su Joker.',                     7),
    (7,  93, 'El cierre perfecto a una trilogia ciclopea.',                                   9),
    (8,  88.5, 'La banda sonora de Howard Shore es simplemente sobrecogedora.',                 5),
    (9,  91, 'Una fabula americana contada con un corazon enorme.',                          11),
    (10, 89.5, 'Tom Hanks brilla en cada plano.',                                              15),
    (11, 99, 'Cinematograficamente austera y dramaticamente perfecta.',                       7),
    (12, 95, 'Un drama de tribunal magistral, basado solo en dialogo.',                       5),
    (13, 97, 'Spielberg firma un retrato indispensable del Holocausto.',                      9),
    (14, 92, 'La fotografia en blanco y negro es devastadora.',                               7),
    (15, 90, 'Nolan construye una odisea espacial emocionante y ambiciosa.',                  5),
    (16, 87, 'Hans Zimmer entrega una de sus mejores partituras.',                           17),
    (17, 93.5, 'Scorsese a maxima potencia, ritmo trepidante.',                                 7),
    (18, 89, 'Los dialogos y la narracion en off son escuela de guion.',                     11),
    (19, 91, 'Un clasico imprescindible sobre la libertad y la institucion.',                 9),
    (20, 95.5, 'La mejor serie de la decada, escritura quirurgica.',                            5),
    (21, 96, 'Bryan Cranston entrega una interpretacion legendaria.',                         7),
    (22, 88, 'Excelente arranque pero el final divide a la audiencia.',                       9),
    (23, 94, 'Produccion historica de altisimo nivel.',                                      13),
    (24, 97.5, 'Documental dramatizado con un guion implacable.',                              17),
    (25, 85, 'Comedia de situacion que envejecio mejor de lo esperado.',                     11),
    (26, 96, 'Mundo abierto monumental, ambicion sin limites.',                               6),
    (27, 92.5, 'La conduccion y los tiroteos son los mejores de la generacion.',                8),
    (28, 99, 'Diseno de niveles brillante, humor y mecanicas inolvidables.',                  6),
    (29, 95.5, 'GLaDOS es uno de los mejores villanos del medio.',                              8),
    (30, 90, 'El portal-gun cambia tu manera de pensar el espacio.',                          6),
    (31, 86, 'Corto pero perfecto en su ejecucion.',                                         18),
    (32, 88.5, 'Reboot solido con una Lara mas humana.',                                       14),
    (33, 92, 'Las animaciones y mocap son sobresalientes.',                                   8),
    (34, 98, 'Rockstar redefine los mundos abiertos de nuevo.',                               6),
    (35, 96.5, 'La narrativa de Arthur Morgan es una de las mejores del medio.',                8),
    (36, 94, 'Rapture es uno de los escenarios mas memorables jamas creados.',                6),
    (37, 89, 'Plasmidos y armas combinan en una jugabilidad satisfactoria.',                 14),
    (38, 95, 'Skyrim es el RPG de mundo abierto definitivo.',                                 8),
    (39, 87, 'Bugs aparte, la libertad de exploracion no tiene rival.',                       6),
    (40, 90.5, 'El loot es adictivo y el tono gamberro funciona.',                              6),
    (41, 86, 'En cooperativo crece muchisimo, mejor con amigos.',                            16),
    (42, 91, 'Sigue siendo el shooter por equipos mas divertido.',                            8),
    (43, 84, 'Las clases estan increiblemente bien diferenciadas.',                          16),
    (44, 97, 'La trilogia Mass Effect alcanza su mejor entrega aqui.',                        6),
    (45, 93.5, 'El elenco de personajes es probablemente el mejor del genero.',                 8),
    (46, 75, 'Esperaba mas profundidad, decente pero no memorable.',                         11),
    (47, 60, 'Sobrevalorada en mi opinion, la trama se hace pesada.',                        12),
    (48, 82, 'Solida pero con problemas de ritmo en el segundo acto.',                       19),
    (49, 70.5, 'El final no esta a la altura del resto del juego.',                            20),
    (50, 78, 'Buena premisa, ejecucion irregular.',                                          21);

-- CRITICA_AUDIOVISUAL: criticas 1..25 son sobre peliculas/series
INSERT INTO critica_audiovisual (ID_critica_audiovisual, ID_aspecto, ID_contenido_audiovisual) VALUES
    (1,  1, 1),   -- General sobre Shawshank
    (2,  6, 1),   -- Fotografia en Shawshank
    (3,  1, 2),   -- General sobre El Padrino
    (4,  4, 2),   -- Interpretacion en El Padrino
    (5,  1, 3),   -- General sobre Dark Knight
    (6,  4, 3),   -- Interpretacion en Dark Knight
    (7,  1, 4),   -- General sobre LOTR
    (8,  3, 4),   -- Banda sonora en LOTR
    (9,  1, 5),   -- General sobre Forrest Gump
    (10, 4, 5),   -- Interpretacion en Forrest Gump
    (11, 1, 6),   -- General sobre 12 Angry Men
    (12, 7, 6),   -- Guion en 12 Angry Men
    (13, 1, 7),   -- General sobre Schindler
    (14, 6, 7),   -- Fotografia en Schindler
    (15, 1, 8),   -- General sobre Interstellar
    (16, 3, 8),   -- Banda sonora en Interstellar
    (17, 1, 9),   -- General sobre Goodfellas
    (18, 7, 9),   -- Guion en Goodfellas
    (19, 1, 10),  -- General sobre Cuckoo
    (20, 1, 11),  -- General sobre Breaking Bad
    (21, 4, 11),  -- Interpretacion en Breaking Bad
    (22, 1, 12),  -- General sobre Game of Thrones
    (23, 1, 13),  -- General sobre Band of Brothers
    (24, 1, 14),  -- General sobre Chernobyl
    (25, 1, 15);  -- General sobre Seinfeld

-- CRITICA_VIDEOJUEGO: criticas 26..45 son sobre videojuegos
INSERT INTO critica_videojuego (ID_critica_videojuego, ID_aspecto, ID_videojuego) VALUES
    (26, 1,  16),  -- General sobre GTA V
    (27, 10, 16),  -- Jugabilidad en GTA V
    (28, 1,  17),  -- General sobre Portal 2
    (29, 15, 17),  -- Diseno de niveles en Portal 2
    (30, 1,  18),  -- General sobre Portal
    (31, 12, 18),  -- Duracion en Portal
    (32, 1,  19),  -- General sobre Tomb Raider
    (33, 11, 19),  -- Graficos en Tomb Raider
    (34, 1,  20),  -- General sobre RDR2
    (35, 2,  20),  -- Historia en RDR2
    (36, 1,  21),  -- General sobre BioShock
    (37, 10, 21),  -- Jugabilidad en BioShock
    (38, 1,  22),  -- General sobre Skyrim
    (39, 15, 22),  -- Diseno de niveles en Skyrim
    (40, 1,  23),  -- General sobre Borderlands 2
    (41, 13, 23),  -- Multijugador en Borderlands 2
    (42, 1,  24),  -- General sobre TF2
    (43, 13, 24),  -- Multijugador en TF2
    (44, 1,  25),  -- General sobre Mass Effect 2
    (45, 2,  25);  -- Historia en Mass Effect 2

-- Criticas mixtas adicionales (puntuaciones bajas para probar filtros)
INSERT INTO critica_audiovisual (ID_critica_audiovisual, ID_aspecto, ID_contenido_audiovisual) VALUES
    (46, 9, 12),   -- Ritmo en GoT
    (47, 1, 12),   -- General sobre GoT (negativa)
    (48, 9, 8);    -- Ritmo en Interstellar

INSERT INTO critica_videojuego (ID_critica_videojuego, ID_aspecto, ID_videojuego) VALUES
    (49, 14, 22),  -- Dificultad en Skyrim
    (50, 11, 23);  -- Graficos en Borderlands 2

-- -----------------------------------------------------------------
-- ETIQUETAS EDITORIALES (curadas por admin)
-- -----------------------------------------------------------------
INSERT INTO etiqueta_editorial (ID_etiqueta, nombre) VALUES
    (1, 'Imprescindible'),
    (2, 'Joya oculta'),
    (3, 'Clasico'),
    (4, 'Recomendado por la redaccion'),
    (5, 'Indie');

INSERT INTO contenido_etiqueta (ID_contenido, ID_etiqueta) VALUES
    (1, 1), (1, 3),     -- Shawshank: Imprescindible + Clasico
    (2, 1), (2, 3),     -- Godfather: Imprescindible + Clasico
    (6, 2), (6, 3),     -- 12 Angry Men: Joya oculta + Clasico
    (8, 4),             -- Interstellar: Recomendado
    (11, 1),            -- Breaking Bad: Imprescindible
    (17, 1), (17, 4),   -- Portal 2: Imprescindible + Recomendado
    (20, 4),            -- RDR2: Recomendado
    (26, 5),            -- LOCAL Cortometraje: Indie
    (27, 5), (27, 4);   -- LOCAL Pixel Heroes: Indie + Recomendado


-- -----------------------------------------------------------------
-- DATOS DE PRUEBA (administrador = ID 4, destinatarios varios)
-- -----------------------------------------------------------------
INSERT INTO mensaje (ID_mensaje, ID_remitente, ID_destinatario, asunto, contenido, fecha_envio, leido, fecha_lectura) VALUES
    (1, 4, 18, 'Advertencia por incumplimiento',  'Tu cuenta ha sido baneada por incumplir las normas de la comunidad.',                '2024-08-01 10:15:00', TRUE,  '2024-08-01 12:30:00'),
    (2, 4, 22, 'Advertencia por incumplimiento',  'Tu cuenta ha sido baneada tras revision de tus criticas.',                            '2024-09-05 09:00:00', TRUE,  '2024-09-05 18:45:00'),
    (3, 4, 5,  'Certificacion aprobada',          'Hemos validado tu certificacion como critico. Bienvenido al equipo.',                '2024-02-04 11:00:00', TRUE,  '2024-02-04 11:20:00'),
    (4, 4, 10, 'Bienvenido a Star Critic',        'Gracias por unirte a la plataforma. Te recomendamos completar tu perfil.',           '2024-04-21 08:30:00', FALSE, NULL),
    (5, 4, 11, 'Mantenimiento programado',        'El sabado 15 a las 03:00 la plataforma estara fuera de servicio durante 2 horas.',  '2024-06-10 17:00:00', FALSE, NULL);

-- Reseteamos los AUTO_INCREMENT a partir de los IDs insertados
ALTER TABLE usuario_registrado AUTO_INCREMENT = 26;
ALTER TABLE contenido          AUTO_INCREMENT = 28;
ALTER TABLE critica            AUTO_INCREMENT = 51;
ALTER TABLE etiqueta_editorial AUTO_INCREMENT = 6;
ALTER TABLE mensaje            AUTO_INCREMENT = 6;