package com.example.carteiraVirtual.controllers;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RespostaDefault<T> {
    private T response;
    private List<String> errors;

    /*public RespostaDefault() {
        this.erros = new ArrayList<>();
    }*/

    public List<String> getErrors() {
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
