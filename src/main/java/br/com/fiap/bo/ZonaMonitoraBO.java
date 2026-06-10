package br.com.fiap.bo;

import br.com.fiap.dao.ZonaMonitoraDao;
import br.com.fiap.entities.ZonaMonitora;
import br.com.fiap.exceptions.DadoInvalidoException;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;

import java.util.List;

public class ZonaMonitoraBO {

    private ZonaMonitoraDao dao = new ZonaMonitoraDao();

    public ZonaMonitora cadastrar(ZonaMonitora zona) {
        validar(zona);
        dao.inserir(zona);
        return zona;
    }

    public List<ZonaMonitora> listarTodas() {
        return dao.listarTodas();
    }

    public void deletar(int id) {
        ZonaMonitora existente = dao.buscarPorId(id);
        if (existente == null)
            throw new RecursoNaoEncontradoException("Zona com ID " + id + " nao encontrada.");
        dao.deletar(id);
    }

    private void validar(ZonaMonitora z) {
        if (z.getNomeZona() == null || z.getNomeZona().isBlank())
            throw new DadoInvalidoException("Nome da zona e obrigatorio.");

        if (z.getPaisZona() == null || z.getPaisZona().isBlank())
            throw new DadoInvalidoException("Pais da zona e obrigatorio.");
    }
}
