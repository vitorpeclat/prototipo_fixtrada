package com.example.prototipo_fixtrada;

public class RegistroServico {
    private int regId;
    private String regDesc;
    private String regData;
    private int regVeiId;
    private int regPreId;

    public RegistroServico() {}

    public RegistroServico(int regId, String regDesc, String regData, int regVeiId, int regPreId) {
        this.regId = regId;
        this.regDesc = regDesc;
        this.regData = regData;
        this.regVeiId = regVeiId;
        this.regPreId = regPreId;
    }

    public int getRegId() { return regId; }
    public void setRegId(int regId) { this.regId = regId; }

    public String getRegDesc() { return regDesc; }
    public void setRegDesc(String regDesc) { this.regDesc = regDesc; }

    public String getRegData() { return regData; }
    public void setRegData(String regData) { this.regData = regData; }

    public int getRegVeiId() { return regVeiId; }
    public void setRegVeiId(int regVeiId) { this.regVeiId = regVeiId; }

    public int getRegPreId() { return regPreId; }
    public void setRegPreId(int regPreId) { this.regPreId = regPreId; }
}
