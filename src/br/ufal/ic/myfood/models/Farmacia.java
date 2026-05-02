package br.ufal.ic.myfood.models;

public class Farmacia extends Empresa{

    boolean aberto24horas;
    int numeroFuncionarios;

    public Farmacia(String tipo, String dono, String name, String endereco, boolean horas24, int numeroFuncionarios) {
        super(tipo, dono, name, endereco);
        this.aberto24horas = horas24;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public Farmacia() {};

    public boolean isAberto24horas() {
        return aberto24horas;
    }

    public void setAberto24horas(boolean aberto24horas) {
        this.aberto24horas = aberto24horas;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }
}
