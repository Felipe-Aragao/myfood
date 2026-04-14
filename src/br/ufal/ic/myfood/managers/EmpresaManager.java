package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Dono;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;

import java.beans.PropertyDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmpresaManager {

    private List<Empresa> empresas;
    private UsuarioManager uManager;
    private static final String ARQUIVO_EMPRESA = "data/empresas.xml";

    // Construtor
    public EmpresaManager(UsuarioManager uManager) {
        this.empresas = new ArrayList<>();
        this.uManager = uManager;
        load();
    }

    // Persistência de dados
    public void save(){
        try {
            new File("./data").mkdirs();

            XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_EMPRESA));
            encoder.writeObject(empresas);
            encoder.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void load() {
        try {
            File file = new File(ARQUIVO_EMPRESA);
            if (!file.exists()) return;

            XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
            empresas = (List<Empresa>) decoder.readObject();
            decoder.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // Empresa
    public String criarEmpresa(String tipo, String dono, String nome, String endereco, String tipoCozinha) throws Exception {

        validarEmpresa(dono, nome, endereco);
        Empresa novo = new Empresa(dono, nome, endereco, tipoCozinha);
        empresas.add(novo);
        return novo.getId();
    }

    public String getAtributoEmpresa(String id, String atributo) throws Exception {

        if (atributo == null) {
            throw new AtributoInvalidoException();
        }

        Empresa empresa = findEmpresa(id);

        if (atributo.equalsIgnoreCase("dono")) {
            return uManager.getUsuario(empresa.getAtributo("dono")).getNome();
        }
        return empresa.getAtributo(atributo);

    }

    public String getEmpresasDoUsuario(String dono) throws Exception {
        validarDono(dono);

        String lista = empresas.stream()
                .filter(e -> e.getDono().equals(dono))
                .map(Empresa::toString)
                .collect(Collectors.joining(", "));
        return "{[" + lista + "]}";
    }

    public String getIdEmpresa(String idDono, String nome, int indice) throws Exception {

        if (nome == null || nome.isBlank()) {
            throw new NomeInvalidoException();
        }

        if (indice < 0) {
            throw new IndiceInvalidoException();
        }

        ArrayList<Empresa> filtro = empresas.stream()
                .filter(e -> e.getDono().equals(idDono) && e.getNome().equals(nome))
                .collect(Collectors.toCollection(ArrayList::new));

        if (filtro.isEmpty()){
            throw new EmpresaNaoExisteException(0);
        }

        if (indice >= filtro.size()) {
            throw new IndiceMaiorQueOEsperadoException();
        }

        return filtro.get(indice).getId();
    }

    public Empresa findEmpresa(String id) throws Exception {
        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(id)) {
                return empresa;
            }
        }
        throw new EmpresaNaoExisteException();
    }

    // Validação
    public void validarEmpresa(String dono, String nome, String endereco) throws Exception {

        validarDono(dono);

        for (Empresa empresa : empresas) {
            if (empresa.getNome().equals(nome)) {
                if (!empresa.getDono().equals(dono)) {
                    throw new EmpresaJaExisteException();
                } else if (empresa.getEndereco().equals(endereco)) {
                    throw new EnderecoJaUtilizadoException();
                }
            }
        }
    }

    public void validarDono(String dono) throws Exception {
        if (!(uManager.getUsuario(dono) instanceof Dono)) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }
    }

    public void validarProduto(String nome, float valor, String categoria) throws Exception{
        if (nome == null || nome.isBlank()) {
            throw new NomeInvalidoException();
        }
        if (valor < 0) {
            throw new ValorInvalidoException();
        }
        if (categoria == null || categoria.isBlank()) {
            throw new CategoriaInvalidaException();
        }
    }

    // Produtos
    public String criarProduto(String idEmpresa, String nome, float valor, String categoria) throws Exception {

        validarProduto(nome, valor, categoria);

        Empresa empresa = findEmpresa(idEmpresa);
        for (Produto p : empresa.getProdutos()) {
            if (p.getNome().equals(nome))
                throw new ProdutoJaExisteException();
        }

        Produto novo = new Produto(nome, valor, categoria);
        empresa.getProdutos().add(novo);
        return novo.getId();
    }

    public void editarProduto(String id, String nome, float valor, String categoria) throws Exception {

        validarProduto(nome, valor, categoria);

        for (Empresa e: empresas) {
            for (Produto p : e.getProdutos()) {
                if (p.getId().equals(id)) {
                    p.setNome(nome);
                    p.setValor(valor);
                    p.setCategoria(categoria);
                    return;
                }
            }
        }

        throw new ProdutoNaoCadastradoException();
    }

    public String getProduto(String nome, String idEmpresa, String atributo) throws Exception {
        Empresa empresa = findEmpresa(idEmpresa);

        if (atributo.equalsIgnoreCase("empresa")) {
            return empresa.getNome();
        }

        for (Produto p : empresa.getProdutos()) {
            if (p.getNome().equals(nome)) {
                return p.getAtributo(atributo);
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    public String listarProdutos(String idEmpresa) throws Exception {
        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(idEmpresa)) {
                String lista = empresa.getProdutos().stream()
                        .map(Produto::getNome)
                        .collect(Collectors.joining(", "));
                return "{[" + lista + "]}";
            }
        }
        throw new EmpresaNaoEncontradaException();
    }
}