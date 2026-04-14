package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoException;

import java.util.Locale;
import java.util.UUID;

public class Produto {

    private String id;
    private String nome;
    private float valor;
    private String categoria;

    public Produto(String nome, float valor, String categoria) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    public Produto(){}

    //getter e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
