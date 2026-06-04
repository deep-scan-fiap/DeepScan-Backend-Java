-- Gerado por Oracle SQL Developer Data Modeler 24.3.1.351.0831
--   em:        2026-06-02 18:47:27 BRT
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g

-- Integrantes

-- RM568542: Hugo Souza de Jesus 
-- RM566815: Lucas Campanhã dos Santos 
-- RM567010: Lucas Marcelino Pompeu 
-- RM567134: Gustavo Souza Nascimento 
-- RM561032: Enzo Yukio Oyadomari 

DROP TABLE alerta CASCADE CONSTRAINTS;
DROP TABLE avistamento CASCADE CONSTRAINTS;
DROP TABLE especie CASCADE CONSTRAINTS;
DROP TABLE estacao_monitora CASCADE CONSTRAINTS;
DROP TABLE estacao_zona CASCADE CONSTRAINTS;
DROP TABLE leitura_telemetria CASCADE CONSTRAINTS;
DROP TABLE usuario_operador CASCADE CONSTRAINTS;
DROP TABLE zona_monitora CASCADE CONSTRAINTS;

DROP SEQUENCE seq_alerta;
DROP SEQUENCE seq_avistamento;
DROP SEQUENCE seq_especie;
DROP SEQUENCE seq_estacao;
DROP SEQUENCE seq_leitura;
DROP SEQUENCE seq_opera;
DROP SEQUENCE seq_zona;

-- ============================================================
-- SEQUENCES
-- ============================================================

CREATE SEQUENCE seq_zona      START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_estacao   START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_especie   START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_leitura   START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_alerta    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_avistamento START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_opera     START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- ============================================================
-- TABELAS
-- ============================================================

CREATE TABLE zona_monitora
    (
     id_zona   NUMBER (4)       NOT NULL ,
     nome_zona VARCHAR2 (100)   NOT NULL ,
     pais_zona VARCHAR2 (60)    NOT NULL ,
     desc_zona VARCHAR2 (200)
    )
;

ALTER TABLE zona_monitora
    ADD CONSTRAINT zona_monitora_PK PRIMARY KEY ( id_zona );

-- ------------------------------------------------------------

CREATE TABLE estacao_monitora
    (
     id_estacao   NUMBER (4)      NOT NULL ,
     nome_estacao VARCHAR2 (50)   NOT NULL ,
     lat_estacao  NUMBER (9,6)    NOT NULL ,
     lon_estacao  NUMBER (9,6)    NOT NULL ,
     tipo_estacao VARCHAR2 (30)   NOT NULL
    )
;

ALTER TABLE estacao_monitora
    ADD CONSTRAINT estacao_monitora_PK PRIMARY KEY ( id_estacao );

ALTER TABLE estacao_monitora
    ADD CONSTRAINT chk_tipo_estacao
    CHECK (tipo_estacao IN ('Boia', 'Satelite', 'Submarina'));

-- ------------------------------------------------------------

CREATE TABLE estacao_zona
    (
     id_zona      NUMBER (4)  NOT NULL ,
     id_estacao   NUMBER (4)  NOT NULL ,
     data_vinculo DATE        NOT NULL
    )
;

ALTER TABLE estacao_zona
    ADD CONSTRAINT estacao_zona_PK PRIMARY KEY ( id_zona, id_estacao );

-- ------------------------------------------------------------

CREATE TABLE especie
    (
     id_especie       NUMBER (4)       NOT NULL ,
     nc_especie       VARCHAR2 (100)   NOT NULL ,
     np_especie       VARCHAR2 (100)   NOT NULL ,
     conserva_especie VARCHAR2 (2)     NOT NULL ,
     habitat_especie  VARCHAR2 (60)    NOT NULL ,
     desc_especie     VARCHAR2 (200)
    )
;

ALTER TABLE especie
    ADD CONSTRAINT especie_PK PRIMARY KEY ( id_especie );

ALTER TABLE especie
    ADD CONSTRAINT chk_conserva_especie
    CHECK (conserva_especie IN ('LC', 'NT', 'VU', 'EN', 'CR'));

-- ------------------------------------------------------------

