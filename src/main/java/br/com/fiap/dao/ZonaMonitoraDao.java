package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.EstacaoZona;
import br.com.fiap.entities.ZonaMonitora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZonaMonitoraDao {

    // ======== CRUD ZonaMonitora ========

    public void inserir(ZonaMonitora z) {
        String sql = "INSERT INTO ZONA_MONITORA (ID_ZONA, NOME_ZONA, PAIS_ZONA, DESC_ZONA) " +
                     "VALUES (SEQ_ZONA.NEXTVAL, ?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, z.getNomeZona());
            ps.setString(2, z.getPaisZona());
            ps.setString(3, z.getDescZona());
            ps.executeUpdate();
            System.out.println("ZonaMonitora inserida com sucesso.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<ZonaMonitora> listarTodas() {
        List<ZonaMonitora> lista = new ArrayList<>();
        String sql = "SELECT * FROM ZONA_MONITORA ORDER BY NOME_ZONA";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new ZonaMonitora(
                        rs.getInt("ID_ZONA"),
                        rs.getString("NOME_ZONA"),
                        rs.getString("PAIS_ZONA"),
                        rs.getString("DESC_ZONA")
                ));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void deletar(int idZona) {
        String sql = "DELETE FROM ZONA_MONITORA WHERE ID_ZONA = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idZona);
            ps.executeUpdate();
            System.out.println("ZonaMonitora deletada.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // ======== CRUD EstacaoZona (tabela associativa) ========

    public void vincularEstacao(EstacaoZona ez) {
        String sql = "INSERT INTO ESTACAO_ZONA (ID_ZONA, ID_ESTACAO, DATA_VINCULO) VALUES (?, ?, ?)";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ez.getIdZona());
            ps.setInt(2, ez.getIdEstacao());
            ps.setDate(3, Date.valueOf(ez.getDataVinculo()));
            ps.executeUpdate();
            System.out.println("EstacaoZona vinculada com sucesso.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<EstacaoZona> listarVinculos() {
        List<EstacaoZona> lista = new ArrayList<>();
        String sql = "SELECT * FROM ESTACAO_ZONA ORDER BY ID_ZONA, ID_ESTACAO";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new EstacaoZona(
                        rs.getInt("ID_ZONA"),
                        rs.getInt("ID_ESTACAO"),
                        rs.getDate("DATA_VINCULO").toLocalDate()
                ));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void removerVinculo(int idZona, int idEstacao) {
        String sql = "DELETE FROM ESTACAO_ZONA WHERE ID_ZONA = ? AND ID_ESTACAO = ?";
        try (Connection con = new ConexaoFactory().conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idZona);
            ps.setInt(2, idEstacao);
            ps.executeUpdate();
            System.out.println("Vinculo EstacaoZona removido.");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
