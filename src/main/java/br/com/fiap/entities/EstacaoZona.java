package br.com.fiap.entities;

import java.time.LocalDate;

public class EstacaoZona {

    private int idZona;
    private int idEstacao;
    private LocalDate dataVinculo;

    public EstacaoZona() {}

    public EstacaoZona(int idZona, int idEstacao, LocalDate dataVinculo) {
        this.idZona = idZona;
        this.idEstacao = idEstacao;
        this.dataVinculo = dataVinculo;
    }

    public int getIdZona() { return idZona; }
    public void setIdZona(int idZona) { this.idZona = idZona; }

    public int getIdEstacao() { return idEstacao; }
    public void setIdEstacao(int idEstacao) { this.idEstacao = idEstacao; }

    public LocalDate getDataVinculo() { return dataVinculo; }
    public void setDataVinculo(LocalDate dataVinculo) { this.dataVinculo = dataVinculo; }

    @Override
    public String toString() {
        return "\n╔══════════════════════════════════════╗" +
               "\n║          VINCULO ESTACAO/ZONA        ║" +
               "\n╠══════════════════════════════════════╣" +
               "\n║  ID Zona    : " + String.format("%-24s", idZona)    + "║" +
               "\n║  ID Estacao : " + String.format("%-24s", idEstacao) + "║" +
               "\n║  Vinculado  : " + String.format("%-24s", dataVinculo != null ? dataVinculo.toString() : "-") + "║" +
               "\n╚══════════════════════════════════════╝";
    }
}
