package br.com.fiap.mapper;

import br.com.fiap.exceptions.DadoInvalidoException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DadoInvalidoMapper implements ExceptionMapper<DadoInvalidoException> {
    @Override
    public Response toResponse(DadoInvalidoException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErroResposta(e.getMessage()))
                .build();
    }
}
