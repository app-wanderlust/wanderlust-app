package br.ufrpe.wanderlustapp.pessoaPrato.negocio;

import android.content.Context;

import java.util.List;

import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.persistencia.PessoaPratoDAO;

public class PessoaPratoServices {
    private PessoaPratoDAO pessoaPratoDAO;

    public PessoaPratoServices(Context context){
        pessoaPratoDAO = new PessoaPratoDAO(context);
    }

    public void cadastrar(PessoaPrato pessoaPrato) throws Exception{
        if(pessoaPratoDAO.getPessoaPrato(pessoaPrato.getPessoa().getId(), pessoaPrato.getPratoTipico().getId()) != null){
            throw new Exception();
        }else{
            long idPessoaPrato = pessoaPratoDAO.cadastrar(pessoaPrato);
            pessoaPrato.setId(idPessoaPrato);
        }
    }

    public List<PessoaPrato> getList(){
        return pessoaPratoDAO.getListPessoaPrato();
    }

    public List<PessoaPrato> getListByIdPessoa(long idPessoa){
        return pessoaPratoDAO.getPratoByIdPessoa(idPessoa);
    }

    public void update(PessoaPrato pessoaPrato){
        pessoaPratoDAO.updatePessoaPrato(pessoaPrato);
    }

    public void delete(PessoaPrato pessoaPrato){
        pessoaPratoDAO.deletePessoaPrato(pessoaPrato);
    }
}
