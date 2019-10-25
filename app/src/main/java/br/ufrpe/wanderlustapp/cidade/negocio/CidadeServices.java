package br.ufrpe.wanderlustapp.cidade.negocio;

import android.content.Context;

import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;

public class CidadeServices {
    private CidadeDAO cidadeDAO;

    public CidadeServices(Context context) {
        cidadeDAO = new CidadeDAO(context);
    }

    public void cadastrar(Cidade cidade){
        long idCidade = cidadeDAO.cadastrar(cidade);
        cidade.setId(idCidade);
    }
}
