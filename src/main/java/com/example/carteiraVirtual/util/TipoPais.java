package com.example.carteiraVirtual.util;

public enum TipoPais {
    BRASIL("BRL", "Brasil"), EUROPA("EUR", "Europa"), SUICA("CHF", "Suiça");

    private String moeda;
    private String descricao;

    TipoPais(String moeda, String descricao) {
        this.moeda = moeda;
        this.descricao = descricao;
    }

    public String getMoeda() {
        return this.moeda;
    }

    public String getDescricao() {
        return descricao;
    }
}
