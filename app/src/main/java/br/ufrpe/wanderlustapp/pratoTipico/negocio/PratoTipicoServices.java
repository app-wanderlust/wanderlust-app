package br.ufrpe.wanderlustapp.pratoTipico.negocio;

import android.content.Context;

import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;


public class PratoTipicoServices {
    private PratoTipicoDAO pratoTipicoDAO;
    private CidadeDAO cidadeDAO;


    public PratoTipicoServices(Context context) {
        pratoTipicoDAO = new PratoTipicoDAO(context);
        cidadeDAO = new CidadeDAO(context);


    }

    public void cadastrar(PratoTipico pratoTipico) throws Exception {
        if (pratoTipicoDAO.getPratoTipicoByNome(pratoTipico.getNome()) != null){
            throw new Exception();
        }
        long idCidade = cidadeDAO.cadastrar(pratoTipico.getCidade());
        pratoTipico.getCidade().setId(idCidade);

        long idPratoTipico = pratoTipicoDAO.cadastrar(pratoTipico);
        pratoTipico.setId(idPratoTipico);
    }
}


