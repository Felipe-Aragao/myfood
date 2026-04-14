package br.ufal.ic.myfood;

import br.ufal.ic.myfood.managers.EmpresaManager;
import br.ufal.ic.myfood.managers.ProdutoManager;
import br.ufal.ic.myfood.managers.UsuarioManager;
import java.io.File;

public class Facade {

    UsuarioManager uManager;
    EmpresaManager eManager;
    ProdutoManager pManager;

    // Sistema
    public Facade() {
        this.uManager = new UsuarioManager();
        this.eManager = new EmpresaManager(uManager);
        this.pManager = new ProdutoManager(eManager);
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
        this.pManager = new ProdutoManager(eManager);
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

    // Produto

    public String criarProduto(String idEmpresa, String nome, float valor, String categoria) throws Exception {
        return pManager.criarProduto(idEmpresa, nome, valor, categoria);
    }

    public void editarProduto(String id, String nome, float valor, String categoria) throws Exception {
        pManager.editarProduto(id, nome, valor, categoria);
    }

    public String getProduto(String nome, String idEmpresa, String atributo) throws Exception {
        return pManager.getProduto(nome, idEmpresa, atributo);
    }

    public String listarProdutos(String idEmpresa) throws Exception {
        return pManager.listarProdutos(idEmpresa);
    }


}
