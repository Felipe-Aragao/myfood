package br.ufal.ic.myfood.models;

public class Mercado extends Empresa{

    String abre;
    String fecha;
    String tipoMercado;

    public Mercado(String tipo, String nome, String dono, String endereco, String abre, String fecha, String tipoMercado) {
        super(tipo, nome, dono, endereco);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    public Mercado(){}

    public String getAbre() {
        return abre;
    }

    public void setAbre(String abre) {
        this.abre = abre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }
}
