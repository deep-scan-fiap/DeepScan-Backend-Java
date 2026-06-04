package br.com.fiap.main;

import br.com.fiap.dao.EstacaoMonitoraDao;
import br.com.fiap.dao.LeituraTelemetriaDao;
import br.com.fiap.entities.EstacaoMonitora;
import br.com.fiap.entities.LeituraTelemetria;
import br.com.fiap.service.GeocodingService;

import java.util.List;
import java.util.Scanner;

public class TesteAPIs {

    private static final String SEP  = "─".repeat(52);
    private static final String SEP2 = "═".repeat(52);

    public static void main(String[] args) {

        EstacaoMonitoraDao   estacaoDao = new EstacaoMonitoraDao();
        LeituraTelemetriaDao leituraDao = new LeituraTelemetriaDao();
        GeocodingService     geocoding  = new GeocodingService();
        Scanner              scanner    = new Scanner(System.in);

        // ── ESTACOES ──────────────────────────────────────────
        List<EstacaoMonitora> estacoes = estacaoDao.listarTodas();

        System.out.println("\n" + SEP2);
        System.out.println("  ESTACOES DE MONITORAMENTO  (" + estacoes.size() + " registros)");
        System.out.println(SEP2);

        for (EstacaoMonitora e : estacoes) {
            String endereco = geocoding.buscarEndereco(e.getLatEstacao(), e.getLonEstacao());
            System.out.println("  #" + e.getIdEstacao() + " | " + e.getNomeEstacao() + " (" + e.getTipoEstacao() + ")");
            System.out.println("     Lat: " + e.getLatEstacao() + "  Lon: " + e.getLonEstacao());
            System.out.println("     Localizacao: " + endereco);
            System.out.println("  " + SEP);
        }

        // ── LEITURAS ──────────────────────────────────────────
        List<LeituraTelemetria> leituras = leituraDao.listarTodas();

        System.out.println("\n" + SEP2);
        System.out.println("  LEITURAS DE TELEMETRIA  (" + leituras.size() + " registros)");
        System.out.println(SEP2);

        for (LeituraTelemetria l : leituras) {
            System.out.println(l);
            System.out.println("  " + SEP);
        }

        // ── CONSULTA POR COORDENADA (input do usuario) ────────
        System.out.println("\n" + SEP2);
        System.out.println("  CONSULTA POR COORDENADA");
        System.out.println(SEP2);

        while (true) {
            System.out.print("\n  Digite a Latitude  (ou 'sair' para encerrar): ");
            String entradaLat = scanner.nextLine().trim();

            if (entradaLat.equalsIgnoreCase("sair")) {
                System.out.println("\n  Encerrando consultas. Ate logo!");
                break;
            }

            System.out.print("  Digite a Longitude : ");
            String entradaLon = scanner.nextLine().trim();

            try {
                double lat = Double.parseDouble(entradaLat.replace(",", "."));
                double lon = Double.parseDouble(entradaLon.replace(",", "."));

                imprimirResumoPorCoordenada(lat, lon, leituras, geocoding);

            } catch (NumberFormatException e) {
                System.out.println("  Valor invalido. Use numeros como -23.5 ou -43.2");
            }
        }

        scanner.close();
    }

    private static void imprimirResumoPorCoordenada(double lat, double lon,
                                                     List<LeituraTelemetria> leituras,
                                                     GeocodingService geocoding) {
        String endereco = geocoding.buscarEndereco(lat, lon);
        LeituraTelemetria maisRecente = leituras.isEmpty() ? null : leituras.get(0);

        System.out.println("\n  " + SEP);
        System.out.println("  Coordenada  : " + lat + ", " + lon);
        System.out.println("  Endereco    : " + endereco);

        if (maisRecente == null) {
            System.out.println("  Sem leituras disponiveis no banco.");
        } else {
            System.out.println("  " + SEP);
            System.out.println("  Telemetria mais recente (Estacao #" + maisRecente.getIdEstacao() + "):");
            System.out.println("    Temp. Superficial : " + maisRecente.getSst() + " C");
            System.out.println("    Altura das Ondas  : " + maisRecente.getWaveHeight() + " m");
            System.out.println("    Periodo das Ondas : " + maisRecente.getWavePeriod() + " s");
            System.out.println("    Velocidade Vento  : " + maisRecente.getWindSpeed() + " km/h");
            System.out.println("    Magnitude Sismo   : " + maisRecente.getEarthquakeMagnitude() + " Richter");
            System.out.println("    Prof. Focal       : " + maisRecente.getFocalDepth() + " km");
        }
        System.out.println("  " + SEP);
    }
}
