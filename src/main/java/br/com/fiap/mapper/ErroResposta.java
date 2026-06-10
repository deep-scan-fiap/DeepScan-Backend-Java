package br.com.fiap.mapper;

public class ErroResposta {
    private String erro;

    public ErroResposta() {}

    public ErroResposta(String erro) {
        this.erro = erro;
    }

    public String getErro() { return erro; }
    public void setErro(String erro) { this.erro = erro; }
}
