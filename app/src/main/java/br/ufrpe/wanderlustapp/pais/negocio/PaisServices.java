package br.ufrpe.wanderlustapp.pais.negocio;

import android.content.Context;

import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.persistencia.PaisDAO;

public class PaisServices {
    private PaisDAO paisDAO;

    public PaisServices(Context context){
        paisDAO = new PaisDAO(context);
    }

    public void cadastrar(Pais pais){
        long idPais = paisDAO.cadastrarPais(pais);
        pais.setId(idPais);
    }
}
