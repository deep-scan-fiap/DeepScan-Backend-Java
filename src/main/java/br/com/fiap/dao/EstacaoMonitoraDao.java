package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.EstacaoMonitora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstacaoMonitoraDao {

    public void inserir(EstacaoMonitora e) {
        String sql = "INSERT INTO ESTACAO_MONITORA (ID_ESTACAO, NOME_ESTACAO, LAT_ESTACAO, LON_ESTACAO, TIPO_ESTACAO) " +
                     "VALUES (SEQ_ESTACAO.NEXTVAL, ?, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_ESTACAO"})) {
            ps.setString(1, e.getNomeEstacao());
            ps.setDouble(2, e.getLatEstacao());
            ps.setDouble(3, e.getLonEstacao());
            ps.setString(4, e.getTipoEstacao());
            ps.executeUpdate();
            // Recupera o ID gerado pela sequence e atualiza o objeto
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    e.setIdEstacao(generatedKeys.getInt(1));
                }
            }
            System.out.println("EstacaoMonitora inserida com sucesso. ID: " + e.getIdEstacao());
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<EstacaoMonitora> listarTodas() {
        List<EstacaoMonitora> lista = new ArrayList<>();
        String sql = "SELECT * FROM ESTACAO_MONITORA ORDER BY ID_ESTACAO";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public EstacaoMonitora buscarPorId(int id) {
        String sql = "SELECT * FROM ESTACAO_MONITORA WHERE ID_ESTACAO = ?";
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

    public void atualizar(EstacaoMonitora e) {
        String sql = "UPDATE ESTACAO_MONITORA SET NOME_ESTACAO=?, LAT_ESTACAO=?, LON_ESTACAO=?, TIPO_ESTACAO=? " +
                     "WHERE ID_ESTACAO=?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNomeEstacao());
            ps.setDouble(2, e.getLatEstacao());
            ps.setDouble(3, e.getLonEstacao());
            ps.setString(4, e.getTipoEstacao());
            ps.setInt(5, e.getIdEstacao());
            ps.executeUpdate();
            System.out.println("EstacaoMonitora atualizada.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM ESTACAO_MONITORA WHERE ID_ESTACAO = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("EstacaoMonitora deletada.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private EstacaoMonitora mapear(ResultSet rs) throws SQLException {
        return new EstacaoMonitora(
                rs.getInt("ID_ESTACAO"),
                rs.getString("NOME_ESTACAO"),
                rs.getDouble("LAT_ESTACAO"),
                rs.getDouble("LON_ESTACAO"),
                rs.getString("TIPO_ESTACAO")
        );
    }
}
