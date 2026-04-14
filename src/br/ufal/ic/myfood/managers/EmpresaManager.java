package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Dono;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Persistencia.save(ARQUIVO_EMPRESA, empresas);
    }

    public void load() {
        empresas = Persistencia.load(ARQUIVO_EMPRESA);
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

        Empresa empresa = findEmpresa(id).orElseThrow(EmpresaNaoExisteException::new);

        if (atributo.equalsIgnoreCase("dono")) {
            return uManager.getUsuario(empresa.getDono()).getNome();
        } else if (atributo.equalsIgnoreCase("nome")) {
            return empresa.getNome();
        } else if (atributo.equalsIgnoreCase("endereco")) {
            return empresa.getEndereco();
        } else if (atributo.equalsIgnoreCase("tipocozinha")) {
            return  empresa.getTipoCozinha();
        }
        throw new AtributoInvalidoException();
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
            throw new EmpresaComEsseNomeNaoExisteException();
        }

        if (indice >= filtro.size()) {
            throw new IndiceMaiorQueOEsperadoException();
        }

        return filtro.get(indice).getId();
    }

    public Optional<Empresa> findEmpresa(String id) throws Exception {
        return empresas.stream().filter(e -> e.getId().equals(id)).findFirst();
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

    public List<Empresa> getEmpresas() {
        return empresas;
    }
}