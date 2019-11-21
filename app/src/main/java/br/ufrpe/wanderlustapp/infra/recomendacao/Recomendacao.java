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
    private Map<Usuario,HashMap<PratoTipico,Integer>> usersMatrix;
    private List<PratoTipico> listaPratos;
    private List<PratoTipico> listaPratosRecomendados;
    private Map<Usuario, HashMap<PratoTipico, Float>> predicao;

    public Recomendacao(){
        listaPratos = pratoTipicoDAO.getListPrato();
        usersMatrix = criaMatrizUsuario();
        predicao = SlopeOne.slopeOne(usersMatrix, listaPratos);
        listaPratosRecomendados = getOrderList(getRecomendacao());
    }

    public Map<Usuario,HashMap<PratoTipico,Integer>> criaMatrizUsuario(){
        Map<Usuario,HashMap<PratoTipico,Integer>> matrizUsuario = new HashMap<>();
        List<Usuario> listaClientes = usuarioDAO.getList();
        for(Usuario usuario: listaClientes){
            matrizUsuario.put(usuario, criaMatrizPratoTipico(usuario.getId()));
        }
        return matrizUsuario;
    }

    private HashMap<PratoTipico, Integer> criaMatrizPratoTipico(long idPessoa){
        HashMap<PratoTipico, Integer> matrizPratos = new HashMap<>();
        for(PratoTipico pratoTipico: listaPratos){
            int matrizPessoaPrato = pessoaPratoDAO.getPessoaPrato(idPessoa, pratoTipico.getId()).getNota();
            if(matrizPessoaPrato != -1){
                matrizPratos.put(pratoTipico, matrizPessoaPrato);
            }
        }
        return matrizPratos;
    }

    private List<Avaliacao> getRecomendacao(){
        List<Avaliacao> notasUsuario = new ArrayList<>();
        Usuario usuario = findUsuario();
        HashMap<PratoTipico, Float> avaliacoes = predicao.get(usuario);
        for(Map.Entry r: avaliacoes.entrySet()){
            PratoTipico pratoTipico = (PratoTipico) r.getKey();
            int avaliacao = pessoaPratoDAO.getPessoaPrato(usuario.getId(), pratoTipico.getId()).getNota();
            int nota = (int)r.getValue();
            if (avaliacao == -1.0f){
                notasUsuario.add(new Avaliacao(pratoTipico, nota));
            }
        }
        return notasUsuario;
    }

    private Usuario findUsuario(){
        Usuario instanceUsuario = Sessao.instance.getUsuario();
        for(Map.Entry e: predicao.entrySet()){
            Usuario cliente = (Usuario) e.getKey();
            if(instanceUsuario.getId() == cliente.getId()){
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
                restaurantes.add(a.getPratoTipico());
            }
        }
        return restaurantes;
    }

    public List<PratoTipico> getListaPratosRecomendados() {
        return this.listaPratosRecomendados;
    }

    private class Avaliacao implements Comparable<Avaliacao>{
        private PratoTipico pratoTipico;
        private int nota;

        public Avaliacao(PratoTipico pratoTipico, int nota) {
            this.pratoTipico = pratoTipico;
            this.nota = nota;
        }

        public PratoTipico getPratoTipico() {
            return pratoTipico;
        }

        public void setPratoTipico(PratoTipico pratoTipico) {
            this.pratoTipico = pratoTipico;
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
