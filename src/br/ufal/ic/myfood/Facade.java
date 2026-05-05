package br.ufal.ic.myfood;

import br.ufal.ic.myfood.managers.*;

import java.io.File;

public class Facade {

    UsuarioManager usuarioManager;
    EmpresaManager empresaManager;
    ProdutoManager produtoManager;
    PedidoManager pedidoManager;
    EntregaManager entregaManager;

    // Sistema
    public Facade() {
        this.usuarioManager = new UsuarioManager();
        this.empresaManager = new EmpresaManager(usuarioManager);
        this.produtoManager = new ProdutoManager(empresaManager);
        this.pedidoManager = new PedidoManager(usuarioManager, empresaManager);
        this.entregaManager = new EntregaManager(usuarioManager, empresaManager, pedidoManager);

    }

    public void encerrarSistema() {
        usuarioManager.save();
        empresaManager.save();
        pedidoManager.save();
        entregaManager.save();
    }

    public void zerarSistema() {
        new File("data/usuarios.xml").delete();
        new File("data/empresas.xml").delete();
        new File("data/pedidos.xml").delete();
        new File("data/entregas.xml").delete();
        this.usuarioManager = new UsuarioManager();
        this.empresaManager = new EmpresaManager(usuarioManager);
        this.produtoManager = new ProdutoManager(empresaManager);
        this.pedidoManager = new PedidoManager(usuarioManager, empresaManager);
        this.entregaManager = new EntregaManager(usuarioManager, empresaManager, pedidoManager);
    }

    // Usuários

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws Exception {
        usuarioManager.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws Exception {
        usuarioManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa)
            throws Exception {
        usuarioManager.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public String getAtributoUsuario(String id, String atributo)
            throws Exception {
        return usuarioManager.getAtributoUsuario(id, atributo);
    }

    public String login(String email, String senha) throws Exception{
        return usuarioManager.login(email, senha);
    }

    // Empresa

    public void cadastrarEntregador(String idEmpresa, String idEntregador) throws Exception {
        empresaManager.cadastrarEntregador(idEmpresa, idEntregador);
    }

    public String getEntregadores(String idEmpresa) throws Exception {
        return empresaManager.getEntregadores(idEmpresa);
    }

    public String getEmpresas(String entregadorId) throws Exception {
        return empresaManager.getEmpresas(entregadorId);
    }

    public String criarEmpresa(String tipo, String dono, String nome, String endereco, String tipoCozinha)
            throws Exception {
        return empresaManager.criarEmpresa(tipo, dono, nome, endereco, tipoCozinha);
    }

    public String criarEmpresa(String tipo, String dono, String nome, String endereco,
                               String abre, String fecha, String tipoMercado)
            throws Exception {
        return empresaManager.criarEmpresa(tipo, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public String criarEmpresa(String tipo, String dono, String nome, String endereco,
                               boolean horas24, int funcionarios) throws Exception {
        return empresaManager.criarEmpresa(tipo, dono, nome, endereco, horas24, funcionarios);
    }

    public void alterarFuncionamento(String idMercado, String abre, String fecha) throws Exception{
        empresaManager.alterarFuncionamento(idMercado, abre, fecha);
    }

    public String getEmpresasDoUsuario(String idDono) throws Exception{
        return empresaManager.getEmpresasDoUsuario(idDono);
    }

    public String getAtributoEmpresa(String id, String atributo) throws Exception{
        return empresaManager.getAtributoEmpresa(id, atributo);
    }

    public String getIdEmpresa(String idDono, String nome, int indice) throws Exception {
        return empresaManager.getIdEmpresa(idDono, nome, indice);
    }

    // Produto

    public String criarProduto(String idEmpresa, String nome, float valor, String categoria) throws Exception {
        return produtoManager.criarProduto(idEmpresa, nome, valor, categoria);
    }

    public void editarProduto(String id, String nome, float valor, String categoria) throws Exception {
        produtoManager.editarProduto(id, nome, valor, categoria);
    }

    public String getProduto(String nome, String idEmpresa, String atributo) throws Exception {
        return produtoManager.getProduto(nome, idEmpresa, atributo);
    }

    public String listarProdutos(String idEmpresa) throws Exception {
        return produtoManager.listarProdutos(idEmpresa);
    }

    // Pedido

    public String criarPedido(String idCliente, String idEmpresa) throws Exception {
        return pedidoManager.criarPedido(idCliente, idEmpresa);
    }

    public void adicionarProduto(String idPedido, String idProduto) throws Exception {
        pedidoManager.adicionarProduto(idPedido, idProduto);
    }

    public String getPedidos(String idPedido, String atributo) throws Exception {
        return pedidoManager.getPedidos(idPedido, atributo);
    }

    public void fecharPedido(String idPedido) throws Exception {
        pedidoManager.fecharPedido(idPedido);
    }

    public void removerProduto(String idPedido, String produto) throws Exception {
        pedidoManager.removerProduto(idPedido, produto);
    }

    public String getNumeroPedido(String idCliente, String idEmpresa, int indice) throws Exception {
        return pedidoManager.getNumeroPedido(idCliente, idEmpresa, indice);
    }

    //Entrega

    public void liberarPedido(String idPedido) throws Exception {
        entregaManager.liberarPedido(idPedido);
    }

    public String obterPedido(String idEntregador) throws Exception {
        return entregaManager.obterPedido(idEntregador);
    }

    public String criarEntrega(String idPedido, String idEntregador, String destino) throws Exception {
        return entregaManager.criarEntrega(idPedido, idEntregador, destino);
    }

    public String getEntrega(String idEntrega, String atributo) throws Exception {
        return entregaManager.getEntrega(idEntrega, atributo);
    }

    public String getIdEntrega(String pedidoId) throws Exception {
        return entregaManager.getIdEntrega(pedidoId);
    }

    public void entregar(String idEntrega) throws Exception {
        entregaManager.entregar(idEntrega);
    }
}
