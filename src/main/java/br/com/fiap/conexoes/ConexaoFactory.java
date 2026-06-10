package br.com.fiap.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abre conexoes JDBC com o Oracle. Os parametros sao lidos das variaveis
 * de ambiente DB_URL, DB_USER e DB_PASSWORD (configuradas como secrets
 * do GitHub Actions e injetadas no container pelo workflow de deploy).
 *
 * Para desenvolvimento local, defina-as antes de rodar a aplicacao, ex:
 *   export DB_URL="jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl"
 *   export DB_USER="RMxxxxxx"
 *   export DB_PASSWORD="..."
 */
public class ConexaoFactory {

    public Connection conexao() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        String url      = exigir("DB_URL");
        String usuario  = exigir("DB_USER");
        String senha    = exigir("DB_PASSWORD");

        return DriverManager.getConnection(url, usuario, senha);
    }

    private String exigir(String nome) {
        String valor = System.getenv(nome);
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                "Variavel de ambiente " + nome + " nao definida. " +
                "Configure DB_URL, DB_USER e DB_PASSWORD antes de iniciar a aplicacao.");
        }
        return valor;
    }
}
