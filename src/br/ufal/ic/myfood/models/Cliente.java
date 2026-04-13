package br.ufal.ic.myfood.models;

public class Cliente extends Usuario{

    public Cliente(String nome, String email, String senha, String endereco) {
        super(nome, email, senha, endereco);
    }

    public Cliente() {};
}
