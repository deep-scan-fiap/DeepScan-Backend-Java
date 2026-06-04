package br.com.fiap.main;

import br.com.fiap.bo.AlertaBO;
import br.com.fiap.dao.LeituraTelemetriaDao;
import br.com.fiap.entities.LeituraTelemetria;

import java.time.LocalDateTime;

public class TesteAlertaAutomatico {
    public static void main(String[] args) {

        LeituraTelemetriaDao leituraDao = new LeituraTelemetriaDao();
        AlertaBO alertaBO = new AlertaBO();

        // simula leitura com magnitude alta = risco ALTO
        LeituraTelemetria leituraTsunami = new LeituraTelemetria(
                0, 1, LocalDateTime.now(),
                27.0, 1.5, 9.0,
                40.0, 90.0, 7.5, 30.0
        );

        // insere no banco — o ID e preenchido automaticamente no objeto
        leituraDao.inserir(leituraTsunami);
        System.out.println("Leitura registrada: " + leituraTsunami);

        // gera alerta usando o ID real da leitura
        alertaBO.gerarAlertaAutomatico(leituraTsunami);

        System.out.println("\n=== Alertas pendentes ===");
        alertaBO.listarPendentes().forEach(System.out::println);
    }
}
