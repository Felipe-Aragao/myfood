package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoException;

import java.util.UUID;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario(String nome, String email, String senha, String endereco) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.id = UUID.randomUUID().toString();
    }

    public Usuario() {}

    // Getter e Setters
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
