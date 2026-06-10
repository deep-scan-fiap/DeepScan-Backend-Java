package br.com.fiap.bo;

import br.com.fiap.dao.AvistamentoDao;
import br.com.fiap.entities.Avistamento;
import br.com.fiap.exceptions.DadoInvalidoException;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;

import java.util.List;

public class AvistamentoBO {

    private AvistamentoDao dao = new AvistamentoDao();

    public Avistamento cadastrar(Avistamento avistamento) {
        validar(avistamento);
        dao.inserir(avistamento);
        return avistamento;
    }

    public Avistamento buscarPorId(int id) {
        Avistamento avistamento = dao.buscarPorId(id);
        if (avistamento == null)
            throw new RecursoNaoEncontradoException("Avistamento com ID " + id + " nao encontrado.");
        return avistamento;
    }

    public List<Avistamento> listarTodos() {
        return dao.listarTodos();
    }

    public List<Avistamento> listarPorEspecie(int idEspecie) {
        if (idEspecie <= 0)
            throw new DadoInvalidoException("ID da especie invalido.");
        return dao.listarPorEspecie(idEspecie);
    }

    public Avistamento atualizar(int id, Avistamento avistamento) {
        Avistamento existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Avistamento com ID " + id + " nao encontrado.");

        validar(avistamento);
        avistamento.setIdAvista(id);
        dao.atualizar(avistamento);
        return avistamento;
    }

    public void deletar(int id) {
        Avistamento existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Avistamento com ID " + id + " nao encontrado.");
        dao.deletar(id);
    }

    private void validar(Avistamento a) {
        if (a.getIdEstacao() <= 0)
            throw new DadoInvalidoException("ID da estacao invalido.");

        if (a.getIdEspecie() <= 0)
            throw new DadoInvalidoException("ID da especie invalido.");

        if (a.getHorarioAvista() == null)
            throw new DadoInvalidoException("Horario do avistamento e obrigatorio.");

        if (a.getQuantAvista() <= 0)
            throw new DadoInvalidoException("Quantidade deve ser maior que zero.");
    }
}
