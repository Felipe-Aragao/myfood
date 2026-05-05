package br.ufal.ic.myfood.exceptions;

public class EntregadorAtivoException extends Exception {
    public EntregadorAtivoException() {
        super("Entregador ainda em entrega");
    }
}
