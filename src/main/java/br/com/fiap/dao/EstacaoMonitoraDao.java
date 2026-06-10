package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.EstacaoMonitora;
import br.com.fiap.exceptions.PersistenciaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstacaoMonitoraDao {

    public void inserir(EstacaoMonitora e) {
        String sql = "INSERT INTO ESTACAO_MONITORA (ID_ESTACAO, NOME_ESTACAO, LAT_ESTACAO, LON_ESTACAO, TIPO_ESTACAO) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int novoId = proximoId(con, "ESTACAO_MONITORA", "ID_ESTACAO");
            ps.setInt(1, novoId);
            ps.setString(2, e.getNomeEstacao());
            ps.setDouble(3, e.getLatEstacao());
            ps.setDouble(4, e.getLonEstacao());
            ps.setString(5, e.getTipoEstacao());
            ps.executeUpdate();
            e.setIdEstacao(novoId);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao inserir estacao.", ex);
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
            throw new PersistenciaException("Falha ao listar estacoes.", ex);
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
            throw new PersistenciaException("Falha ao buscar estacao.", ex);
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
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao atualizar estacao.", ex);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM ESTACAO_MONITORA WHERE ID_ESTACAO = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao deletar estacao.", ex);
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
