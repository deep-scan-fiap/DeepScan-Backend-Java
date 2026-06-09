package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Alerta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDao {

    public void inserir(Alerta a) {
        String sql = "INSERT INTO ALERTA (ID_ALERTA, ID_LEITURA, RISCO_ALERTA, DESC_ALERTA, " +
                     "HORARIO_ALERTA, OBSERVA_ALERTA, CONCLUSAO_ALERTA) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int novoId = proximoId(con, "ALERTA", "ID_ALERTA");
            ps.setInt(1, novoId);
            ps.setInt(2, a.getIdLeitura());
            ps.setString(3, a.getRiscoAlerta());
            ps.setString(4, a.getDescAlerta());
            ps.setTimestamp(5, Timestamp.valueOf(a.getHorarioAlerta()));
            ps.setString(6, a.getObservaAlerta());
            ps.setString(7, a.getConclusaoAlerta());
            ps.executeUpdate();
            a.setIdAlerta(novoId);
            System.out.println("Alerta inserido com sucesso.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private int proximoId(Connection con, String tabela, String coluna) throws SQLException {
        String sql = "SELECT NVL(MAX(" + coluna + "), 0) + 1 FROM " + tabela;
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public List<Alerta> listarTodos() {
        List<Alerta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ALERTA ORDER BY HORARIO_ALERTA DESC";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Alerta> listarNaoResolvidos() {
        List<Alerta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ALERTA WHERE CONCLUSAO_ALERTA = 'N' ORDER BY HORARIO_ALERTA DESC";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void resolverAlerta(int id) {
        String sql = "UPDATE ALERTA SET CONCLUSAO_ALERTA = 'S' WHERE ID_ALERTA = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Alerta " + id + " marcado como resolvido.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM ALERTA WHERE ID_ALERTA = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Alerta deletado.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Alerta mapear(ResultSet rs) throws SQLException {
        return new Alerta(
                rs.getInt("ID_ALERTA"),
                rs.getInt("ID_LEITURA"),
                rs.getString("RISCO_ALERTA"),
                rs.getString("DESC_ALERTA"),
                rs.getTimestamp("HORARIO_ALERTA").toLocalDateTime(),
                rs.getString("OBSERVA_ALERTA"),
                rs.getString("CONCLUSAO_ALERTA")
        );
    }
}
