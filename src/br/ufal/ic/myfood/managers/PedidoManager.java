package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PedidoManager {

    private UsuarioManager uManager;
    private EmpresaManager eManager;
    private ProdutoManager proManager;
    private static final String ARQUIVO_PEDIDO = "data/pedidos.xml";


    private List<Pedido>  pedidos;

    public PedidoManager(UsuarioManager uManager, EmpresaManager eManager, ProdutoManager proManager) {
        this.uManager = uManager;
        this.eManager = eManager;
        this.proManager = proManager;

        this.pedidos = new ArrayList<>();
        load();
    }


    // Persistência de dados
    public void save(){
        try {
            new File("./data").mkdirs();

            XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_PEDIDO));
            encoder.writeObject(pedidos);
            encoder.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void load() {
        try {
            File file = new File(ARQUIVO_PEDIDO);
            if (!file.exists()) return;

            XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
            pedidos = (List<Pedido>) decoder.readObject();
            decoder.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String criarPedido(String idCliente, String idEmpresa) throws Exception {

        validarPedido(idCliente, idEmpresa);

        Pedido novo = new Pedido(idCliente, idEmpresa);
        this.pedidos.add(novo);
        return novo.getNumero();
    }

    public void adicionarProduto(String idPedido, String idProduto) throws Exception {
        Pedido pedido = findPedido(idPedido);

        validarPedido(pedido);

        Empresa empresa = eManager.findEmpresa(pedido.getEmpresa());

        for (Produto p : empresa.getProdutos()) {
            if (p.getId().equals(idProduto)) {
                pedido.getProdutos().add(p);
                pedido.setValor(pedido.getValor() + p.getValor());
                return;
            }
        }
        throw new ProdutoNaoPertenceAEmpresaException();
    }

    public Pedido findPedido(String id) throws Exception {
        for (Pedido p : pedidos) {
            if (p.getNumero().equals(id)){
                return p;
            }
        }

        throw new PedidoNaoExisteException();
    }

    public void validarPedido(Pedido p) throws Exception {
        if (!(p.getEstado().equals("aberto"))) {
            throw new ModificarPedidoFechadoException();
        }
    }

    public void validarPedido(String cliente, String empresa) throws Exception {
        validarCliente(cliente);

        for (Pedido p : pedidos) {
            if (p.getEmpresa().equals(empresa) && p.getCliente().equals(cliente)
                    &&p.getEstado().equals("aberto")) {
                throw new MuitosPedidosEmAbertoException();
            }
        }
    }

    public void validarCliente(String cliente) throws Exception {
        if (!(uManager.getUsuario(cliente) instanceof Cliente)) {
            throw new UsuarioNaoPodeCriarPedidoException();
        }

    }

    public String getPedidos(String idPedido, String atributo) throws Exception {

        Pedido pedido = findPedido(idPedido);

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        if (atributo.equalsIgnoreCase("cliente")) {
            return uManager.getUsuario(pedido.getCliente()).getNome();
        } else if (atributo.equalsIgnoreCase("empresa")) {
            return eManager.findEmpresa(pedido.getEmpresa()).getNome();
        } else if (atributo.equalsIgnoreCase("estado")) {
            return pedido.getEstado();
        } else if (atributo.equalsIgnoreCase("valor")) {
            return String.format(Locale.US, "%.2f", pedido.getValor());
        } else if (atributo.equalsIgnoreCase("produtos")) {
            String lista = pedido.getProdutos().stream()
                    .map(Produto::getNome)
                    .collect(Collectors.joining(", "));
            return "{[" + lista + "]}";
        }

        throw new AtributoNaoExisteException();
    }

    public void removerProduto(String idPedido, String produto) throws Exception {

        if (produto == null || produto.isEmpty()) {
            throw new ProdutoInvalidoException();
        }

        Pedido pedido = findPedido(idPedido);

        if (!(pedido.getEstado().equals("aberto"))) {
            throw new RemoverDePedidoFechadoException();
        }

        for (Produto p : pedido.getProdutos()) {
            if (p.getNome().equals(produto)) {
                pedido.setValor(pedido.getValor() - p.getValor());
                pedido.getProdutos().remove(p);
                return;
            }
        }

        throw new ProdutoNaoEncontradoException();
    }

    public String getNumeroPedido(String idCliente, String idEmpresa, int indice) throws Exception {

        if (indice < 0) {
            throw new IndiceInvalidoException();
        }

        ArrayList<Pedido> filtro = pedidos.stream()
                .filter(e -> e.getCliente().equals(idCliente) && e.getEmpresa().equals(idEmpresa))
                .collect(Collectors.toCollection(ArrayList::new));

        if (indice >= filtro.size()) {
            throw new IndiceMaiorQueOEsperadoException();
        }

        return filtro.get(indice).getNumero();
    }

    public void fecharPedido(String idPedido) throws Exception {
        for (Pedido p : pedidos) {
            if (p.getNumero().equals(idPedido)){
                p.setEstado("preparando");
                return;
            }
        }

        throw new PedidoNaoEncontradoException();
    }
}
