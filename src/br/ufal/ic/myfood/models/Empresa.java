package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoException;
import br.ufal.ic.myfood.exceptions.EmpresaNaoExisteException;

import java.util.UUID;

public class Empresa {

    private String id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String dono;

    public Empresa(String dono, String name, String endereco, String tipoCozinha) {
        this.nome = name;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.dono = dono;
        this.id = UUID.randomUUID().toString();
    }

    public Empresa(){};

    @Override
    public String toString() {
        return "[" + this.nome + ", " + this.endereco + "]";
    }

    public String getAtributo(String atributo) throws Exception {
        if (atributo.equalsIgnoreCase("nome")) {
            return this.nome;
        } else if (atributo.equalsIgnoreCase("dono")) {
            return this.dono;
        } else if (atributo.equalsIgnoreCase("endereco")) {
            return this.endereco;
        } else if (atributo.equalsIgnoreCase("tipocozinha")) {
            return  this.tipoCozinha;
        }
        throw new AtributoInvalidoException();
    }

    // Getter e Setters
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
}
