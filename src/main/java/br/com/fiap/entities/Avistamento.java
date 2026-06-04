package br.com.fiap.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Avistamento {

    private int idAvista;
    private int idEstacao;
    private int idEspecie;
    private LocalDateTime horarioAvista;
    private int quantAvista;

    public Avistamento() {}

    public Avistamento(int idAvista, int idEstacao, int idEspecie,
                       LocalDateTime horarioAvista, int quantAvista) {
        this.idAvista = idAvista;
        this.idEstacao = idEstacao;
        this.idEspecie = idEspecie;
        this.horarioAvista = horarioAvista;
        this.quantAvista = quantAvista;
    }

    public int getIdAvista() { return idAvista; }
    public void setIdAvista(int idAvista) { this.idAvista = idAvista; }

    public int getIdEstacao() { return idEstacao; }
    public void setIdEstacao(int idEstacao) { this.idEstacao = idEstacao; }

    public int getIdEspecie() { return idEspecie; }
    public void setIdEspecie(int idEspecie) { this.idEspecie = idEspecie; }

    public LocalDateTime getHorarioAvista() { return horarioAvista; }
    public void setHorarioAvista(LocalDateTime horarioAvista) { this.horarioAvista = horarioAvista; }

    public int getQuantAvista() { return quantAvista; }
    public void setQuantAvista(int quantAvista) { this.quantAvista = quantAvista; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Avistamento #" + idAvista +
               " | Estacao #" + idEstacao +
               " | Especie #" + idEspecie +
               " | Qtd: " + quantAvista +
               " | " + (horarioAvista != null ? horarioAvista.format(fmt) : "-");
    }
}
