package br.com.fiap.main;

import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Executa o setup completo do banco de dados conforme o SQL de producao (v_final).
 * - Dropa tabelas existentes (ignora erros de "nao existe")
 * - Cria tabelas com constraints e foreign keys (sem sequences; IDs sao gerados por MAX+1)
 *
 * Execute este teste UMA VEZ antes de rodar os demais testes.
 */
public class TesteSetupBanco {

    public static void main(String[] args) {
        System.out.println("=== Iniciando setup do banco de dados ===\n");

        List<String> comandos = Arrays.asList(

            // ---- DROP TABLES (mesma ordem do SQL original) ----
            "DROP TABLE alerta CASCADE CONSTRAINTS",
            "DROP TABLE avistamento CASCADE CONSTRAINTS",
            "DROP TABLE especie CASCADE CONSTRAINTS",
            "DROP TABLE estacao_monitora CASCADE CONSTRAINTS",
            "DROP TABLE estacao_zona CASCADE CONSTRAINTS",
            "DROP TABLE leitura_telemetria CASCADE CONSTRAINTS",
            "DROP TABLE usuario_operador CASCADE CONSTRAINTS",
            "DROP TABLE zona_monitora CASCADE CONSTRAINTS",

            // ---- CREATE TABLE alerta ----
            "CREATE TABLE alerta (" +
            "  id_alerta        NUMBER(4)     NOT NULL, " +
            "  id_leitura       NUMBER(4)     NOT NULL, " +
            "  risco_alerta     VARCHAR2(5)   NOT NULL, " +
            "  desc_alerta      VARCHAR2(200) NOT NULL, " +
            "  horario_alerta   TIMESTAMP WITH LOCAL TIME ZONE NOT NULL, " +
            "  observa_alerta   VARCHAR2(200), " +
            "  conclusao_alerta VARCHAR2(1)   NOT NULL" +
            ")",
            "ALTER TABLE alerta ADD CONSTRAINT alerta_PK PRIMARY KEY (id_alerta)",
            "ALTER TABLE alerta ADD CONSTRAINT chk_risco_alerta CHECK (risco_alerta IN ('ALTO', 'MEDIO', 'BAIXO'))",
            "ALTER TABLE alerta ADD CONSTRAINT chk_conclusao_alerta CHECK (conclusao_alerta IN ('S', 'N'))",

            // ---- CREATE TABLE avistamento ----
            "CREATE TABLE avistamento (" +
            "  id_estacao     NUMBER(4) NOT NULL, " +
            "  id_especie     NUMBER(4) NOT NULL, " +
            "  id_avista      NUMBER    NOT NULL, " +
            "  horario_avista TIMESTAMP WITH LOCAL TIME ZONE NOT NULL, " +
            "  quant_avista   NUMBER(3) NOT NULL" +
            ")",
            "ALTER TABLE avistamento ADD CONSTRAINT avistamento_PK PRIMARY KEY (id_avista)",

            // ---- CREATE TABLE especie ----
            "CREATE TABLE especie (" +
            "  id_especie       NUMBER(4)     NOT NULL, " +
            "  nc_especie       VARCHAR2(100) NOT NULL, " +
            "  np_especie       VARCHAR2(100) NOT NULL, " +
            "  conserva_especie VARCHAR2(2)   NOT NULL, " +
            "  habitat_especie  VARCHAR2(60)  NOT NULL, " +
            "  desc_especie     VARCHAR2(200)" +
            ")",
            "ALTER TABLE especie ADD CONSTRAINT especie_PK PRIMARY KEY (id_especie)",
            "ALTER TABLE especie ADD CONSTRAINT chk_conserva_especie " +
            "  CHECK (conserva_especie IN ('LC', 'NT', 'VU', 'EN', 'CR'))",

            // ---- CREATE TABLE estacao_monitora ----
            "CREATE TABLE estacao_monitora (" +
            "  id_estacao   NUMBER(4)    NOT NULL, " +
            "  nome_estacao VARCHAR2(50) NOT NULL, " +
            "  lat_estacao  NUMBER(9,6)  NOT NULL, " +
            "  lon_estacao  NUMBER(9,6)  NOT NULL, " +
            "  tipo_estacao VARCHAR2(30) NOT NULL" +
            ")",
            "ALTER TABLE estacao_monitora ADD CONSTRAINT estacao_monitora_PK PRIMARY KEY (id_estacao)",
            "ALTER TABLE estacao_monitora ADD CONSTRAINT chk_tipo_estacao " +
            "  CHECK (tipo_estacao IN ('BOIA', 'SATELITE', 'SUBMARINA'))",
            "ALTER TABLE estacao_monitora ADD CONSTRAINT uq_nome_estacao UNIQUE (nome_estacao)",

            // ---- CREATE TABLE estacao_zona ----
            "CREATE TABLE estacao_zona (" +
            "  id_zona      NUMBER(4) NOT NULL, " +
            "  id_estacao   NUMBER(4) NOT NULL, " +
            "  data_vinculo DATE      NOT NULL" +
            ")",

            // ---- CREATE TABLE leitura_telemetria ----
            "CREATE TABLE leitura_telemetria (" +
            "  id_leitura           NUMBER(4)   NOT NULL, " +
            "  id_estacao           NUMBER(4)   NOT NULL, " +
            "  horario_leitura      TIMESTAMP WITH LOCAL TIME ZONE NOT NULL, " +
            "  sst                  NUMBER(5,2) NOT NULL, " +
            "  wave_height          NUMBER(5,2) NOT NULL, " +
            "  wave_period          NUMBER(5,2) NOT NULL, " +
            "  wind_speed           NUMBER(6,2) NOT NULL, " +
            "  wind_direction       NUMBER(5,2) NOT NULL, " +
            "  earthquake_magnitude NUMBER(4,2) NOT NULL, " +
            "  focal_depth          NUMBER(7,3) NOT NULL" +
            ")",
            "ALTER TABLE leitura_telemetria ADD CONSTRAINT leitura_telemetria_PK PRIMARY KEY (id_leitura)",

            // ---- CREATE TABLE usuario_operador ----
            "CREATE TABLE usuario_operador (" +
            "  id_opera    NUMBER(4)    NOT NULL, " +
            "  nm_opera    VARCHAR2(40) NOT NULL, " +
            "  email_opera VARCHAR2(60) NOT NULL, " +
            "  senha_opera VARCHAR2(50) NOT NULL" +
            ")",
            "ALTER TABLE usuario_operador ADD CONSTRAINT usuario_operador_PK PRIMARY KEY (id_opera)",
            "ALTER TABLE usuario_operador ADD CONSTRAINT uq_email_opera UNIQUE (email_opera)",

            // ---- CREATE TABLE zona_monitora ----
            "CREATE TABLE zona_monitora (" +
            "  id_zona   NUMBER(4)     NOT NULL, " +
            "  nome_zona VARCHAR2(100) NOT NULL, " +
            "  pais_zona VARCHAR2(60)  NOT NULL, " +
            "  desc_zona VARCHAR2(200)" +
            ")",
            "ALTER TABLE zona_monitora ADD CONSTRAINT zona_monitora_PK PRIMARY KEY (id_zona)",

            // ---- FOREIGN KEYS (mesmas do SQL original) ----
            "ALTER TABLE avistamento ADD CONSTRAINT especie_id_avista " +
            "  FOREIGN KEY (id_especie) REFERENCES especie (id_especie)",
            "ALTER TABLE estacao_zona ADD CONSTRAINT estacao_id_estazona " +
            "  FOREIGN KEY (id_estacao) REFERENCES estacao_monitora (id_estacao)",
            "ALTER TABLE leitura_telemetria ADD CONSTRAINT estacao_id_leitura " +
            "  FOREIGN KEY (id_estacao) REFERENCES estacao_monitora (id_estacao)",
            "ALTER TABLE alerta ADD CONSTRAINT leitura_id_alerta " +
            "  FOREIGN KEY (id_leitura) REFERENCES leitura_telemetria (id_leitura)",
            "ALTER TABLE avistamento ADD CONSTRAINT zona_id_avista " +
            "  FOREIGN KEY (id_estacao) REFERENCES estacao_monitora (id_estacao)",
            "ALTER TABLE estacao_zona ADD CONSTRAINT zona_id_estazona " +
            "  FOREIGN KEY (id_zona) REFERENCES zona_monitora (id_zona)"
        );

        try (Connection con = new ConexaoFactory().conexao();
             Statement stmt = con.createStatement()) {

            for (String sql : comandos) {
                try {
                    stmt.executeUpdate(sql);
                    System.out.println("OK: " + sql.substring(0, Math.min(60, sql.length())).trim() + "...");
                } catch (SQLException e) {
                    // Ignora erros de DROP quando o objeto nao existe (ORA-00942 tabela, ORA-02289 sequence)
                    int code = e.getErrorCode();
                    if (code == 942 || code == 2289 || code == 4043) {
                        System.out.println("IGNORADO (nao existia): " + sql.substring(0, Math.min(50, sql.length())).trim() + "...");
                    } else {
                        System.err.println("ERRO: " + sql.substring(0, Math.min(60, sql.length())).trim());
                        System.err.println("      " + e.getMessage());
                    }
                }
            }

            System.out.println("\n=== Setup concluido com sucesso! ===");
            System.out.println("Agora voce pode executar os demais testes.");

        } catch (Exception e) {
            System.err.println("Falha critica na conexao: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
