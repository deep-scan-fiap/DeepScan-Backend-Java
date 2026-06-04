package br.com.fiap.main;

import br.com.fiap.bo.AlertaBO;
import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.dao.LeituraTelemetriaDao;
import br.com.fiap.entities.Alerta;
import br.com.fiap.entities.EstacaoMonitora;
import br.com.fiap.entities.LeituraTelemetria;

import java.util.List;

public class TesteSelecionar {
    public static void main(String[] args) {

        System.out.println("=== Estacoes ===");
        EstacaoMonitoraDao estacaoDao = new EstacaoMonitoraDao();
        List<EstacaoMonitora> estacoes = estacaoDao.listarTodas();
        for (EstacaoMonitora e : estacoes) {
            System.out.println(e);
        }

        System.out.println("\n=== Leituras ===");
        LeituraTelemetriaDao leituraDao = new LeituraTelemetriaDao();
        List<LeituraTelemetria> leituras = leituraDao.listarTodas();
        for (LeituraTelemetria l : leituras) {
            System.out.println(l);
        }

        System.out.println("\n=== Alertas Pendentes ===");
        AlertaBO alertaBO = new AlertaBO();
        List<Alerta> alertas = alertaBO.listarPendentes();
        for (Alerta a : alertas) {
            System.out.println(a);
        }
    }
}
