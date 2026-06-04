package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Avistamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvistamentoDao {

    public void inserir(Avistamento a) {
        String sql = "INSERT INTO AVISTAMENTO (ID_AVISTA, ID_ESTACAO, ID_ESPECIE, HORARIO_AVISTA, QUANT_AVISTA) " +
                     "VALUES (SEQ_AVISTAMENTO.NEXTVAL, ?, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_AVISTA"})) {
            ps.setInt(1, a.getIdEstacao());
            ps.setInt(2, a.getIdEspecie());
            ps.setTimestamp(3, Timestamp.valueOf(a.getHorarioAvista()));
            ps.setInt(4, a.getQuantAvista());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) a.setIdAvista(keys.getInt(1));
            }
            System.out.println("Avistamento inserido. ID: " + a.getIdAvista());
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<Avistamento> listarTodos() {
        List<Avistamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM AVISTAMENTO ORDER BY HORARIO_AVISTA DESC";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public Avistamento buscarPorId(int id) {
        String sql = "SELECT * FROM AVISTAMENTO WHERE ID_AVISTA = ?";
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

    public List<Avistamento> listarPorEspecie(int idEspecie) {
        List<Avistamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM AVISTAMENTO WHERE ID_ESPECIE = ? ORDER BY HORARIO_AVISTA DESC";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEspecie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void atualizar(Avistamento a) {
        String sql = "UPDATE AVISTAMENTO SET ID_ESTACAO=?, ID_ESPECIE=?, HORARIO_AVISTA=?, QUANT_AVISTA=? " +
                     "WHERE ID_AVISTA=?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, a.getIdEstacao());
            ps.setInt(2, a.getIdEspecie());
            ps.setTimestamp(3, Timestamp.valueOf(a.getHorarioAvista()));
            ps.setInt(4, a.getQuantAvista());
            ps.setInt(5, a.getIdAvista());
            ps.executeUpdate();
            System.out.println("Avistamento atualizado.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM AVISTAMENTO WHERE ID_AVISTA = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Avistamento deletado.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Avistamento mapear(ResultSet rs) throws SQLException {
        return new Avistamento(
                rs.getInt("ID_AVISTA"),
                rs.getInt("ID_ESTACAO"),
                rs.getInt("ID_ESPECIE"),
                rs.getTimestamp("HORARIO_AVISTA").toLocalDateTime(),
                rs.getInt("QUANT_AVISTA")
        );
    }
}
