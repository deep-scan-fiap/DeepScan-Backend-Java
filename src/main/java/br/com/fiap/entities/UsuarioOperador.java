package br.com.fiap.entities;

/**
 * Representa um operador do sistema DeepScan.
 * Responsável por monitorar alertas e registrar avistamentos.
 * Tabela: USUARIO_OPERADOR
 */
public class UsuarioOperador {

    private int idOpera;
    private String nmOpera;
    private String emailOpera;
    private String senhaOpera;

    public UsuarioOperador() {}

    public UsuarioOperador(int idOpera, String nmOpera,
                            String emailOpera, String senhaOpera) {
        this.idOpera = idOpera;
        this.nmOpera = nmOpera;
        this.emailOpera = emailOpera;
        this.senhaOpera = senhaOpera;
    }

    public int getIdOpera() { return idOpera; }
    public void setIdOpera(int idOpera) { this.idOpera = idOpera; }

    public String getNmOpera() { return nmOpera; }
    public void setNmOpera(String nmOpera) { this.nmOpera = nmOpera; }

    public String getEmailOpera() { return emailOpera; }
    public void setEmailOpera(String emailOpera) { this.emailOpera = emailOpera; }

    public String getSenhaOpera() { return senhaOpera; }
    public void setSenhaOpera(String senhaOpera) { this.senhaOpera = senhaOpera; }

    @Override
    public String toString() {
        return "UsuarioOperador{" +
                "idOpera=" + idOpera +
                ", nmOpera='" + nmOpera + '\'' +
                ", emailOpera='" + emailOpera + '\'' +
                '}';
    }
}
