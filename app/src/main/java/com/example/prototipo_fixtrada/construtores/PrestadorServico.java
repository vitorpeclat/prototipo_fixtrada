package com.example.prototipo_fixtrada.construtores;

public class PrestadorServico {
    private int preId;
    private String preNome;
    private String preEmail;
    private String preSenha;
    private String preCpf;
    private String preEspecialidade;
    private float preNota;

    private String preEndereco;

    public PrestadorServico() {}

    public PrestadorServico(int preId, String preNome, String preEmail, String preSenha, String preCpf, String preEspecialidade, float preNota, String preEndereco) {
        this.preId = preId;
        this.preNome = preNome;
        this.preEmail = preEmail;
        this.preSenha = preSenha;
        this.preCpf = preCpf;
        this.preEspecialidade = preEspecialidade;
        this.preNota = preNota;
        this.preEndereco = preEndereco;
    }

    public String getPreEndereco() {
        return preEndereco;
    }
    public void setPreEndereco(String preEndereco) {
        this.preEndereco = preEndereco;
    }

    public int getPreId() { return preId; }
    public void setPreId(int preId) { this.preId = preId; }

    public String getPreNome() { return preNome; }
    public void setPreNome(String preNome) { this.preNome = preNome; }

    public String getPreEmail() { return preEmail; }
    public void setPreEmail(String preEmail) { this.preEmail = preEmail; }

    public String getPreSenha() { return preSenha; }
    public void setPreSenha(String preSenha) { this.preSenha = preSenha; }

    public String getPreCpf() { return preCpf; }
    public void setPreCpf(String preCpf) { this.preCpf = preCpf; }

    public String getPreEspecialidade() { return preEspecialidade; }
    public void setPreEspecialidade(String preEspecialidade) { this.preEspecialidade = preEspecialidade; }

    public float getPreNota() { return preNota; }
    public void setPreNota(float preNota) { this.preNota = preNota; }
}
