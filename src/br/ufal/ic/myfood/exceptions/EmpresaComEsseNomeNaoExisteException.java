package br.ufal.ic.myfood.exceptions;

public class EmpresaComEsseNomeNaoExisteException extends Exception {
    public EmpresaComEsseNomeNaoExisteException() {
        super("Nao existe empresa com esse nome");
    }
}
