package br.com.fiap.resource;

import br.com.fiap.bo.EstacaoMonitoraBO;
import br.com.fiap.entities.EstacaoMonitora;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deepscan/estacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstacaoResource {

    private final EstacaoMonitoraBO bo = new EstacaoMonitoraBO();

    @GET
    public Response listar() {
        return Response.ok(bo.listarTodas()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        return Response.ok(bo.buscarPorId(id)).build();
    }

    @POST
    public Response inserir(EstacaoMonitora estacao) {
        return Response.status(Response.Status.CREATED)
                .entity(bo.cadastrar(estacao))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, EstacaoMonitora estacao) {
        return Response.ok(bo.atualizar(id, estacao)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        bo.deletar(id);
        return Response.noContent().build();
    }
}
