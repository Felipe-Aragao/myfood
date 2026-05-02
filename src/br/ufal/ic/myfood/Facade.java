package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.FormatoDeHoraException;
import br.ufal.ic.myfood.managers.EmpresaManager;
import br.ufal.ic.myfood.managers.PedidoManager;
import br.ufal.ic.myfood.managers.ProdutoManager;
import br.ufal.ic.myfood.managers.UsuarioManager;

import java.io.File;

public class Facade {

    UsuarioManager uManager;
    EmpresaManager eManager;
    ProdutoManager proManager;
    PedidoManager pedManager;

    // Sistema
    public Facade() {
        this.uManager = new UsuarioManager();
        this.eManager = new EmpresaManager(uManager);
        this.proManager = new ProdutoManager(eManager);
        this.pedManager = new PedidoManager(uManager, eManager);
    }

    public void encerrarSistema() {
        uManager.save();
        eManager.save();
        pedManager.save();
    }

    public void zerarSistema() {
        new File("data/usuarios.xml").delete();
        new File("data/empresas.xml").delete();
        new File("data/pedidos.xml").delete();
        this.uManager = new UsuarioManager();
        this.eManager = new EmpresaManager(uManager);
        this.proManager = new ProdutoManager(eManager);
        this.pedManager = new PedidoManager(uManager, eManager);
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

    public String criarEmpresa(String tipo, String dono, String nome, String endereco,
                               String abre, String fecha, String tipoMercado)
            throws Exception {
        return eManager.criarEmpresa(tipo, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public String criarEmpresa(String tipo, String dono, String nome, String endereco,
                               boolean horas24, int funcionarios) throws Exception {
        return eManager.criarEmpresa(tipo, dono, nome, endereco, horas24, funcionarios);
    }

    public void alterarFuncionamento(String idMercado, String abre, String fecha) throws Exception{
        eManager.alterarFuncionamento(idMercado, abre, fecha);
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
        return proManager.criarProduto(idEmpresa, nome, valor, categoria);
    }

    public void editarProduto(String id, String nome, float valor, String categoria) throws Exception {
        proManager.editarProduto(id, nome, valor, categoria);
    }

    public String getProduto(String nome, String idEmpresa, String atributo) throws Exception {
        return proManager.getProduto(nome, idEmpresa, atributo);
    }

    public String listarProdutos(String idEmpresa) throws Exception {
        return proManager.listarProdutos(idEmpresa);
    }

    // Pedido

    public String criarPedido(String idCliente, String idEmpresa) throws Exception {
        return pedManager.criarPedido(idCliente, idEmpresa);
    }

    public void adicionarProduto(String idPedido, String idProduto) throws Exception {
        pedManager.adicionarProduto(idPedido, idProduto);
    }

    public String getPedidos(String idPedido, String atributo) throws Exception {
        return pedManager.getPedidos(idPedido, atributo);
    }

    public void fecharPedido(String idPedido) throws Exception {
        pedManager.fecharPedido(idPedido);
    }

    public void removerProduto(String idPedido, String produto) throws Exception {
        pedManager.removerProduto(idPedido, produto);
    }

    public String getNumeroPedido(String idCliente, String idEmpresa, int indice) throws Exception {
        return pedManager.getNumeroPedido(idCliente, idEmpresa, indice);
    }
}
