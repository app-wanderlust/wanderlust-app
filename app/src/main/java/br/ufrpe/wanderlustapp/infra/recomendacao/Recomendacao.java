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
    private UsuarioDAO usuarioDAO;
    private PratoTipicoDAO pratoTipicoDAO;
    private PessoaPratoDAO pessoaPratoDAO;
    private Map<Usuario,HashMap<PratoTipico,Float>> usersMatrix;
    private List<PratoTipico> listaPratos;
    private List<PratoTipico> listaPratosRecomendados;
    private Map<Usuario, HashMap<PratoTipico, Float>> predicao;

    public Recomendacao(Context context){
        this.context = context;
        pratoTipicoDAO = new PratoTipicoDAO(context);
        pessoaPratoDAO = new PessoaPratoDAO(context);
        usuarioDAO = new UsuarioDAO(context);
        listaPratos = pratoTipicoDAO.getListPrato();
        usersMatrix = criaMatrizUsuario();
        predicao = SlopeOne.slopeOne(usersMatrix, listaPratos);
        listaPratosRecomendados = getOrderList(getRecomendacao());
    }

    public Map<Usuario,HashMap<PratoTipico,Float>> criaMatrizUsuario(){
        Map<Usuario,HashMap<PratoTipico,Float>> matrizUsuario = new HashMap<>();
        List<Usuario> listaUsuario = usuarioDAO.getList();
        for(Usuario usuario: listaUsuario){
            matrizUsuario.put(usuario, criaMatrizPratoTipico(usuario.getPessoa().getId()));
        }
        return matrizUsuario;
    }

    private HashMap<PratoTipico, Float> criaMatrizPratoTipico(long idPessoa){
        HashMap<PratoTipico, Float> matrizPratos = new HashMap<>();
        for(PratoTipico pratoTipico: listaPratos){
            if(pessoaPratoDAO.getPessoaPrato(idPessoa, pratoTipico.getId()) != null){
                float matrizPessoaPrato = pessoaPratoDAO.getPessoaPrato(idPessoa, pratoTipico.getId()).getNota();
                if(matrizPessoaPrato != -1.0f){
                    matrizPratos.put(pratoTipico, matrizPessoaPrato);
                }
            }else{
                float matrizPessoaPrato = 0;
                matrizPratos.put(pratoTipico, matrizPessoaPrato);
            }

        }
        return matrizPratos;
    }

    private List<Avaliacao> getRecomendacao(){
        List<Avaliacao> notasUsuario = new ArrayList<>();
        Usuario usuario = findUsuario();
        HashMap<PratoTipico, Float> avaliacoes = predicao.get(usuario);
        for(Map.Entry prato: avaliacoes.entrySet()){
            PratoTipico pratoTipico = (PratoTipico) prato.getKey();
            if (pessoaPratoDAO.getPessoaPrato(usuario.getPessoa().getId(), pratoTipico.getId()) != null){
                float avaliacao = pessoaPratoDAO.getPessoaPrato(usuario.getId(), pratoTipico.getId()).getNota();
                float nota = (float)prato.getValue();
                if (avaliacao != -1.0f){
                    notasUsuario.add(new Avaliacao(pratoTipico, nota));
                }
            }

        }
        return notasUsuario;
    }

    private Usuario findUsuario(){
        Usuario instanceUsuario = Sessao.instance.getUsuario();
        for(Map.Entry e: predicao.entrySet()){
            Usuario usuario = (Usuario) e.getKey();
            if(instanceUsuario.getId() == usuario.getId()){
                return (Usuario) e.getKey();
            }
        }
        return null;
    }

    private List<PratoTipico> getOrderList(List<Avaliacao> avaliacao){
        Collections.sort(avaliacao);
        List<PratoTipico> pratos = new ArrayList<>();
        for(Avaliacao a: avaliacao){
            if(a.nota >= 1.0f){
                pratos.add(a.getPratoTipico());
            }
        }
        return pratos;
    }

    public List<PratoTipico> getListaPratosRecomendados() {
        return this.listaPratosRecomendados;
    }

    private class Avaliacao implements Comparable<Avaliacao>{
        private PratoTipico pratoTipico;
        private float nota;

        public Avaliacao(PratoTipico pratoTipico, float nota) {
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
