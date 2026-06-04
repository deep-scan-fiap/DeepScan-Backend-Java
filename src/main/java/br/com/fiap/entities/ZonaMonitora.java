package br.com.fiap.entities;

/**
 * Representa uma zona geográfica de monitoramento.
 * Tabela: ZONA_MONITORA
 */
public class ZonaMonitora {

    private int idZona;
    private String nomeZona;
    private String paisZona;
    private String descZona;

    public ZonaMonitora() {}

    public ZonaMonitora(int idZona, String nomeZona, String paisZona, String descZona) {
        this.idZona = idZona;
        this.nomeZona = nomeZona;
        this.paisZona = paisZona;
        this.descZona = descZona;
    }

    public int getIdZona() { return idZona; }
    public void setIdZona(int idZona) { this.idZona = idZona; }

    public String getNomeZona() { return nomeZona; }
    public void setNomeZona(String nomeZona) { this.nomeZona = nomeZona; }

    public String getPaisZona() { return paisZona; }
    public void setPaisZona(String paisZona) { this.paisZona = paisZona; }

    public String getDescZona() { return descZona; }
    public void setDescZona(String descZona) { this.descZona = descZona; }

    @Override
    public String toString() {
        return "ZonaMonitora{" +
                "idZona=" + idZona +
                ", nomeZona='" + nomeZona + '\'' +
                ", paisZona='" + paisZona + '\'' +
                ", descZona='" + descZona + '\'' +
                '}';
    }
}
