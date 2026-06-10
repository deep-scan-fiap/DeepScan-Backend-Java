package br.com.fiap.bo;

import br.com.fiap.dao.LeituraTelemetriaDao;
import br.com.fiap.entities.LeituraTelemetria;
import br.com.fiap.exceptions.DadoInvalidoException;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;

import java.util.List;

public class LeituraTelemetriaBO {

    private LeituraTelemetriaDao dao = new LeituraTelemetriaDao();
    private AlertaBO alertaBO        = new AlertaBO();

    public LeituraTelemetria registrar(LeituraTelemetria leitura) {
        if (leitura.getIdEstacao() <= 0)
            throw new DadoInvalidoException("ID da estacao invalido.");

        if (leitura.getSst() < -5 || leitura.getSst() > 50)
            throw new DadoInvalidoException("SST invalida. Deve estar entre -5 e 50 C.");

        if (leitura.getWaveHeight() < 0 || leitura.getWaveHeight() > 50)
            throw new DadoInvalidoException("Altura de onda invalida. Deve estar entre 0 e 50 m.");

        if (leitura.getEarthquakeMagnitude() < 0 || leitura.getEarthquakeMagnitude() > 10)
            throw new DadoInvalidoException("Magnitude invalida. Deve estar entre 0 e 10.");

        if (leitura.getFocalDepth() < 0)
            throw new DadoInvalidoException("Profundidade focal nao pode ser negativa.");

        dao.inserir(leitura);
        alertaBO.gerarAlertaAutomatico(leitura);
        return leitura;
    }

    public LeituraTelemetria buscarPorId(int id) {
        LeituraTelemetria leitura = dao.buscarPorId(id);
        if (leitura == null)
            throw new RecursoNaoEncontradoException("Leitura com ID " + id + " nao encontrada.");
        return leitura;
    }

    public List<LeituraTelemetria> listarTodas() {
        return dao.listarTodas();
    }

    public List<LeituraTelemetria> listarPorEstacao(int idEstacao) {
        if (idEstacao <= 0)
            throw new DadoInvalidoException("ID da estacao invalido.");
        return dao.listarPorEstacao(idEstacao);
    }

    public void deletar(int id) {
        LeituraTelemetria existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Leitura com ID " + id + " nao encontrada.");
        dao.deletar(id);
    }
}
