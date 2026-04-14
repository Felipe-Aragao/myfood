package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Empresa {

    private String id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String dono;

    private List<Produto> produtos = new ArrayList<>();

    public Empresa(String dono, String name, String endereco, String tipoCozinha) {
        this.nome = name;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.dono = dono;
        this.id = UUID.randomUUID().toString();

        this.produtos = new ArrayList<>();
    }

    public Empresa(){}

    // Getter e Setters

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

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    // toString
    @Override
    public String toString() {
        return "[" + this.nome + ", " + this.endereco + "]";
    }
}
