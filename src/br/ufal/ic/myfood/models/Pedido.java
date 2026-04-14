package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private String numero;
    private String cliente;
    private String empresa;
    private String estado;
    private float valor;
    private List<Produto> produtos;

    public Pedido(String cliente, String empresa) {
        this.cliente = cliente;
        this.empresa = empresa;
        numero = UUID.randomUUID().toString();
        this.estado = "aberto";

        produtos = new ArrayList<>();
    }

    public Pedido(){}

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
