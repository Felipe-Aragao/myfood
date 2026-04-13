package br.ufal.ic.myfood.models;

public class Empresa extends Usuario{

    private String cpf;

    public Empresa(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public Empresa() {};

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
