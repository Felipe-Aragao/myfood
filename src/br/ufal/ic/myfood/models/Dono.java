package br.ufal.ic.myfood.models;

public class Dono extends Usuario{

    private String cpf;

    public Dono(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public Dono() {}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
