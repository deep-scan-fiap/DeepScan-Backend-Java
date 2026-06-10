package br.com.fiap.resource;

import br.com.fiap.bo.LeituraTelemetriaBO;
import br.com.fiap.entities.LeituraTelemetria;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/deepscan/leituras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LeituraResource {

    private final LeituraTelemetriaBO bo = new LeituraTelemetriaBO();

    @GET
    public Response listar() {
        return Response.ok(bo.listarTodas()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        return Response.ok(bo.buscarPorId(id)).build();
    }

    @GET
    @Path("/estacao/{idEstacao}")
    public Response listarPorEstacao(@PathParam("idEstacao") int idEstacao) {
        return Response.ok(bo.listarPorEstacao(idEstacao)).build();
    }

    @POST
    public Response inserir(LeituraTelemetria leitura) {
        return Response.status(Response.Status.CREATED)
                .entity(bo.registrar(leitura))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        bo.deletar(id);
        return Response.noContent().build();
    }
}