CREATE TABLE leitura_telemetria
    (
     id_leitura           NUMBER (4)   NOT NULL ,
     id_estacao           NUMBER (4)   NOT NULL ,
     horario_leitura      TIMESTAMP WITH LOCAL TIME ZONE  NOT NULL ,
     sst                  NUMBER (5,2) NOT NULL ,
     wave_height          NUMBER (5,2) NOT NULL ,
     wave_period          NUMBER (5,2) NOT NULL ,
     wind_speed           NUMBER (6,2) NOT NULL ,
     wind_direction       NUMBER (5,2) NOT NULL ,
     earthquake_magnitude NUMBER (4,2) NOT NULL ,
     focal_depth          NUMBER (7,3) NOT NULL
    )
;

ALTER TABLE leitura_telemetria
    ADD CONSTRAINT leitura_telemetria_PK PRIMARY KEY ( id_leitura );

-- ------------------------------------------------------------

CREATE TABLE alerta
    (
     id_leitura       NUMBER (4)       NOT NULL ,
     id_alerta        NUMBER (4)       NOT NULL ,
     risco_alerta     VARCHAR2 (5)     NOT NULL ,
     desc_alerta      VARCHAR2 (200)   NOT NULL ,
     horario_alerta   TIMESTAMP WITH LOCAL TIME ZONE  NOT NULL ,
     observa_alerta   VARCHAR2 (200)   ,
     conclusao_alerta VARCHAR2 (1)     NOT NULL
    )
;

ALTER TABLE alerta
    ADD CONSTRAINT alerta_PK PRIMARY KEY ( id_alerta );

ALTER TABLE alerta
    ADD CONSTRAINT chk_conclusao_alerta
    CHECK (conclusao_alerta IN ('S', 'N'));

ALTER TABLE alerta
    ADD CONSTRAINT chk_risco_alerta
    CHECK (risco_alerta IN ('ALTO', 'MEDIO', 'BAIXO'));

-- ------------------------------------------------------------

CREATE TABLE avistamento
    (
     id_estacao     NUMBER (4)   NOT NULL ,
     id_especie     NUMBER (4)   NOT NULL ,
     id_avista      NUMBER       NOT NULL ,
     horario_avista TIMESTAMP WITH LOCAL TIME ZONE  NOT NULL ,
     quant_avista   NUMBER (3)   NOT NULL
    )
;

ALTER TABLE avistamento
    ADD CONSTRAINT avistamento_PK PRIMARY KEY ( id_avista );

-- ------------------------------------------------------------

CREATE TABLE usuario_operador
    (
     id_opera    NUMBER (4)      NOT NULL ,
     nm_opera    VARCHAR2 (40)   NOT NULL ,
     email_opera VARCHAR2 (60)   NOT NULL ,
     senha_opera VARCHAR2 (50)   NOT NULL
    )
;

ALTER TABLE usuario_operador
    ADD CONSTRAINT usuario_operador_PK PRIMARY KEY ( id_opera );

-- ============================================================
-- FOREIGN KEYS
-- ============================================================

ALTER TABLE estacao_zona
    ADD CONSTRAINT fk_estazona_zona FOREIGN KEY ( id_zona )
    REFERENCES zona_monitora ( id_zona );

ALTER TABLE estacao_zona
    ADD CONSTRAINT fk_estazona_estacao FOREIGN KEY ( id_estacao )
    REFERENCES estacao_monitora ( id_estacao );

ALTER TABLE leitura_telemetria
    ADD CONSTRAINT fk_leitura_estacao FOREIGN KEY ( id_estacao )
    REFERENCES estacao_monitora ( id_estacao );

ALTER TABLE alerta
    ADD CONSTRAINT fk_alerta_leitura FOREIGN KEY ( id_leitura )
    REFERENCES leitura_telemetria ( id_leitura );

ALTER TABLE avistamento
    ADD CONSTRAINT fk_avista_estacao FOREIGN KEY ( id_estacao )
    REFERENCES estacao_monitora ( id_estacao );

ALTER TABLE avistamento
    ADD CONSTRAINT fk_avista_especie FOREIGN KEY ( id_especie )
    REFERENCES especie ( id_especie );
