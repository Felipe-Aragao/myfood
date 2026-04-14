package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Empresa;

import br.ufal.ic.myfood.models.Produto;

import java.util.Locale;
import java.util.stream.Collectors;

public class ProdutoManager {

    private EmpresaManager eManager;

    public ProdutoManager(EmpresaManager eManager) {
        this.eManager = eManager;
    }

    public void validarProduto(String nome, float valor, String categoria) throws Exception{
        if (nome == null || nome.isBlank()) {
            throw new NomeInvalidoException();
        }
        if (valor < 0) {
            throw new ValorInvalidoException();
        }
        if (categoria == null || categoria.isBlank()) {
            throw new CategoriaInvalidaException();
        }
    }

    // Produtos
    public String criarProduto(String idEmpresa, String nome, float valor, String categoria) throws Exception {

        validarProduto(nome, valor, categoria);

        Empresa empresa = eManager.findEmpresa(idEmpresa).orElseThrow(EmpresaNaoExisteException::new);
        for (Produto p : empresa.getProdutos()) {
            if (p.getNome().equals(nome))
                throw new ProdutoJaExisteException();
        }

        Produto novo = new Produto(nome, valor, categoria);
        empresa.getProdutos().add(novo);
        return novo.getId();
    }

    public void editarProduto(String id, String nome, float valor, String categoria) throws Exception {

        validarProduto(nome, valor, categoria);

        for (Empresa e: eManager.getEmpresas()) {
            for (Produto p : e.getProdutos()) {
                if (p.getId().equals(id)) {
                    p.setNome(nome);
                    p.setValor(valor);
                    p.setCategoria(categoria);
                    return;
                }
            }
        }

        throw new ProdutoNaoCadastradoException();
    }

    public String getProduto(String nome, String idEmpresa, String atributo) throws Exception {
        Empresa empresa = eManager.findEmpresa(idEmpresa).orElseThrow(EmpresaNaoExisteException::new);

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        if (atributo.equalsIgnoreCase("empresa")) {
            return empresa.getNome();
        }
        for (Produto p : empresa.getProdutos()) {
            if (p.getNome().equals(nome)) {
                if (atributo.equalsIgnoreCase("nome")) {
                    return p.getNome();
                } else if (atributo.equalsIgnoreCase("valor")) {
                    return String.format(Locale.US, "%.2f", p.getValor());
                } else if (atributo.equalsIgnoreCase("categoria")) {
                    return p.getCategoria();
                }
                throw new AtributoNaoExisteException();
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    public String listarProdutos(String idEmpresa) throws Exception {

        for (Empresa empresa : eManager.getEmpresas()) {
            if (empresa.getId().equals(idEmpresa)) {
                String lista = empresa.getProdutos().stream()
                        .map(Produto::getNome)
                        .collect(Collectors.joining(", "));
                return "{[" + lista + "]}";
            }
        }
        throw new EmpresaNaoEncontradaException();
    }


}

