package br.ufal.ic.myfood.exceptions;

public class UsuarioNaoPodeCriarPedidoException extends Exception {
     public UsuarioNaoPodeCriarPedidoException() {
         super("Dono de empresa nao pode fazer um pedido");
     }
}
