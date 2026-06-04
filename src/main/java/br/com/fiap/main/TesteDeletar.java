package br.com.fiap.main;

import br.com.fiap.bo.AlertaBO;
import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.dao.LeituraTelemetriaDao;

public class TesteDeletar {
    public static void main(String[] args) {

        // deletar alerta
        AlertaBO alertaBO = new AlertaBO();
        alertaBO.deletarAlerta(1);

        // deletar leitura
        LeituraTelemetriaDao leituraDao = new LeituraTelemetriaDao();
        leituraDao.deletar(1);

        // deletar estacao
        EstacaoMonitoraDao estacaoDao = new EstacaoMonitoraDao();
        estacaoDao.deletar(1);
    }
}
