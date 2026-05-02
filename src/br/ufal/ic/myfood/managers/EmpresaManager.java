package br.ufal.ic.myfood.managers;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.*;
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

    //  Restaurante
    public String criarEmpresa(String tipo, String dono, String nome, String endereco, String tipoCozinha) throws Exception {

        validarEmpresa(tipo, dono, nome, endereco);
        Empresa novo = new Restaurante(tipo, dono, nome, endereco, tipoCozinha);
        empresas.add(novo);
        return novo.getId();
    }


    //  Mercado
    public String criarEmpresa(String tipo, String dono, String nome, String endereco,
                               String abre, String fecha, String tipoMercado) throws Exception {

        validarHorario(abre, fecha);
        validarMercado(tipoMercado);
        validarEmpresa(tipo, dono, nome, endereco);
        Empresa novo = new Mercado(tipo, dono, nome, endereco, abre, fecha, tipoMercado);
        empresas.add(novo);
        return novo.getId();
    }

    public String criarEmpresa(String tipo, String dono, String nome, String endereco,
                               boolean horas24, int funcionarios) throws Exception {

        validarEmpresa(tipo, dono, nome, endereco);
        Empresa novo = new Farmacia(tipo, dono, nome, endereco, horas24, funcionarios);
        empresas.add(novo);
        return novo.getId();
    }

    public void alterarFuncionamento(String mercadoId, String abre, String fecha) throws Exception {

        Empresa mercado = findEmpresa(mercadoId).orElseThrow(EmpresaNaoExisteException::new);

        if (!mercado.getTipo().equals("mercado")) {
            throw new MercadoInvalidoException();
        }

        validarHorario(abre, fecha);

        ((Mercado)mercado).setAbre(abre);
        ((Mercado)mercado).setFecha(fecha);
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
            if (empresa.getTipo().equals("restaurante")) {
                return ((Restaurante) empresa).getTipoCozinha();
            }
        } else if (atributo.equalsIgnoreCase("abre")) {
            if (empresa.getTipo().equals("mercado")) {
                return ((Mercado) empresa).getAbre();
            }
        } else if (atributo.equalsIgnoreCase("fecha")) {
            if (empresa.getTipo().equals("mercado")) {
                return ((Mercado) empresa).getFecha();
            }
        } else if (atributo.equalsIgnoreCase("tipomercado")) {
            if (empresa.getTipo().equals("mercado")) {
                return ((Mercado) empresa).getTipoMercado();
            }
        } else if (atributo.equalsIgnoreCase("aberto24horas")) {
            if (empresa.getTipo().equals("farmacia")) {
                boolean isAberto24 = ((Farmacia) empresa).isAberto24horas();
                if (isAberto24) {
                    return "true";
                } else {
                    return "false";
                }
            }
        } else if (atributo.equalsIgnoreCase("numerofuncionarios")) {
            if (empresa.getTipo().equals("farmacia")) {
                return "" + ((Farmacia) empresa).getNumeroFuncionarios();
            }
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
    public void validarEmpresa(String tipo, String dono, String nome, String endereco) throws Exception {

        validarDono(dono);

        if (tipo == null || tipo.isEmpty()) {
            throw new TipoInvalidoException();
        }

        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoEmpresaInvalidoException();
        }


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

    public void validarMercado(String tipoMercado) throws Exception{
        if (tipoMercado == null || tipoMercado.isEmpty()) {
            throw new TipoMercadoInvalidoException();
        }
    }

    public void validarHorario(String abre, String fecha) throws Exception {

        String horaRegex = "^\\d{2}:\\d{2}$";

        if (abre == null || fecha == null ) {
            throw new HorarioInvalidoException();
        }
        if (abre.isEmpty() || !abre.matches(horaRegex)) {
            throw new FormatoDeHoraException();
        }
        if (fecha.isEmpty() || !fecha.matches(horaRegex)) {
            throw new FormatoDeHoraException();
        }

        String[] horaAbre = abre.split(":");
        String[] horaFecha = fecha.split(":");

        horaRegex = "^([01]\\d|2[0-3]):([0-5]\\d)$";
        if (!abre.matches(horaRegex) || !fecha.matches(horaRegex)) {
            throw new HorarioInvalidoException();
        }

        if (Integer.parseInt(horaFecha[0]) == Integer.parseInt(horaAbre[0])) {
            if (Integer.parseInt(horaFecha[1]) < Integer.parseInt(horaAbre[1])) {
                throw new HorarioInvalidoException();
            }
        } else if (Integer.parseInt(horaFecha[0]) < Integer.parseInt(horaAbre[0])) {
            throw new HorarioInvalidoException();
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