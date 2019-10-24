package br.ufrpe.wanderlustapp.pais.negocio;

import android.content.Context;

import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.persistencia.PaisDAO;

public class PaisServices {
    private Context context;
    private PaisDAO paisDAO = new PaisDAO(context);

    public PaisServices(Context context){
        paisDAO = new PaisDAO(context);
    }

    public long cadastrar(Pais pais){
        long idPais = paisDAO.cadastrarPais(pais);
        return idPais;
    }
}
