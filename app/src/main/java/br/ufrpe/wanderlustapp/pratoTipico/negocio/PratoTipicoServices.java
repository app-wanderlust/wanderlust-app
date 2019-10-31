package br.ufrpe.wanderlustapp.pratoTipico.negocio;

import android.content.Context;

import java.util.List;

import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class PratoTipicoServices {
    private PratoTipicoDAO pratoTipicoDAO;

    public PratoTipicoServices(Context context) {
        pratoTipicoDAO = new PratoTipicoDAO(context);
    }

    public boolean estaCadastrado(PratoTipico pratoTipico){
        boolean cadastrado = false;
        if (pratoTipicoDAO.getPratoTipicoByNome(pratoTipico.getNome()) != null){
            cadastrado = true;
        }
        return cadastrado;
    }

    public void cadastrar(PratoTipico pratoTipico) throws Exception {
        if (pratoTipicoDAO.getPratoTipicoByNome(pratoTipico.getNome()) != null){
            throw new Exception();
        }
        long idPrato = pratoTipicoDAO.cadastrar(pratoTipico);
        pratoTipico.setId(idPrato);
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


