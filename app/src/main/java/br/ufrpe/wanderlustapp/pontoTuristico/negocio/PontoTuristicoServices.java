package br.ufrpe.wanderlustapp.pontoTuristico.negocio;

import android.content.Context;

import java.util.List;

import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.persistencia.PontoTuristicoDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class PontoTuristicoServices {
    private PontoTuristicoDAO pontoTuristicoDAO;

    public PontoTuristicoServices(Context context) {
        pontoTuristicoDAO = new PontoTuristicoDAO(context);
    }


    public void cadastrar(PontoTuristico pontoTuristico) throws Exception {
        if (pontoTuristicoDAO.getPontoTuristicoByNome(pontoTuristico.getNome()) != null){
            throw new Exception();
        }
        long idPonto = pontoTuristicoDAO.cadastrar(pontoTuristico);
        pontoTuristico.setId(idPonto);
    }

    public List<PontoTuristico> getLista(){
        return pontoTuristicoDAO.getListPontoTuristico();
    }

    public void update(PontoTuristico pontoTuristico){
        pontoTuristicoDAO.updatePontoTuristico(pontoTuristico);
    }

    public void delete(PontoTuristico pontoTuristico) {
        pontoTuristicoDAO.deletePontoTuristico(pontoTuristico);
    }
}
