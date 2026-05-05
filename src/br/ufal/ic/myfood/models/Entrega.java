package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Entrega {

    private String id;
    private String clienteNome;
    private String empresaNome;
    private String pedidoId;
    private String entregadorId;
    private String destino;
    private List<String> produtos;

    public Entrega(String cliente, String empresa, String pedido, String entregador, String destino) {
        this.clienteNome = cliente;
        this.empresaNome = empresa;
        this.pedidoId = pedido;
        this.entregadorId = entregador;
        this.destino = destino;
        this.id = UUID.randomUUID().toString();
        this.produtos = new ArrayList<String>();
    }

    public Entrega(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getEmpresaNome() {
        return empresaNome;
    }

    public void setEmpresaNome(String empresaNome) {
        this.empresaNome = empresaNome;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getEntregadorId() {
        return entregadorId;
    }

    public void setEntregadorId(String entregadorId) {
        this.entregadorId = entregadorId;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<String> produtos) {
        this.produtos = produtos;
    }
}
