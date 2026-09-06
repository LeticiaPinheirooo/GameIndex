package com.school.sptech.gameIndex_individual;

import java.time.LocalDate;
import java.util.UUID;

public class Jogo {
    private Integer id;
    private String nome;
    private LocalDate dataJogou;
    private String categoria;
    private String plataforma;
    private Boolean favorito;

    public Jogo() {
    }

    public Jogo(Integer id, String nome, LocalDate dataJogou, String categoria, String plataforma, Boolean favorito) {
        this.id = id;
        this.nome = nome;
        this.dataJogou = dataJogou;
        this.categoria = categoria;
        this.plataforma = plataforma;
        this.favorito = favorito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataJogou() {
        return dataJogou;
    }

    public void setDataJogou(LocalDate dataJogou) {
        this.dataJogou = dataJogou;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }
}