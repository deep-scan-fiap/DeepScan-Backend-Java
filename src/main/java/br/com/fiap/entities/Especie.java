package br.com.fiap.entities;

public class Especie {

    private int idEspecie;
    private String ncEspecie;
    private String npEspecie;
    private String conservaEspecie;
    private String habitatEspecie;
    private String descEspecie;

    public Especie() {}

    public Especie(int idEspecie, String ncEspecie, String npEspecie,
                   String conservaEspecie, String habitatEspecie, String descEspecie) {
        this.idEspecie = idEspecie;
        this.ncEspecie = ncEspecie;
        this.npEspecie = npEspecie;
        this.conservaEspecie = conservaEspecie;
        this.habitatEspecie = habitatEspecie;
        this.descEspecie = descEspecie;
    }

    public int getIdEspecie() { return idEspecie; }
    public void setIdEspecie(int idEspecie) { this.idEspecie = idEspecie; }

    public String getNcEspecie() { return ncEspecie; }
    public void setNcEspecie(String ncEspecie) { this.ncEspecie = ncEspecie; }

    public String getNpEspecie() { return npEspecie; }
    public void setNpEspecie(String npEspecie) { this.npEspecie = npEspecie; }

    public String getConservaEspecie() { return conservaEspecie; }
    public void setConservaEspecie(String conservaEspecie) { this.conservaEspecie = conservaEspecie; }

    public String getHabitatEspecie() { return habitatEspecie; }
    public void setHabitatEspecie(String habitatEspecie) { this.habitatEspecie = habitatEspecie; }

    public String getDescEspecie() { return descEspecie; }
    public void setDescEspecie(String descEspecie) { this.descEspecie = descEspecie; }

    @Override
    public String toString() {
        return "\n╔══════════════════════════════════════╗" +
               "\n║              ESPECIE                 ║" +
               "\n╠══════════════════════════════════════╣" +
               "\n║  ID         : " + String.format("%-24s", idEspecie)      + "║" +
               "\n║  Nome Comum : " + String.format("%-24s", ncEspecie)      + "║" +
               "\n║  Nome Pop.  : " + String.format("%-24s", npEspecie)      + "║" +
               "\n║  Conserv.   : " + String.format("%-24s", conservaEspecie)+ "║" +
               "\n║  Habitat    : " + String.format("%-24s", habitatEspecie) + "║" +
               "\n╚══════════════════════════════════════╝";
    }
}
