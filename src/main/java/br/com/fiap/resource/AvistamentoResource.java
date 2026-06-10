package br.com.fiap.resource;

import br.com.fiap.bo.AvistamentoBO;
import br.com.fiap.entities.Avistamento;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deepscan/avistamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvistamentoResource {

    private final AvistamentoBO bo = new AvistamentoBO();

    @GET
    public Response listar() {
        return Response.ok(bo.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        return Response.ok(bo.buscarPorId(id)).build();
    }

    @GET
    @Path("/especie/{idEspecie}")
    public Response listarPorEspecie(@PathParam("idEspecie") int idEspecie) {
        return Response.ok(bo.listarPorEspecie(idEspecie)).build();
    }

    @POST
    public Response inserir(Avistamento avistamento) {
        return Response.status(Response.Status.CREATED)
                .entity(bo.cadastrar(avistamento))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Avistamento avistamento) {
        return Response.ok(bo.atualizar(id, avistamento)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        bo.deletar(id);
        return Response.noContent().build();
    }
}
