package br.com.fiap.bo;

import br.com.fiap.dao.AlertaDao;
import br.com.fiap.entities.Alerta;
import br.com.fiap.entities.LeituraTelemetria;

import java.time.LocalDateTime;
import java.util.List;

public class AlertaBO {

    private AlertaDao dao = new AlertaDao();

    /**
     * Gera um alerta automaticamente a partir de uma leitura de telemetria.
     * O nivel de risco e calculado com base nos limites dos sensores.
     */
    public void gerarAlertaAutomatico(LeituraTelemetria leitura) {
        String risco = calcularRisco(leitura);

        if (risco == null) {
            System.out.println("Sem risco detectado. Nenhum alerta gerado.");
            return;
        }

        String descricao = montarDescricao(leitura, risco);

        Alerta alerta = new Alerta(
                0,
                leitura.getIdLeitura(),
                risco,
                descricao,
                LocalDateTime.now(),
                null,
                "N"
        );

        dao.inserir(alerta);
        System.out.println("Alerta gerado: " + risco + " | " + descricao);
    }

    /**
     * Calcula o nivel de risco com base nos limites dos sensores da leitura.
     * Retorna: "ALTO", "MEDIO", "BAIXO" ou null (sem risco)
     * Os valores devem ter no maximo 5 caracteres (VARCHAR2(5) no banco).
     */
    private String calcularRisco(LeituraTelemetria l) {
        // Risco ALTO: sismo >= 5.5 ou onda >= 3.5m com vento >= 80 km/h
        if (l.getEarthquakeMagnitude() >= 5.5) return "ALTO";
        if (l.getWaveHeight() >= 3.5 && l.getWindSpeed() >= 80) return "ALTO";

        // Risco MEDIO: ciclone provavel ou ressaca severa
        if (l.getWindSpeed() >= 60 && l.getSst() >= 26) return "MEDIO";
        if (l.getWaveHeight() >= 2.5) return "MEDIO";

        // Risco BAIXO: condicoes elevadas mas ainda dentro do limite
        if (l.getEarthquakeMagnitude() >= 3.0) return "BAIXO";
        if (l.getWaveHeight() >= 1.5) return "BAIXO";

        return null; // sem risco, nao gera alerta
    }

    private String montarDescricao(LeituraTelemetria l, String risco) {
        return String.format(
                "Risco %s detectado. SST: %.1fC | Onda: %.1fm | Vento: %.1f km/h | Magnitude: %.1f",
                risco,
                l.getSst(),
                l.getWaveHeight(),
                l.getWindSpeed(),
                l.getEarthquakeMagnitude()
        );
    }

    public List<Alerta> listarAlertas() {
        return dao.listarTodos();
    }

    public List<Alerta> listarPendentes() {
        return dao.listarNaoResolvidos();
    }

    public void resolverAlerta(int id) {
        dao.resolverAlerta(id);
    }

    public void deletarAlerta(int id) {
        dao.deletar(id);
    }
}
