package br.ufrpe.wanderlustapp.pratoTipico.negocio;

import android.content.Context;

import java.util.List;

import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;
import br.ufrpe.wanderlustapp.pais.persistencia.PaisDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class PratoTipicoServices {
    private PratoTipicoDAO pratoTipicoDAO;
    private CidadeDAO cidadeDAO;
    private PaisDAO paisDAO;

    public PratoTipicoServices(Context context) {
        pratoTipicoDAO = new PratoTipicoDAO(context);
        cidadeDAO = new CidadeDAO(context);
        paisDAO = new PaisDAO(context);
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
        long idPais = paisDAO.cadastrarPais(pratoTipico.getCidade().getPais());
        pratoTipico.getCidade().getPais().setId(idPais);
    }

    public List<PratoTipico> getLista(){
        return pratoTipicoDAO.getListPrato();
    }

}


