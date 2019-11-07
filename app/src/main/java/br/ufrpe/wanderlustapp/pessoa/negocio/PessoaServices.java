package br.ufrpe.wanderlustapp.pessoa.negocio;

import android.content.Context;

import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pessoa.persistencia.PessoaDAO;


public class PessoaServices {
    private Context context;
    private PessoaDAO pessoaDAO;

    public PessoaServices(Context context){
        this.context = context;
        pessoaDAO = new PessoaDAO(this.context);
    }

    public void cadastrar(Pessoa pessoa) {
        long idPessoa = pessoaDAO.cadastrar(pessoa);
        pessoa.setId(idPessoa);
    }

    public Pessoa getPessoa(long idPessoa) {
        Pessoa pessoa;
        pessoa = pessoaDAO.getPessoa(idPessoa);
        return pessoa;
    }

}
