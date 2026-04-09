package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {

    private List<Usuario> usuarios;

    public UsuarioManager() {
        usuarios = new ArrayList<>();
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        validarUsuario(email, nome, senha, endereco, cpf);
        usuarios.add(new Empresa(nome, email, senha, endereco, cpf));
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        validarUsuario(email, nome, senha, endereco);
        usuarios.add(new Cliente(nome, email, senha, endereco));
    }

    private void validarUsuario(String email, String nome, String senha, String endereco) throws Exception {

        if (nome == null || nome.isBlank()) {
            throw new NomeInvalidoException();
        }

        String emailRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (email == null || email.isBlank() || !email.matches(emailRegex)) {
            throw new EmailInvalidoException();
        }

        if (senha == null || senha.isBlank()) {
            throw new SenhaInvalidaException();
        }

        if (endereco == null || endereco.isBlank()) {
            throw new EnderecoInvalidoException();
        }

        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email)) {
                throw new UsuarioJaExisteException();
            }
        }
    }

    private void validarUsuario(String email, String nome, String senha, String endereco, String cpf) throws Exception {
        if (cpf == null || cpf.length() != 14) {
            throw new CPFInvalidoException();
        }

        validarUsuario(email, nome, senha, endereco);
    }

    public String getAtributoUsuario(String id, String atributo) throws UsuarioNaoExisteException {
        for (Usuario user : usuarios) {
            if (user.getId().equals(id)) {
                if (atributo.equalsIgnoreCase("nome")) {
                    return user.getNome();
                } else if (atributo.equalsIgnoreCase("email")) {
                    return user.getEmail();
                } else if (atributo.equalsIgnoreCase("endereco")) {
                    return user.getEndereco();
                } else if (atributo.equalsIgnoreCase("cpf")) {
                    if (user instanceof Empresa) {
                        return ((Empresa) user).getCpf();
                    }
                }
            }
        }

        throw new UsuarioNaoExisteException();
    }

    public String login(String email, String senha) throws LoginInvalidoException {
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email)) {
                if (user.getSenha().equals(senha)){
                    return user.getId();
                }
            }
        }

        throw new LoginInvalidoException();
    }
}
