package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

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
    private static final String ARQUIVO = "data/empresas.xml";


    public EmpresaManager(UsuarioManager uManager) {
        this.empresas = new ArrayList<>();
        this.uManager = uManager;
        load();
    }

    public void save(){
        try {
            new File("./data").mkdirs();

            XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO));
            encoder.writeObject(empresas);
            encoder.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void load() {
        try {
            File file = new File(ARQUIVO);
            if (!file.exists()) return;

            XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
            empresas = (List<Empresa>) decoder.readObject();
            decoder.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String criarEmpresa(String tipo, String dono, String nome, String endereco, String tipoCozinha) throws Exception {

        validarEmpresa(dono, nome, endereco);
        Empresa novo = new Empresa(dono, nome, endereco, tipoCozinha);
        empresas.add(novo);
        return novo.getId();
    }

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

    public String getAtributoEmpresa(String id, String atributo) throws Exception {

        if (atributo == null) {
            throw new AtributoInvalidoException();
        }

        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(id)) {
                if (atributo.equalsIgnoreCase("dono")) {
                    return uManager.getUsuario(empresa.getAtributo("dono")).getNome();
                }
                return empresa.getAtributo(atributo);
            }
        }
        throw new EmpresaNaoExisteException();
    }

    public String getEmpresasDoUsuario(String dono) throws Exception {
        validarDono(dono);

        boolean first = true;
        StringBuilder str = new StringBuilder();
        str.append("{[");
        for (Empresa empresa : empresas) {
            if (empresa.getDono().equals(dono)) {
                if (first) {
                    str.append(empresa);
                    first = false;
                } else {
                    str.append(", ");
                    str.append(empresa);
                }
            }
        }
        str.append("]}");

        return str.toString();
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
}