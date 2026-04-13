package br.ufal.ic.myfood.exceptions;

public class EnderecoJaUtilizadoException extends Exception {

    public EnderecoJaUtilizadoException() {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }
}
