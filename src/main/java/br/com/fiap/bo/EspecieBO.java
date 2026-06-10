package br.com.fiap.bo;

import br.com.fiap.dao.EspecieDao;
import br.com.fiap.entities.Especie;
import br.com.fiap.exceptions.DadoInvalidoException;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;

import java.util.Arrays;
import java.util.List;

public class EspecieBO {

    private EspecieDao dao = new EspecieDao();

    private static final List<String> CONSERVACOES_VALIDAS =
            Arrays.asList("LC", "NT", "VU", "EN", "CR");

    public Especie cadastrar(Especie especie) {
        validar(especie);
        dao.inserir(especie);
        return especie;
    }

    public Especie buscarPorId(int id) {
        Especie especie = dao.buscarPorId(id);
        if (especie == null)
            throw new RecursoNaoEncontradoException("Especie com ID " + id + " nao encontrada.");
        return especie;
    }

    public List<Especie> listarTodas() {
        return dao.listarTodas();
    }

    public Especie atualizar(int id, Especie especie) {
        Especie existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Especie com ID " + id + " nao encontrada.");

        validar(especie);
        especie.setIdEspecie(id);
        dao.atualizar(especie);
        return especie;
    }

    public void deletar(int id) {
        Especie existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Especie com ID " + id + " nao encontrada.");
        dao.deletar(id);
    }

    private void validar(Especie especie) {
        if (especie.getNcEspecie() == null || especie.getNcEspecie().isBlank())
            throw new DadoInvalidoException("Nome cientifico e obrigatorio.");

        if (especie.getNpEspecie() == null || especie.getNpEspecie().isBlank())
            throw new DadoInvalidoException("Nome popular e obrigatorio.");

        if (!CONSERVACOES_VALIDAS.contains(especie.getConservaEspecie()))
            throw new DadoInvalidoException(
                "Status de conservacao invalido. Use: LC, NT, VU, EN ou CR.");

        if (especie.getHabitatEspecie() == null || especie.getHabitatEspecie().isBlank())
            throw new DadoInvalidoException("Habitat e obrigatorio.");
    }
}
