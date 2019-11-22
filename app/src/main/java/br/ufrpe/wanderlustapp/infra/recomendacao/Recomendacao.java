package br.ufrpe.wanderlustapp.infra.recomendacao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private Map<Usuario, HashMap<PratoTipico, Float>> usersMatrix;
    private List<PratoTipico> listaPratos;
    private List<PratoTipico> listaPratosRecomendados;
    private Map<Usuario, HashMap<PratoTipico, Float>> predicao;


    public ArrayList<PratoTipico> getRecomendacao(Context context) {
        Usuario usarioLogado = Sessao.instance.getUsuario();
        Map<Usuario, Map<String, Float>> dados = getAvaliacoesUsuario(context);
        HashMap<String, Float> avaliacoesUsuario = avaliacaoPorUsuario(usarioLogado, context);
        SlopeOne slopeOne = new SlopeOne(dados);
        Map<String, Float> predicoes = slopeOne.predict(avaliacoesUsuario);
        return getProfissionaisRecomendadas(predicoes, context);
    }

    private ArrayList<PratoTipico> getProfissionaisRecomendadas(Map<String, Float> predicoes, Context context) {

        ArrayList<PratoTipico> recomendados = new ArrayList<>();
        for (Map.Entry<String, Float> entry : predicoes.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();
            PratoTipico pratoTipicoAtual = profissionalByID(key, context);
            pratoTipicoAtual.setAvaliacao(value);
            Float nota = avaliacaoProfissionalUsuario(pratoTipicoAtual, context);
            if (nota == null && (pratoTipicoAtual.getAvaliacao() >= 1.0)) {
                recomendados.add(pratoTipicoAtual);
            }
        }
        Collections.sort(recomendados, new Comparator<PratoTipico>() {
            @Override
            public int compare(PratoTipico p1, PratoTipico p2) {
                return p2.getAvaliacao().intValue() - p1.getAvaliacao().intValue();
            }

        });
        return recomendados;
    }

    public Float avaliacaoProfissionalUsuario(PratoTipico pratoTipico, Context context) {
        Usuario usuario = Sessao.instance.getUsuario();
        PessoaPratoDAO pessoaPratoDAO = new PessoaPratoDAO(context);
        float notaPRato = (float)pessoaPratoDAO.getPessoaPrato(usuario.getPessoa().getId(), usuario.getId()).getNota();
        return notaPRato;
    }

    private Map<Usuario, Map<String, Float>> getAvaliacoesUsuario(Context context) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(context);
        Usuario usuarioLogado = Sessao.instance.getUsuario();
        Map<Usuario, Map<String, Float>> dados = new HashMap<>();
        ArrayList<Usuario> usuarios = usuarioDAO.
        for (Usuario usuario : usuarios) {
            if (usuario.getId() != usuarioLogado.getId()) {
                dados.put(usuario, avaliacaoPorUsuario(usuario, context));
            }
        }
        return dados;
    }

    private HashMap<String,Float> avaliacaoPorUsuario(Usuario usuario, Context context){
        AvaliacaoDAO dao = new AvaliacaoDAO(context);
        return  dao.getAvaliacaoProfissional(usuario);
    }

    private PratoTipico profissionalByID(String nomeDoPrato, Context context) {
        PratoTipicoDAO pratoTipicoDAO = new PratoTipicoDAO(context);
        return pratoTipicoDAO.getPratoTipicoByNome(nomeDoPrato);
    }

}