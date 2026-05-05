package br.ufal.ic.myfood.exceptions;

public class NaoExisteEntregaComEsseIdException extends Exception {
    public NaoExisteEntregaComEsseIdException() {
        super("Nao existe entrega com esse id");
    }
}
