package br.ufrpe.wanderlustapp.infra.recomendacao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoaPrato.persistencia.PessoaPratoDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;
import br.ufrpe.wanderlustapp.usuario.persistencia.UsuarioDAO;

public class Recomendacao {
    private Context context;
    private UsuarioDAO usuarioDAO = new UsuarioDAO(context);
    private PratoTipicoDAO pratoTipicoDAO = new PratoTipicoDAO(context);
    private PessoaPratoDAO pessoaPratoDAO = new PessoaPratoDAO(context);
    private Map<Usuario,HashMap<PratoTipico,Float>> usersMatrix;
    private List<PratoTipico> listaPratos;
    private List<PratoTipico> listaPratosRecomendados;
    private Map<Usuario, HashMap<PratoTipico, Float>> predicao;

    public Recomendacao(){
        listaPratos = pratoTipicoDAO.getListPrato();
        usersMatrix = criaMatrizCliente();
        predicao = SlopeOne.slopeOne(usersMatrix, listaPratos);
        listaPratosRecomendados = getOrderList(getRecomendacao());
    }

    public Map<Usuario,HashMap<PratoTipico,Float>> criaMatrizCliente(){
        Map<Usuario,HashMap<PratoTipico,Float>> matrizClientes = new HashMap<>();
        List<Usuario> listaClientes = usuarioDAO.getList();
        for(Usuario cliente: listaClientes){
            matrizClientes.put(cliente, criaMatrizRestaurante(cliente.getId()));
        }
        return matrizClientes;
    }

    private HashMap<PratoTipico,Float> criaMatrizRestaurante(long idPessoa){
        HashMap<PratoTipico,Float> matrizRestaurante = new HashMap<>();
        for(PratoTipico pratoTipico: listaPratos){
            int avaliacaoRestaurante = pessoaPratoDAO.getPessoaPrato(idPessoa, pratoTipico.getId()).getNota();
            if(avaliacaoRestaurante != -1){
                matrizRestaurante.put(pratoTipico, avaliacaoRestaurante);
            }
        }
        return matrizRestaurante;
    }

    private List<Avaliacao> getRecomendacao(){
        List<Avaliacao> notasCliente = new ArrayList<>();
        Usuario usuario = findCliente();
        HashMap<PratoTipico, Float> avaliacoes = predicao.get(usuario);
        for(Map.Entry r: avaliacoes.entrySet()){
            PratoTipico pratoTipico = (PratoTipico) r.getKey();
            int avaliacao = pessoaPratoDAO.getPessoaPrato(usuario.getId(), pratoTipico.getId()).getNota();
            Float nota = (Float)r.getValue();
            if (avaliacao == -1.0f){
                notasCliente.add(new Avaliacao(pratoTipico, nota));
            }
        }
        return notasCliente;
    }

    private Usuario findCliente(){
        Usuario clienteLogado = Sessao.instance.getUsuario();
        for(Map.Entry e: predicao.entrySet()){
            Usuario cliente = (Usuario) e.getKey();
            if(clienteLogado.getId() == cliente.getId()){
                return (Usuario) e.getKey();
            }
        }
        return null;
    }

    private List<PratoTipico> getOrderList(List<Avaliacao> avaliacao){
        Collections.sort(avaliacao);
        List<PratoTipico> restaurantes = new ArrayList<>();
        for(Avaliacao a: avaliacao){
            if(a.nota >= 2.0f){
                restaurantes.add(a.getRestaurante());
            }
        }
        return restaurantes;
    }

    public List<PratoTipico> getListaPratosRecomendados() {
        return this.listaPratosRecomendados;
    }

    private class Avaliacao implements Comparable<Avaliacao>{
        private PratoTipico restaurante;
        private Float nota;

        public Avaliacao(PratoTipico restaurante, Float nota) {
            this.restaurante = restaurante;
            this.nota = nota;
        }

        public PratoTipico getRestaurante() {
            return restaurante;
        }

        public void setRestaurante(PratoTipico restaurante) {
            this.restaurante = restaurante;
        }

        public int compareTo(Avaliacao outra) {
            if(this.nota < outra.nota){
                return 1;
            }
            if(this.nota > outra.nota){
                return -1;
            }
            return 0;
        }
    }
}
