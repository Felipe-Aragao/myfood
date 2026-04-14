package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoPertenceAEmpresaException extends Exception {
    public ProdutoNaoPertenceAEmpresaException() {
        super("O produto nao pertence a essa empresa");
    }
}
