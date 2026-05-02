package br.ufal.ic.myfood.models;

public class Restaurante extends Empresa{

    private String tipoCozinha;

    public Restaurante(String tipo, String dono, String name, String endereco, String tipoCozinha) {
        super(tipo, dono, name, endereco);
        this.tipoCozinha = tipoCozinha;
    }

    public Restaurante(){}

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }
}
