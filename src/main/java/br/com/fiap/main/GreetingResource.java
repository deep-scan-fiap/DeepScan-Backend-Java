package br.com.fiap.main;

import br.com.fiap.bo.AlertaBO;
import br.com.fiap.bo.EspecieBO;
import br.com.fiap.bo.EstacaoMonitoraBO;
import br.com.fiap.bo.LeituraTelemetriaBO;
import br.com.fiap.dao.AvistamentoDao;
import br.com.fiap.dao.ZonaMonitoraDao;
import br.com.fiap.entities.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/deepscan")
public class GreetingResource {

    private final EstacaoMonitoraBO  estacaoBO  = new EstacaoMonitoraBO();
    private final LeituraTelemetriaBO leituraBO = new LeituraTelemetriaBO();
    private final AlertaBO           alertaBO   = new AlertaBO();
    private final EspecieBO          especieBO  = new EspecieBO();
    private final AvistamentoDao     avistDao   = new AvistamentoDao();
    private final ZonaMonitoraDao    zonaDao    = new ZonaMonitoraDao();

    // ================================================================
    // ESTACOES  –  GET / POST / PUT / DELETE
    // ================================================================

    @GET
    @Path("/estacoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEstacoes() {
        try {
            List<EstacaoMonitora> lista = estacaoBO.listarTodas();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @GET
    @Path("/estacoes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEstacao(@PathParam("id") int id) {
        try {
            return Response.ok(estacaoBO.buscarPorId(id)).build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @POST
    @Path("/estacoes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirEstacao(EstacaoMonitora estacao) {
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(estacaoBO.cadastrar(estacao)).build();
        } catch (IllegalArgumentException e) {
            return erro400(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @PUT
    @Path("/estacoes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarEstacao(@PathParam("id") int id, EstacaoMonitora estacao) {
        try {
            return Response.ok(estacaoBO.atualizar(id, estacao)).build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @DELETE
    @Path("/estacoes/{id}")
    public Response deletarEstacao(@PathParam("id") int id) {
        try {
            estacaoBO.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    // ================================================================
    // LEITURAS  –  GET / POST / DELETE
    // ================================================================

    @GET
    @Path("/leituras")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarLeituras() {
        try {
            return Response.ok(leituraBO.listarTodas()).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @GET
    @Path("/leituras/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarLeitura(@PathParam("id") int id) {
        try {
            return Response.ok(leituraBO.buscarPorId(id)).build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @GET
    @Path("/leituras/estacao/{idEstacao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarLeiturasPorEstacao(@PathParam("idEstacao") int idEstacao) {
        try {
            return Response.ok(leituraBO.listarPorEstacao(idEstacao)).build();
        } catch (IllegalArgumentException e) {
            return erro400(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @POST
    @Path("/leituras")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirLeitura(LeituraTelemetria leitura) {
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(leituraBO.registrar(leitura)).build();
        } catch (IllegalArgumentException e) {
            return erro400(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @DELETE
    @Path("/leituras/{id}")
    public Response deletarLeitura(@PathParam("id") int id) {
        try {
            leituraBO.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    // ================================================================
    // ALERTAS  –  GET / PUT / DELETE
    // ================================================================

    @GET
    @Path("/alertas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAlertas() {
        try {
            return Response.ok(alertaBO.listarAlertas()).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @GET
    @Path("/alertas/pendentes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAlertasPendentes() {
        try {
            return Response.ok(alertaBO.listarPendentes()).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @PUT
    @Path("/alertas/{id}/resolver")
    public Response resolverAlerta(@PathParam("id") int id) {
        try {
            alertaBO.resolverAlerta(id);
            return Response.ok("Alerta " + id + " resolvido.").build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @DELETE
    @Path("/alertas/{id}")
    public Response deletarAlerta(@PathParam("id") int id) {
        try {
            alertaBO.deletarAlerta(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    // ================================================================
    // ESPECIES  –  GET / POST / PUT / DELETE
    // ================================================================

    @GET
    @Path("/especies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEspecies() {
        try {
            return Response.ok(especieBO.listarTodas()).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @GET
    @Path("/especies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEspecie(@PathParam("id") int id) {
        try {
            return Response.ok(especieBO.buscarPorId(id)).build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @POST
    @Path("/especies")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirEspecie(Especie especie) {
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(especieBO.cadastrar(especie)).build();
        } catch (IllegalArgumentException e) {
            return erro400(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @PUT
    @Path("/especies/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarEspecie(@PathParam("id") int id, Especie especie) {
        try {
            return Response.ok(especieBO.atualizar(id, especie)).build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @DELETE
    @Path("/especies/{id}")
    public Response deletarEspecie(@PathParam("id") int id) {
        try {
            especieBO.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return erro404(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    // ================================================================
    // AVISTAMENTOS  –  GET / POST / PUT / DELETE
    // ================================================================

    @GET
    @Path("/avistamentos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAvistamentos() {
        try {
            return Response.ok(avistDao.listarTodos()).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @GET
    @Path("/avistamentos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarAvistamento(@PathParam("id") int id) {
        try {
            Avistamento a = avistDao.buscarPorId(id);
            if (a == null) return Response.status(Response.Status.NOT_FOUND)
                    .entity("Avistamento nao encontrado.").build();
            return Response.ok(a).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @GET
    @Path("/avistamentos/especie/{idEspecie}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAvistamentosPorEspecie(@PathParam("idEspecie") int idEspecie) {
        try {
            return Response.ok(avistDao.listarPorEspecie(idEspecie)).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @POST
    @Path("/avistamentos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirAvistamento(Avistamento avistamento) {
        try {
            if (avistamento.getQuantAvista() <= 0)
                return erro400(new IllegalArgumentException("Quantidade deve ser maior que zero."));
            avistDao.inserir(avistamento);
            return Response.status(Response.Status.CREATED).entity(avistamento).build();
        } catch (IllegalArgumentException e) {
            return erro400(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @PUT
    @Path("/avistamentos/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarAvistamento(@PathParam("id") int id, Avistamento avistamento) {
        try {
            Avistamento existente = avistDao.buscarPorId(id);
            if (existente == null) return erro404(new IllegalArgumentException("Avistamento nao encontrado."));
            avistamento.setIdAvista(id);
            avistDao.atualizar(avistamento);
            return Response.ok(avistamento).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @DELETE
    @Path("/avistamentos/{id}")
    public Response deletarAvistamento(@PathParam("id") int id) {
        try {
            Avistamento existente = avistDao.buscarPorId(id);
            if (existente == null) return erro404(new IllegalArgumentException("Avistamento nao encontrado."));
            avistDao.deletar(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    // ================================================================
    // ZONAS  –  GET / POST / DELETE
    // ================================================================

    @GET
    @Path("/zonas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarZonas() {
        try {
            return Response.ok(zonaDao.listarTodas()).build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @POST
    @Path("/zonas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirZona(ZonaMonitora zona) {
        try {
            if (zona.getNomeZona() == null || zona.getNomeZona().isBlank())
                return erro400(new IllegalArgumentException("Nome da zona e obrigatorio."));
            zonaDao.inserir(zona);
            return Response.status(Response.Status.CREATED).entity(zona).build();
        } catch (IllegalArgumentException e) {
            return erro400(e);
        } catch (Exception e) {
            return erro500(e);
        }
    }

    @DELETE
    @Path("/zonas/{id}")
    public Response deletarZona(@PathParam("id") int id) {
        try {
            zonaDao.deletar(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return erro500(e);
        }
    }

    // ================================================================
    // HELPERS  –  respostas de erro padronizadas
    // ================================================================

    private Response erro400(Exception e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"erro\": \"" + e.getMessage() + "\"}").build();
    }

    private Response erro404(Exception e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"erro\": \"" + e.getMessage() + "\"}").build();
    }

    private Response erro500(Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"erro\": \"Erro interno no servidor. Tente novamente.\"}").build();
    }
}
