package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.LeituraTelemetria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeituraTelemetriaDao {

    public void inserir(LeituraTelemetria l) {
        String sql = "INSERT INTO LEITURA_TELEMETRIA " +
                     "(ID_LEITURA, ID_ESTACAO, HORARIO_LEITURA, SST, WAVE_HEIGHT, WAVE_PERIOD, " +
                     "WIND_SPEED, WIND_DIRECTION, EARTHQUAKE_MAGNITUDE, FOCAL_DEPTH) " +
                     "VALUES (SEQ_LEITURA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_LEITURA"})) {
            ps.setInt(1, l.getIdEstacao());
            ps.setTimestamp(2, Timestamp.valueOf(l.getHorarioLeitura()));
            ps.setDouble(3, l.getSst());
            ps.setDouble(4, l.getWaveHeight());
            ps.setDouble(5, l.getWavePeriod());
            ps.setDouble(6, l.getWindSpeed());
            ps.setDouble(7, l.getWindDirection());
            ps.setDouble(8, l.getEarthquakeMagnitude());
            ps.setDouble(9, l.getFocalDepth());
            ps.executeUpdate();
            // Recupera o ID gerado pela sequence e atualiza o objeto
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    l.setIdLeitura(generatedKeys.getInt(1));
                }
            }
            System.out.println("LeituraTelemetria inserida com sucesso. ID: " + l.getIdLeitura());
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
        return lista;
    }

    public void deletar(int id) {
        String sql = "DELETE FROM LEITURA_TELEMETRIA WHERE ID_LEITURA = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("LeituraTelemetria deletada.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
