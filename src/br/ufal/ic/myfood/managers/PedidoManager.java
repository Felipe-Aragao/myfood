package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.*;
import br.ufal.ic.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoManager {

    private UsuarioManager uManager;
    private EmpresaManager eManager;
    private static final String ARQUIVO_PEDIDO = "data/pedidos.xml";


    private List<Pedido>  pedidos;

    public PedidoManager(UsuarioManager uManager, EmpresaManager eManager) {
        this.uManager = uManager;
        this.eManager = eManager;

        this.pedidos = new ArrayList<>();
        load();
    }


    // Persistência de dados
    public void save(){
        Persistencia.save(ARQUIVO_PEDIDO, pedidos);
    }

    public void load() {
        pedidos = Persistencia.load(ARQUIVO_PEDIDO);
    }

    public String criarPedido(String idCliente, String idEmpresa) throws Exception {

        validarPedido(idCliente, idEmpresa);

        Pedido novo = new Pedido(idCliente, idEmpresa);
        this.pedidos.add(novo);
        return novo.getNumero();
    }

    public void adicionarProduto(String idPedido, String idProduto) throws Exception {
        Pedido pedido = findPedido(idPedido).orElseThrow(PedidoNaoExisteException::new);

        validarPedido(pedido);

        Empresa empresa = eManager.findEmpresa(pedido.getEmpresa()).orElseThrow(EmpresaNaoExisteException::new);

        for (Produto p : empresa.getProdutos()) {
            if (p.getId().equals(idProduto)) {
                pedido.getProdutos().add(p);
                pedido.setValor(pedido.getValor() + p.getValor());
                return;
            }
        }
        throw new ProdutoNaoPertenceAEmpresaException();
    }

    public Optional<Pedido> findPedido(String id) throws Exception {
        return pedidos.stream().filter(e -> e.getNumero().equals(id)).findFirst();
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

        Pedido pedido = findPedido(idPedido).orElseThrow(PedidoNaoExisteException::new);

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        if (atributo.equalsIgnoreCase("cliente")) {
            return uManager.getUsuario(pedido.getCliente()).getNome();
        } else if (atributo.equalsIgnoreCase("empresa")) {
            return eManager.findEmpresa(pedido.getEmpresa()).orElseThrow(EmpresaNaoExisteException::new).getNome();
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

        Pedido pedido = findPedido(idPedido).orElseThrow(PedidoNaoExisteException::new);

        if (!(pedido.getEstado().equals("aberto"))) {
            throw new RemoverDePedidoFechadoException();
        }

        Produto pRemover = pedido.getProdutos().stream().filter(p -> p.getNome().equals(produto)).findFirst()
                .orElseThrow(ProdutoNaoEncontradoException::new);

        pedido.setValor(pedido.getValor() - pRemover.getValor());
        pedido.getProdutos().remove(pRemover);

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

        Pedido pedido = findPedido(idPedido).orElseThrow(PedidoNaoEncontradoException::new);

        pedido.setEstado("preparando");
    }
}
