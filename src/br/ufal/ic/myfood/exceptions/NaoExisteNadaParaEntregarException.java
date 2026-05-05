package br.ufal.ic.myfood.exceptions;

public class NaoExisteNadaParaEntregarException extends Exception {
    public NaoExisteNadaParaEntregarException() {
        super("Nao existe nada para ser entregue com esse id");
    }
}
