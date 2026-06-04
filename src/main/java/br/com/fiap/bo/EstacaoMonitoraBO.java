package br.com.fiap.bo;

import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.entities.EstacaoMonitora;

import java.util.List;

public class EstacaoMonitoraBO {

    private EstacaoMonitoraDao dao = new EstacaoMonitoraDao();

    public EstacaoMonitora cadastrar(EstacaoMonitora estacao) {
        if (estacao.getNomeEstacao() == null || estacao.getNomeEstacao().isBlank())
            throw new IllegalArgumentException("Nome da estacao e obrigatorio.");

        if (!estacao.getTipoEstacao().equals("Boia") &&
            !estacao.getTipoEstacao().equals("Satelite") &&
            !estacao.getTipoEstacao().equals("Submarina"))
            throw new IllegalArgumentException("Tipo invalido. Use: Boia, Satelite ou Submarina.");

        if (estacao.getLatEstacao() < -90 || estacao.getLatEstacao() > 90)
            throw new IllegalArgumentException("Latitude invalida. Deve estar entre -90 e 90.");

        if (estacao.getLonEstacao() < -180 || estacao.getLonEstacao() > 180)
            throw new IllegalArgumentException("Longitude invalida. Deve estar entre -180 e 180.");

        dao.inserir(estacao);
        return estacao;
    }

    public EstacaoMonitora buscarPorId(int id) {
        EstacaoMonitora estacao = dao.buscarPorId(id);
        if (estacao == null)
            throw new IllegalArgumentException("Estacao com ID " + id + " nao encontrada.");
        return estacao;
    }

    public List<EstacaoMonitora> listarTodas() {
        return dao.listarTodas();
    }

    public EstacaoMonitora atualizar(int id, EstacaoMonitora estacao) {
        EstacaoMonitora existente = dao.buscarPorId(id);
        if (existente == null)
            throw new IllegalArgumentException("Estacao com ID " + id + " nao encontrada.");

        if (estacao.getNomeEstacao() == null || estacao.getNomeEstacao().isBlank())
            throw new IllegalArgumentException("Nome da estacao e obrigatorio.");

        estacao.setIdEstacao(id);
        dao.atualizar(estacao);
        return estacao;
    }

    public void deletar(int id) {
        EstacaoMonitora existente = dao.buscarPorId(id);
        if (existente == null)
            throw new IllegalArgumentException("Estacao com ID " + id + " nao encontrada.");
        dao.deletar(id);
    }
}
