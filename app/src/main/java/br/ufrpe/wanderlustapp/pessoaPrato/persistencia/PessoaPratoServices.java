package br.ufrpe.wanderlustapp.pessoaPrato.persistencia;

import android.content.Context;

import java.util.List;

import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;

public class PessoaPratoServices {
    private PessoaPratoDAO pessoaPratoDAO;

    public PessoaPratoServices(Context context){
        pessoaPratoDAO = new PessoaPratoDAO(context);
    }

    public void cadastrar(PessoaPrato pessoaPrato){
        long idPessoaPrato = pessoaPratoDAO.cadastrar(pessoaPrato);
        pessoaPrato.setId(idPessoaPrato);
    }

    public List<PessoaPrato> getList(){
        return pessoaPratoDAO.getListPessoaPrato();
    }

    public void update(PessoaPrato pessoaPrato){
        pessoaPratoDAO.updatePessoaPrato(pessoaPrato);
    }

    public void delete(PessoaPrato pessoaPrato){
        pessoaPratoDAO.deletePessoaPrato(pessoaPrato);
    }
}
