package br.ufrpe.wanderlustapp.pratoTipico.negocio;

import android.content.Context;

import java.util.List;

import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.persistencia.PessoaPratoDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class PratoTipicoServices {
    private PratoTipicoDAO pratoTipicoDAO;
    private PessoaPratoDAO pessoaPratoDAO;
    PessoaPrato pessoaPrato = new PessoaPrato();

    public PratoTipicoServices(Context context) {
        pratoTipicoDAO = new PratoTipicoDAO(context);
        pessoaPratoDAO = new PessoaPratoDAO(context);

    }


    public void cadastrar(PratoTipico pratoTipico) throws Exception {
        if (pratoTipicoDAO.getPratoTipicoByNome(pratoTipico.getNome()) != null){
            throw new Exception();
        }
        long idPrato = pratoTipicoDAO.cadastrar(pratoTipico);
        pratoTipico.setId(idPrato);
        //pessoaPrato.setNota(0);
        pessoaPrato.setPratoTipico(pratoTipico);
        pessoaPrato.setPessoa(Sessao.instance.getUsuario().getPessoa());
        pessoaPratoDAO.cadastrar(pessoaPrato);

    }

    public List<PratoTipico> getLista(){
        return pratoTipicoDAO.getListPrato();
    }

    public void update(PratoTipico prato){
        pratoTipicoDAO.updatePrato(prato);
    }

    public void delete(PratoTipico prato) {
        pratoTipicoDAO.deletePrato(prato);
    }
}


