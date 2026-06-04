package br.com.fiap.entities;

public class EstacaoMonitora {

    private int idEstacao;
    private String nomeEstacao;
    private double latEstacao;
    private double lonEstacao;
    private String tipoEstacao;

    public EstacaoMonitora() {}

    public EstacaoMonitora(int idEstacao, String nomeEstacao,
                            double latEstacao, double lonEstacao,
                            String tipoEstacao) {
        this.idEstacao = idEstacao;
        this.nomeEstacao = nomeEstacao;
        this.latEstacao = latEstacao;
        this.lonEstacao = lonEstacao;
        this.tipoEstacao = tipoEstacao;
    }

    public int getIdEstacao() { return idEstacao; }
    public void setIdEstacao(int idEstacao) { this.idEstacao = idEstacao; }

    public String getNomeEstacao() { return nomeEstacao; }
    public void setNomeEstacao(String nomeEstacao) { this.nomeEstacao = nomeEstacao; }

    public double getLatEstacao() { return latEstacao; }
    public void setLatEstacao(double latEstacao) { this.latEstacao = latEstacao; }

    public double getLonEstacao() { return lonEstacao; }
    public void setLonEstacao(double lonEstacao) { this.lonEstacao = lonEstacao; }

    public String getTipoEstacao() { return tipoEstacao; }
    public void setTipoEstacao(String tipoEstacao) { this.tipoEstacao = tipoEstacao; }

    @Override
    public String toString() {
        return "Estacao #" + idEstacao + " - " + nomeEstacao +
               " (" + tipoEstacao + ")" +
               " | Lat: " + latEstacao + ", Lon: " + lonEstacao;
    }
}
