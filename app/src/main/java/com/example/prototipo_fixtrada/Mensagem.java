package com.example.prototipo_fixtrada;

public class Mensagem {
    private String remetente;
    private String texto;
    private String horario;

    public Mensagem(String remetente, String texto, String horario) {
        this.remetente = remetente;
        this.texto = texto;
        this.horario = horario;
    }

    public String getRemetente() {
        return remetente;
    }

    public String getTexto() {
        return texto;
    }

    public String getHorario() {
        return horario;
    }
}
