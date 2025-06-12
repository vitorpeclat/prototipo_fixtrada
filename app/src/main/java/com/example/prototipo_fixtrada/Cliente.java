package com.example.prototipo_fixtrada;

public class Cliente {
    private int cliId;
    private String cliNome;
    private String cliEmail;
    private String cliSenha;
    private String cliCpf;
    private String cliDataNasc;

    public Cliente(int cliId, String cliNome, String cliEmail, String cliSenha, String cliCpf, String cliDataNasc) {
        this.cliId = cliId;
        this.cliNome = cliNome;
        this.cliEmail = cliEmail;
        this.cliSenha = cliSenha;
        this.cliCpf = cliCpf;
        this.cliDataNasc = cliDataNasc;
    }

    public Cliente() {}

    public int getCliId() { return cliId; }
    public void setCliId(int cliId) { this.cliId = cliId; }

    public String getCliNome() { return cliNome; }
    public void setCliNome(String cliNome) { this.cliNome = cliNome; }

    public String getCliEmail() { return cliEmail; }
    public void setCliEmail(String cliEmail) { this.cliEmail = cliEmail; }

    public String getCliSenha() { return cliSenha; }
    public void setCliSenha(String cliSenha) { this.cliSenha = cliSenha; }

    public String getCliCpf() { return cliCpf; }
    public void setCliCpf(String cliCpf) { this.cliCpf = cliCpf; }

    public String getCliDataNasc() { return cliDataNasc; }
    public void setCliDataNasc(String cliDataNasc) { this.cliDataNasc = cliDataNasc; }
}
