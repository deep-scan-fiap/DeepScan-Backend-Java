package br.com.fiap.main;

import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.dao.LeituraTelemetriaDao;
import br.com.fiap.entities.EstacaoMonitora;
import br.com.fiap.entities.LeituraTelemetria;

import java.time.LocalDateTime;

public class TesteInserir {
    public static void main(String[] args) {

        // insere uma estacao — tipo deve ser 'BOIA', 'SATELITE' ou 'SUBMARINA'
        EstacaoMonitoraDao estacaoDao = new EstacaoMonitoraDao();
        EstacaoMonitora estacao = new EstacaoMonitora(
                0, "Boia DART-01", -23.5, -43.2, "BOIA"
        );
        estacaoDao.inserir(estacao);
        System.out.println("Estacao inserida: " + estacao);

        // insere uma leitura de telemetria vinculada a estacao acima
        LeituraTelemetriaDao leituraDao = new LeituraTelemetriaDao();
        LeituraTelemetria leitura = new LeituraTelemetria(
                0, estacao.getIdEstacao(), LocalDateTime.now(),
                29.5, 3.2, 12.0,
                75.0, 180.0, 4.5, 55.0
        );
        leituraDao.inserir(leitura);
        System.out.println("Leitura inserida: " + leitura);
    }
}
