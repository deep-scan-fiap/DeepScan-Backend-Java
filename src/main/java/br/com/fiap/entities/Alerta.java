package br.com.fiap.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alerta {

    private int idAlerta;
    private int idLeitura;
    private String riscoAlerta;
    private String descAlerta;
    private LocalDateTime horarioAlerta;
    private String observaAlerta;
    private String conclusaoAlerta;

    public Alerta() {}

    public Alerta(int idAlerta, int idLeitura, String riscoAlerta,
                  String descAlerta, LocalDateTime horarioAlerta,
                  String observaAlerta, String conclusaoAlerta) {
        this.idAlerta = idAlerta;
        this.idLeitura = idLeitura;
        this.riscoAlerta = riscoAlerta;
        this.descAlerta = descAlerta;
        this.horarioAlerta = horarioAlerta;
        this.observaAlerta = observaAlerta;
        this.conclusaoAlerta = conclusaoAlerta;
    }

    public int getIdAlerta() { return idAlerta; }
    public void setIdAlerta(int idAlerta) { this.idAlerta = idAlerta; }

    public int getIdLeitura() { return idLeitura; }
    public void setIdLeitura(int idLeitura) { this.idLeitura = idLeitura; }

    public String getRiscoAlerta() { return riscoAlerta; }
    public void setRiscoAlerta(String riscoAlerta) { this.riscoAlerta = riscoAlerta; }

    public String getDescAlerta() { return descAlerta; }
    public void setDescAlerta(String descAlerta) { this.descAlerta = descAlerta; }

    public LocalDateTime getHorarioAlerta() { return horarioAlerta; }
    public void setHorarioAlerta(LocalDateTime horarioAlerta) { this.horarioAlerta = horarioAlerta; }

    public String getObservaAlerta() { return observaAlerta; }
    public void setObservaAlerta(String observaAlerta) { this.observaAlerta = observaAlerta; }

    public String getConclusaoAlerta() { return conclusaoAlerta; }
    public void setConclusaoAlerta(String conclusaoAlerta) { this.conclusaoAlerta = conclusaoAlerta; }

    public boolean isResolvido() { return "S".equalsIgnoreCase(conclusaoAlerta); }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String status = "S".equalsIgnoreCase(conclusaoAlerta) ? "Resolvido" : "Pendente";
        return "Alerta #" + idAlerta + " [" + riscoAlerta + "] - " + status +
               " | Leitura #" + idLeitura +
               " | " + (horarioAlerta != null ? horarioAlerta.format(fmt) : "-") +
               "\n  " + descAlerta;
    }
}
