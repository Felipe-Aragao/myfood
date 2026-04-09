package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.UsuarioJaExisteException;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.UsuarioManager;

public class Facade {

    UsuarioManager uManager;

    public void zerarSistema() {
        this.uManager = new UsuarioManager();
    }

    public String getAtributoUsuario(String id, String atributo)
            throws UsuarioNaoExisteException {
        return uManager.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws Exception {
        uManager.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws Exception {
        uManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public String login(String email, String senha) throws Exception{
        return uManager.login(email, senha);
    }

    public void encerrarSistema() {
        return;
    }

}
