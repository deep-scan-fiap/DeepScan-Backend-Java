package br.com.fiap.bo;

import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.entities.EstacaoMonitora;
import br.com.fiap.exceptions.DadoInvalidoException;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;

import java.text.Normalizer;
import java.util.List;

public class EstacaoMonitoraBO {

    private EstacaoMonitoraDao dao = new EstacaoMonitoraDao();

    public EstacaoMonitora cadastrar(EstacaoMonitora estacao) {
        validar(estacao);
        dao.inserir(estacao);
        return estacao;
    }

    public EstacaoMonitora buscarPorId(int id) {
        EstacaoMonitora estacao = dao.buscarPorId(id);
        if (estacao == null)
            throw new RecursoNaoEncontradoException("Estacao com ID " + id + " nao encontrada.");
        return estacao;
    }

    public List<EstacaoMonitora> listarTodas() {
        return dao.listarTodas();
    }

    public EstacaoMonitora atualizar(int id, EstacaoMonitora estacao) {
        EstacaoMonitora existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Estacao com ID " + id + " nao encontrada.");

        validar(estacao);
        estacao.setIdEstacao(id);
        dao.atualizar(estacao);
        return estacao;
    }

    public void deletar(int id) {
        EstacaoMonitora existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Estacao com ID " + id + " nao encontrada.");
        dao.deletar(id);
    }

    private void validar(EstacaoMonitora estacao) {
        if (estacao.getNomeEstacao() == null || estacao.getNomeEstacao().isBlank())
            throw new DadoInvalidoException("Nome da estacao e obrigatorio.");

        String tipo = normalizarTipo(estacao.getTipoEstacao());
        if (!tipo.equals("BOIA") && !tipo.equals("SATELITE") && !tipo.equals("SUBMARINA"))
            throw new DadoInvalidoException("Tipo invalido. Use: BOIA, SATELITE ou SUBMARINA.");
        estacao.setTipoEstacao(tipo);

        if (estacao.getLatEstacao() < -90 || estacao.getLatEstacao() > 90)
            throw new DadoInvalidoException("Latitude invalida. Deve estar entre -90 e 90.");

        if (estacao.getLonEstacao() < -180 || estacao.getLonEstacao() > 180)
            throw new DadoInvalidoException("Longitude invalida. Deve estar entre -180 e 180.");
    }

    private String normalizarTipo(String tipo) {
        if (tipo == null)
            throw new DadoInvalidoException("Tipo da estacao e obrigatorio.");
        String semAcento = Normalizer.normalize(tipo, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return semAcento.trim().toUpperCase();
    }
}
