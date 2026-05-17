package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Empresa {

    private String tipo;
    private String id;
    private String nome;
    private String endereco;
    private String dono;

    private List<Produto> produtos = new ArrayList<>();
    private List<Entregador> entregadores = new ArrayList<>();


    public Empresa(String tipo, String dono, String name, String endereco) {
        this.nome = name;
        this.endereco = endereco;
        this.tipo = tipo;
        this.dono = dono;
        this.id = UUID.randomUUID().toString();

        this.produtos = new ArrayList<>();
        this.entregadores = new ArrayList<>();
    }

    public Empresa(){}

    // Getter e Setters

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public List<Entregador> getEntregadores() {
        return entregadores;
    }

    public void setEntregadores(List<Entregador> entregadores) {
        this.entregadores = entregadores;
    }

    // toString
    @Override
    public String toString() {
        return "[" + this.nome + ", " + this.endereco + "]";
    }
}
