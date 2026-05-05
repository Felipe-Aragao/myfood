package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.*;
import br.ufal.ic.myfood.utils.Persistencia;

import java.util.*;
import java.util.stream.Collectors;

public class EntregaManager {

    private UsuarioManager usuarioManager;
    private EmpresaManager empresaManager;
    private PedidoManager pedidoManager;
    private static final String ARQUIVO_PEDIDO = "data/entregas.xml";

    private List<Entrega> entregas;

    public EntregaManager(UsuarioManager uManager, EmpresaManager eManager, PedidoManager pManager) {
        this.usuarioManager = uManager;
        this.empresaManager = eManager;
        this.pedidoManager = pManager;

        this.entregas = new ArrayList<>();
        load();
    }

    public void save() {
        Persistencia.save(ARQUIVO_PEDIDO, entregas);
    }

    public void load() {
        entregas = Persistencia.load(ARQUIVO_PEDIDO);
    }

    public void liberarPedido(String idPedido) throws Exception {
        Pedido pedido = pedidoManager.findPedido(idPedido).orElseThrow(PedidoNaoEncontradoException::new);

        if (pedido.getEstado().equals("pronto")) {
            throw new PedidoJaLiberadoException();
        }

        if (!pedido.getEstado().equals("preparando")) {
            throw new PedidoNaoEstaSendoPreparadoException();
        }

        pedido.setEstado("pronto");
    }

    public String obterPedido(String idEntregador) throws Exception {

        Usuario entregador = usuarioManager.getUsuario(idEntregador);

        if (!(entregador instanceof Entregador)) {
            throw new UsuarioNaoEntregadorException();
        }

        String empresasString = empresaManager.getEmpresas(idEntregador);
        empresasString = empresasString.substring(2, empresasString.length() - 2);
        List<String> empresas = Arrays.stream(empresasString.split("(?<=]), (?=\\[)"))
                .map(String::trim)
                .collect(Collectors.toList());

        if (empresas.get(0).isEmpty()) {
            throw new EntregadorNaoEstaEmEmpresaException();
        }

        boolean antigo = false;
        String primeiro = "";
        for (Pedido p : pedidoManager.getPedidosList()) {
            if (empresaManager.findEmpresa(p.getEmpresa()).orElseThrow(EmpresaNaoExisteException::new).getTipo().equals("farmacia")) {
               if (isValidoObter(p, (ArrayList<String>) empresas)) {
                        return p.getNumero();
                    }
            } else {
                if (isValidoObter(p, (ArrayList<String>) empresas)) {
                    if (!antigo) {
                        antigo =true;
                        primeiro = p.getNumero();
                    }
                }
            }
        }
        if (primeiro.isEmpty()) {
            throw new NaoExistePedidoParaEntregaException();
        }

        return primeiro;
    }

    public boolean isValidoObter(Pedido p, ArrayList<String> empresas) throws Exception {
        if (p.getEstado().equals("pronto")) {
            return empresas.contains(empresaManager.findEmpresa(p.getEmpresa()).orElseThrow(EmpresaNaoExisteException::new).toString());
        }
        return false;
    }

    public String criarEntrega(String idPedido, String idEntregador, String destino) throws Exception {

        Pedido pedido = pedidoManager.findPedido(idPedido)
                .orElseThrow(PedidoNaoEncontradoException::new);

        if (!(pedido.getEstado().equals("pronto"))) {
            throw new PedidoNaoEstaProntoException();
        }

        if (!(usuarioManager.getUsuario(idEntregador) instanceof Entregador)) {
            throw new EntregadorInvalidoException();
        }

        if (entregadorTemEntregaAtiva(idEntregador)) {
            throw new EntregadorAtivoException();
        }

        String empresaNome = empresaManager.findEmpresa(pedido.getEmpresa()).orElseThrow(EmpresaNaoExisteException::new).getNome();
        String clienteNome = usuarioManager.getUsuario(pedido.getCliente()).getNome();

        if (destino == null || destino.isEmpty()) {
            destino = usuarioManager.getUsuario(pedido.getCliente()).getEndereco();
        }

        Entrega entrega = new Entrega(clienteNome, empresaNome, idPedido, idEntregador, destino);

        for (Produto p : pedidoManager.findPedido(idPedido).orElseThrow(PedidoNaoEncontradoException::new).getProdutos()) {
            entrega.getProdutos().add(p.getNome());
        }

        entregas.add(entrega);

        pedido.setEstado("entregando");

        return entrega.getId();
    }

    private boolean entregadorTemEntregaAtiva(String entregadorId) throws Exception {
        for (Entrega e : entregas) {
            if (e.getEntregadorId().equals(entregadorId)) {
                Pedido p = pedidoManager.findPedido(e.getPedidoId()).orElse(null);
                if (p != null && p.getEstado().equals("entregando")) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getEntrega(String idEntrega, String atributo) throws Exception {

        Entrega entrega = findEntrega(idEntrega).orElseThrow(PedidoNaoExisteException::new);

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        if (atributo.equalsIgnoreCase("cliente")) {
            return entrega.getClienteNome();
        } else if (atributo.equalsIgnoreCase("empresa")) {
            return entrega.getEmpresaNome();
        } else if (atributo.equalsIgnoreCase("pedido")) {
            return entrega.getPedidoId();
        } else if (atributo.equalsIgnoreCase("entregador")) {
            return usuarioManager.getUsuario(entrega.getEntregadorId()).getNome();
        } else if (atributo.equalsIgnoreCase("destino")) {
            return entrega.getDestino();
        } else if (atributo.equalsIgnoreCase("produtos")) {
            return "{" + entrega.getProdutos().toString() + "}";
        }

        throw new AtributoNaoExisteException();
    }

    public String getIdEntrega(String pedidoId) throws Exception {
        Optional<Entrega> entrega = entregas.stream()
                .filter(en -> en.getPedidoId().equals(pedidoId))
                .findFirst();

        if (entrega.isEmpty()) {
            throw new NaoExisteEntregaComEsseIdException();
        }

        return entrega.get().getId();
    }

    public Optional<Entrega> findEntrega(String id) throws Exception {
        return entregas.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public void entregar(String idEntrega) throws Exception {
        Entrega entrega = findEntrega(idEntrega).orElseThrow(NaoExisteNadaParaEntregarException::new);
        pedidoManager.findPedido(entrega.getPedidoId()).orElseThrow(PedidoNaoEncontradoException::new).setEstado("entregue");
        }
}
