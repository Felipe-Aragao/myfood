package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Cliente;
import br.ufal.ic.myfood.models.Dono;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {

    private List<Usuario> usuarios;
    private static final String ARQUIVO_USUARIO = "data/usuarios.xml";

    public UsuarioManager() {
        usuarios = new ArrayList<>();
        load();
    }

    public void save(){
        Persistencia.save(ARQUIVO_USUARIO, usuarios);
    }

    public void load() {
        usuarios = Persistencia.load(ARQUIVO_USUARIO);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        validarUsuario(email, nome, senha, endereco, cpf);
        usuarios.add(new Dono(nome, email, senha, endereco, cpf));
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        validarUsuario(email, nome, senha, endereco);
        usuarios.add(new Cliente(nome, email, senha, endereco));
    }

    public Usuario getUsuario(String id) throws UsuarioNaoExisteException {

        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst()
                .orElseThrow(UsuarioNaoExisteException::new);
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

        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(email))) {
            throw new UsuarioJaExisteException();
        }
    }

    private void validarUsuario(String email, String nome, String senha, String endereco, String cpf) throws Exception {
        if (cpf == null || cpf.length() != 14) {
            throw new CPFInvalidoException();
        }

        validarUsuario(email, nome, senha, endereco);
    }

    public String getAtributoUsuario(String id, String atributo) throws Exception {

        Usuario user = getUsuario(id);

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        if (atributo.equalsIgnoreCase("nome")) {
            return user.getNome();
        } else if (atributo.equalsIgnoreCase("email")) {
            return user.getEmail();
        } else if (atributo.equalsIgnoreCase("endereco")) {
            return user.getEndereco();
        } else if (atributo.equalsIgnoreCase("cpf")) {
            if (user instanceof Dono) {
                return ((Dono) user).getCpf();
            }
        } else if (atributo.equalsIgnoreCase("senha")) {
            return  user.getSenha();
        }
        throw new AtributoInvalidoException();
    }

    public String login(String email, String senha) throws LoginInvalidoException {
        return usuarios.stream().filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .map(Usuario::getId)
                .findFirst()
                .orElseThrow(LoginInvalidoException::new);
    }

}
