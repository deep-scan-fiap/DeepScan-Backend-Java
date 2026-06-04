package br.com.fiap.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servico de geocoding reverso usando a API Nominatim (OpenStreetMap).
 * Gratuita, sem necessidade de chave de API.
 */
public class GeocodingService {

    private static final String BASE_URL =
            "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s&accept-language=pt-BR";

    /**
     * Retorna o endereco formatado a partir de uma latitude e longitude.
     * Exemplo: "Copacabana, Rio de Janeiro, RJ, Brasil"
     */
    public String buscarEndereco(double lat, double lon) {
        try {
            String urlStr = String.format(BASE_URL,
                    String.valueOf(lat).replace(",", "."),
                    String.valueOf(lon).replace(",", "."));

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // Nominatim exige um User-Agent identificado
            conn.setRequestProperty("User-Agent", "DeepScan-FIAP/1.0");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status != 200) {
                return "Endereco nao encontrado (HTTP " + status + ")";
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

            if (!json.has("address")) {
                return json.has("display_name")
                        ? json.get("display_name").getAsString()
                        : "Endereco nao disponivel";
            }

            JsonObject address = json.getAsJsonObject("address");

            StringBuilder endereco = new StringBuilder();

            if (address.has("suburb"))            endereco.append(address.get("suburb").getAsString());
            else if (address.has("neighbourhood")) endereco.append(address.get("neighbourhood").getAsString());
            else if (address.has("road"))         endereco.append(address.get("road").getAsString());

            if (address.has("city"))              append(endereco, address.get("city").getAsString());
            else if (address.has("town"))         append(endereco, address.get("town").getAsString());
            else if (address.has("municipality")) append(endereco, address.get("municipality").getAsString());

            if (address.has("state"))             append(endereco, address.get("state").getAsString());
            if (address.has("country"))           append(endereco, address.get("country").getAsString());

            return endereco.length() > 0 ? endereco.toString() : json.get("display_name").getAsString();

        } catch (Exception e) {
            return "Erro ao buscar endereco: " + e.getMessage();
        }
    }

    private void append(StringBuilder sb, String valor) {
        if (sb.length() > 0) sb.append(", ");
        sb.append(valor);
    }
}
