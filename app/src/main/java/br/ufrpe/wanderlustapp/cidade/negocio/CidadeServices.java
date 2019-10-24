package br.ufrpe.wanderlustapp.cidade.negocio;

import android.content.Context;

import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.persistencia.PaisDAO;

public class CidadeServices {
    private Context context;
    private CidadeDAO cidadeDAO;
    private PaisDAO paisDAO;

    public CidadeServices(Context context){
        paisDAO = new PaisDAO(context);
        cidadeDAO = new CidadeDAO(context);

    }

    public void cadastrar(Cidade cidade){
        long idCidade = cidadeDAO.cadastrar(cidade);
        cidade.setId(idCidade);
    }

}
