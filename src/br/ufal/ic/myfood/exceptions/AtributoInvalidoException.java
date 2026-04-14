package br.ufal.ic.myfood.exceptions;

public class AtributoInvalidoException extends Exception {

    public AtributoInvalidoException() {
        super("Atributo invalido");
    }

    public AtributoInvalidoException(int x) {
        super("Atributo nao existe");
    }
}
