package br.com.fiap.resource;

import br.com.fiap.bo.AlertaBO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deepscan/alertas")
@Produces(MediaType.APPLICATION_JSON)
public class AlertaResource {

    private final AlertaBO bo = new AlertaBO();

    @GET
    public Response listar() {
        return Response.ok(bo.listarAlertas()).build();
    }

    @GET
    @Path("/pendentes")
    public Response listarPendentes() {
        return Response.ok(bo.listarPendentes()).build();
    }

    @PUT
    @Path("/{id}/resolver")
    public Response resolver(@PathParam("id") int id) {
        bo.resolverAlerta(id);
        return Response.ok("Alerta " + id + " resolvido.").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        bo.deletarAlerta(id);
        return Response.noContent().build();
    }
}
