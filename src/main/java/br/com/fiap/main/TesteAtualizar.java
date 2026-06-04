package br.com.fiap.main;

import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.entities.EstacaoMonitora;

public class TesteAtualizar {
    public static void main(String[] args) {

        EstacaoMonitoraDao estacaoDao = new EstacaoMonitoraDao();

        // busca a estacao existente
        EstacaoMonitora estacao = estacaoDao.buscarPorId(1);

        if (estacao != null) {
            // atualiza os dados — tipo deve ser 'Boia', 'Satelite' ou 'Submarina'
            estacao.setNomeEstacao("Boia DART-01 Atualizada");
            estacao.setLatEstacao(-24.0);
            estacao.setLonEstacao(-44.0);
            estacao.setTipoEstacao("Boia");

            estacaoDao.atualizar(estacao);
            System.out.println("Estacao apos atualizacao: " + estacao);
        } else {
            System.out.println("Estacao com ID 1 nao encontrada.");
        }
    }
}
