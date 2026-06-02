-- =================================================================
-- SEED: 1000 criticas de prueba para star_critic
--
-- Requisitos previos: haber ejecutado create_database.sql
-- (necesita usuarios 4..25, contenido 1..27, aspectos 1..15 y los
--  triggers trg_critica_*_to_resenados ya creados).
--
-- Una critica completa = 1 fila en critica (supertipo)
--                       + 1 fila en critica_audiovisual O critica_videojuego.
-- Usamos LAST_INSERT_ID() para enlazar supertipo y subtipo sin
-- depender del valor concreto del AUTO_INCREMENT.
-- =================================================================
USE star_critic;

DROP PROCEDURE IF EXISTS sp_generar_mil_criticas;

DELIMITER $$

CREATE PROCEDURE sp_generar_mil_criticas()
BEGIN
    DECLARE i           INT DEFAULT 0;
    DECLARE v_id_critica INT;
    DECLARE v_punt      INT;
    DECLARE v_usuario   INT;
    DECLARE v_tipo      INT;          -- 0 = audiovisual, 1 = videojuego
    DECLARE v_aspecto   INT;
    DECLARE v_contenido INT;
    DECLARE v_desc      VARCHAR(3000);

    WHILE i < 1000 DO
        -- puntuacion valida segun chk_critica_puntuacion (0..100)
        SET v_punt = FLOOR(RAND() * 100);

        -- usuario registrado existente: IDs 4..25 (22 usuarios)
        SET v_usuario = 4 + FLOOR(RAND() * 22);

        -- descripcion variada (NOT NULL) combinando frases
        SET v_desc = CONCAT(
            ELT(1 + FLOOR(RAND() * 5),
                'Una experiencia notable',
                'Muy por debajo de lo esperado',
                'Cumple sin llegar a destacar',
                'Sobresaliente en casi todo',
                'Irregular pero con momentos brillantes'),
            '. ',
            ELT(1 + FLOOR(RAND() * 5),
                'La recomendaria sin dudarlo.',
                'No repetiria la experiencia.',
                'Ideal para los fans del genero.',
                'Tiene altibajos pero merece la pena.',
                'Le falta un punto para ser memorable.'),
            ' (critica de prueba #', i + 1, ')');

        -- 0 = audiovisual, 1 = videojuego
        SET v_tipo = FLOOR(RAND() * 2);

        -- 1) Supertipo
        INSERT INTO critica (puntuacion, descripcion, ID_usuario_registrado)
        VALUES (v_punt, v_desc, v_usuario);

        SET v_id_critica = LAST_INSERT_ID();

        -- 2) Subtipo correspondiente
        IF v_tipo = 0 THEN
            -- aspectos validos para audiovisual: 1,2,3 (AMBOS) y 4..9 (AUDIOVISUAL)
            SET v_aspecto = 1 + FLOOR(RAND() * 9);
            -- contenido audiovisual existente: 1..15 y 26 (LOCAL)
            SET v_contenido = ELT(1 + FLOOR(RAND() * 16),
                                  1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,26);
            INSERT INTO critica_audiovisual
                   (ID_critica_audiovisual, ID_aspecto, ID_contenido_audiovisual)
            VALUES (v_id_critica, v_aspecto, v_contenido);
        ELSE
            -- aspectos validos para videojuego: 1,2,3 (AMBOS) y 10..15 (VIDEOJUEGO)
            SET v_aspecto = ELT(1 + FLOOR(RAND() * 9),
                                1,2,3,10,11,12,13,14,15);
            -- videojuegos existentes: 16..25 y 27 (LOCAL)
            SET v_contenido = ELT(1 + FLOOR(RAND() * 11),
                                  16,17,18,19,20,21,22,23,24,25,27);
            INSERT INTO critica_videojuego
                   (ID_critica_videojuego, ID_aspecto, ID_videojuego)
            VALUES (v_id_critica, v_aspecto, v_contenido);
        END IF;

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

-- Ejecuta la generacion y limpia el procedimiento
CALL sp_generar_mil_criticas();

DROP PROCEDURE IF EXISTS sp_generar_mil_criticas;

-- Comprobacion rapida
SELECT COUNT(*) AS total_criticas FROM critica;
