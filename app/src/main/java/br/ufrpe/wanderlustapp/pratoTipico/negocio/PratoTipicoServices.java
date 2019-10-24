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

    public void cadastrar(PratoTipico pratoTipico) throws Exception {
        if (pratoTipicoDAO.getPratoTipicoByNome(pratoTipico.getNome()) != null){
            throw new Exception();
        }

        long idPais = paisDAO.cadastrarPais(pratoTipico.getCidade().getPais());
        pratoTipico.getCidade().getPais().setId(idPais);

        long idCidade = cidadeDAO.cadastrar(pratoTipico.getCidade());
        pratoTipico.getCidade().setId(idCidade);

        long idPratoTipico = pratoTipicoDAO.cadastrar(pratoTipico);
        pratoTipico.setId(idPratoTipico);
    }

    public List<PratoTipico> getLista(){
        List<PratoTipico> lista = pratoTipicoDAO.getListPrato();
        return lista;
    }

}


