package com.example.prototipo_fixtrada.construtores;

public class Veiculo {
    private int veiId;
    private String veiModelo;
    private String veiPlaca;
    private int veiCliId;

    public Veiculo() {}

    public Veiculo(int veiId, String veiModelo, String veiPlaca, int veiCliId) {
        this.veiId = veiId;
        this.veiModelo = veiModelo;
        this.veiPlaca = veiPlaca;
        this.veiCliId = veiCliId;
    }

    public int getVeiId() { return veiId; }
    public void setVeiId(int veiId) { this.veiId = veiId; }

    public String getVeiModelo() { return veiModelo; }
    public void setVeiModelo(String veiModelo) { this.veiModelo = veiModelo; }

    public String getVeiPlaca() { return veiPlaca; }
    public void setVeiPlaca(String veiPlaca) { this.veiPlaca = veiPlaca; }

    public int getVeiCliId() { return veiCliId; }
    public void setVeiCliId(int veiCliId) { this.veiCliId = veiCliId; }
}
