package br.com.fiap.main;

import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.dao.LeituraTelemetriaDao;
import br.com.fiap.entities.EstacaoMonitora;
import br.com.fiap.entities.LeituraTelemetria;

public class TesteSelecionarPorCodigo {
    public static void main(String[] args) {

        int idEstacao = 1;
        EstacaoMonitoraDao estacaoDao = new EstacaoMonitoraDao();
        EstacaoMonitora estacao = estacaoDao.buscarPorId(idEstacao);
        if (estacao != null) {
            System.out.println("Estacao encontrada: " + estacao);
        }

        int idLeitura = 1;
        LeituraTelemetriaDao leituraDao = new LeituraTelemetriaDao();
        LeituraTelemetria leitura = leituraDao.buscarPorId(idLeitura);
        if (leitura != null) {
            System.out.println("Leitura encontrada: " + leitura);
        }
    }
}
