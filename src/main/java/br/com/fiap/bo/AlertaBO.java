package br.com.fiap.bo;

import br.com.fiap.dao.AlertaDao;
import br.com.fiap.entities.Alerta;
import br.com.fiap.entities.LeituraTelemetria;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;

import java.time.LocalDateTime;
import java.util.List;

public class AlertaBO {

    private AlertaDao dao = new AlertaDao();

    /**
     * Gera um alerta automaticamente a partir de uma leitura de telemetria.
     */
    public void gerarAlertaAutomatico(LeituraTelemetria leitura) {
        String risco = calcularRisco(leitura);
        if (risco == null) return;

        Alerta alerta = new Alerta(
                0,
                leitura.getIdLeitura(),
                risco,
                montarDescricao(leitura, risco),
                LocalDateTime.now(),
                null,
                "N"
        );
        dao.inserir(alerta);
    }

    /**
     * Calcula o nivel de risco com base nos limites dos sensores. Retorna
     * "ALTO", "MEDIO", "BAIXO" ou null (sem risco). Limite VARCHAR2(5) no banco.
     */
    private String calcularRisco(LeituraTelemetria l) {
        if (l.getEarthquakeMagnitude() >= 5.5) return "ALTO";
        if (l.getWaveHeight() >= 3.5 && l.getWindSpeed() >= 80) return "ALTO";
        if (l.getWindSpeed() >= 60 && l.getSst() >= 26) return "MEDIO";
        if (l.getWaveHeight() >= 2.5) return "MEDIO";
        if (l.getEarthquakeMagnitude() >= 3.0) return "BAIXO";
        if (l.getWaveHeight() >= 1.5) return "BAIXO";
        return null;
    }

    private String montarDescricao(LeituraTelemetria l, String risco) {
        return String.format(
                "Risco %s detectado. SST: %.1fC | Onda: %.1fm | Vento: %.1f km/h | Magnitude: %.1f",
                risco, l.getSst(), l.getWaveHeight(), l.getWindSpeed(), l.getEarthquakeMagnitude()
        );
    }

    public List<Alerta> listarAlertas() {
        return dao.listarTodos();
    }

    public List<Alerta> listarPendentes() {
        return dao.listarNaoResolvidos();
    }

    public void resolverAlerta(int id) {
        Alerta existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Alerta com ID " + id + " nao encontrado.");
        dao.resolverAlerta(id);
    }

    public void deletarAlerta(int id) {
        Alerta existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Alerta com ID " + id + " nao encontrado.");
        dao.deletar(id);
    }
}
