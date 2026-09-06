package com.school.sptech.gameIndex_individual;

public class Plataforma {
    private Integer idPlat;
    private String nomePlat;

    public Plataforma() {
    }

    public Plataforma(Integer idPlat, String nomePlat) {
        this.idPlat = idPlat;
        this.nomePlat = nomePlat;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public String getNomePlat() {
        return nomePlat;
    }

    public void setNomePlat(String nomePlat) {
        this.nomePlat = nomePlat;
    }
}


