package br.com.fiap.mapper;

import br.com.fiap.exceptions.PersistenciaException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PersistenciaMapper implements ExceptionMapper<PersistenciaException> {
    @Override
    public Response toResponse(PersistenciaException e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErroResposta("Erro interno no servidor. Tente novamente."))
                .build();
    }
}
