package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Especie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecieDao {

    public void inserir(Especie e) {
        String sql = "INSERT INTO ESPECIE (ID_ESPECIE, NC_ESPECIE, NP_ESPECIE, CONSERVA_ESPECIE, HABITAT_ESPECIE, DESC_ESPECIE) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int novoId = proximoId(con, "ESPECIE", "ID_ESPECIE");
            ps.setInt(1, novoId);
            ps.setString(2, e.getNcEspecie());
            ps.setString(3, e.getNpEspecie());
            ps.setString(4, e.getConservaEspecie());
            ps.setString(5, e.getHabitatEspecie());
            ps.setString(6, e.getDescEspecie());
            ps.executeUpdate();
            e.setIdEspecie(novoId);
            System.out.println("Especie inserida. ID: " + e.getIdEspecie());
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

    public List<Especie> listarTodas() {
        List<Especie> lista = new ArrayList<>();
        String sql = "SELECT * FROM ESPECIE ORDER BY NC_ESPECIE";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public Especie buscarPorId(int id) {
        String sql = "SELECT * FROM ESPECIE WHERE ID_ESPECIE = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void atualizar(Especie e) {
        String sql = "UPDATE ESPECIE SET NC_ESPECIE=?, NP_ESPECIE=?, CONSERVA_ESPECIE=?, " +
                     "HABITAT_ESPECIE=?, DESC_ESPECIE=? WHERE ID_ESPECIE=?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNcEspecie());
            ps.setString(2, e.getNpEspecie());
            ps.setString(3, e.getConservaEspecie());
            ps.setString(4, e.getHabitatEspecie());
            ps.setString(5, e.getDescEspecie());
            ps.setInt(6, e.getIdEspecie());
            ps.executeUpdate();
            System.out.println("Especie atualizada.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM ESPECIE WHERE ID_ESPECIE = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Especie deletada.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Especie mapear(ResultSet rs) throws SQLException {
        return new Especie(
                rs.getInt("ID_ESPECIE"),
                rs.getString("NC_ESPECIE"),
                rs.getString("NP_ESPECIE"),
                rs.getString("CONSERVA_ESPECIE"),
                rs.getString("HABITAT_ESPECIE"),
                rs.getString("DESC_ESPECIE")
        );
    }
}
