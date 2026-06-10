package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.LeituraTelemetria;
import br.com.fiap.exceptions.PersistenciaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeituraTelemetriaDao {

    public void inserir(LeituraTelemetria l) {
        String sql = "INSERT INTO LEITURA_TELEMETRIA " +
                     "(ID_LEITURA, ID_ESTACAO, HORARIO_LEITURA, SST, WAVE_HEIGHT, WAVE_PERIOD, " +
                     "WIND_SPEED, WIND_DIRECTION, EARTHQUAKE_MAGNITUDE, FOCAL_DEPTH) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int novoId = proximoId(con, "LEITURA_TELEMETRIA", "ID_LEITURA");
            ps.setInt(1, novoId);
            ps.setInt(2, l.getIdEstacao());
            ps.setTimestamp(3, Timestamp.valueOf(l.getHorarioLeitura()));
            ps.setDouble(4, l.getSst());
            ps.setDouble(5, l.getWaveHeight());
            ps.setDouble(6, l.getWavePeriod());
            ps.setDouble(7, l.getWindSpeed());
            ps.setDouble(8, l.getWindDirection());
            ps.setDouble(9, l.getEarthquakeMagnitude());
            ps.setDouble(10, l.getFocalDepth());
            ps.executeUpdate();
            l.setIdLeitura(novoId);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao inserir leitura.", ex);
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

    public List<LeituraTelemetria> listarTodas() {
        List<LeituraTelemetria> lista = new ArrayList<>();
        String sql = "SELECT * FROM LEITURA_TELEMETRIA ORDER BY HORARIO_LEITURA DESC";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao listar leituras.", ex);
        }
        return lista;
    }

    public LeituraTelemetria buscarPorId(int id) {
        String sql = "SELECT * FROM LEITURA_TELEMETRIA WHERE ID_LEITURA = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao buscar leitura.", ex);
        }
        return null;
    }

    public List<LeituraTelemetria> listarPorEstacao(int idEstacao) {
        List<LeituraTelemetria> lista = new ArrayList<>();
        String sql = "SELECT * FROM LEITURA_TELEMETRIA WHERE ID_ESTACAO = ? ORDER BY HORARIO_LEITURA DESC";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstacao);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao listar leituras da estacao.", ex);
        }
        return lista;
    }

    public void deletar(int id) {
        String sql = "DELETE FROM LEITURA_TELEMETRIA WHERE ID_LEITURA = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            throw new PersistenciaException("Falha ao deletar leitura.", ex);
        }
    }

    private LeituraTelemetria mapear(ResultSet rs) throws SQLException {
        return new LeituraTelemetria(
                rs.getInt("ID_LEITURA"),
                rs.getInt("ID_ESTACAO"),
                rs.getTimestamp("HORARIO_LEITURA").toLocalDateTime(),
                rs.getDouble("SST"),
                rs.getDouble("WAVE_HEIGHT"),
                rs.getDouble("WAVE_PERIOD"),
                rs.getDouble("WIND_SPEED"),
                rs.getDouble("WIND_DIRECTION"),
                rs.getDouble("EARTHQUAKE_MAGNITUDE"),
                rs.getDouble("FOCAL_DEPTH")
        );
    }
}
