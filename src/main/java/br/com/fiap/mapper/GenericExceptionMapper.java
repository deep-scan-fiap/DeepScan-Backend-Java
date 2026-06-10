package br.com.fiap.mapper;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Captura qualquer Throwable nao tratado por mappers especificos
 * e responde 500 com corpo padronizado {"erro": "..."}.
 * WebApplicationException (404, 405 etc do JAX-RS) e re-emitido sem alteracao
 * para que o status original seja preservado.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
        if (e instanceof WebApplicationException) {
            return ((WebApplicationException) e).getResponse();
        }
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErroResposta("Erro interno no servidor. Tente novamente."))
                .build();
    }
}
