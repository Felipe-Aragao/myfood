package br.ufal.ic.myfood;

import br.ufal.ic.myfood.models.EmpresaManager;
import br.ufal.ic.myfood.models.UsuarioManager;
import java.io.File;

public class Facade {

    UsuarioManager uManager;
    EmpresaManager eManager;

    // Sistema
    public Facade() {
        this.uManager = new UsuarioManager();
        this.eManager = new EmpresaManager(uManager);
    }

    public void encerrarSistema() {
        uManager.save();
        eManager.save();
    }

    public void zerarSistema() {
        new File("data/usuarios.xml").delete();
        new File("data/empresas.xml").delete();
        this.uManager = new UsuarioManager();
        this.eManager = new EmpresaManager(uManager);
    }

    // Usuários

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws Exception {
        uManager.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws Exception {
        uManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public String getAtributoUsuario(String id, String atributo)
            throws Exception {
        return uManager.getAtributoUsuario(id, atributo);
    }

    public String login(String email, String senha) throws Exception{
        return uManager.login(email, senha);
    }

    // Empresa

    public String criarEmpresa(String tipo, String dono, String nome, String endereco, String tipoCozinha)
            throws Exception {
        return eManager.criarEmpresa(tipo, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(String idDono) throws Exception{
        return eManager.getEmpresasDoUsuario(idDono);
    }

    public String getAtributoEmpresa(String id, String atributo) throws Exception{
        return eManager.getAtributoEmpresa(id, atributo);
    }

    public String getIdEmpresa(String idDono, String nome, int indice) throws Exception {
        return eManager.getIdEmpresa(idDono, nome, indice);
    }




}
