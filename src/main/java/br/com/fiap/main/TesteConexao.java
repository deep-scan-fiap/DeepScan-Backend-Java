package br.com.fiap.main;

import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection con = new ConexaoFactory().conexao();
            System.out.println("Conexao bem-sucedida: " + con);
            con.close();
        } catch (Exception e) {
            System.out.println("Falha na conexao: " + e.getMessage());
        }
    }
}
