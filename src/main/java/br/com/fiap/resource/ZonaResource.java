package br.com.fiap.resource;

import br.com.fiap.bo.ZonaMonitoraBO;
import br.com.fiap.entities.ZonaMonitora;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deepscan/zonas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZonaResource {

    private final ZonaMonitoraBO bo = new ZonaMonitoraBO();

    @GET
    public Response listar() {
        return Response.ok(bo.listarTodas()).build();
    }

    @POST
    public Response inserir(ZonaMonitora zona) {
        return Response.status(Response.Status.CREATED)
                .entity(bo.cadastrar(zona))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        bo.deletar(id);
        return Response.noContent().build();
    }
}
