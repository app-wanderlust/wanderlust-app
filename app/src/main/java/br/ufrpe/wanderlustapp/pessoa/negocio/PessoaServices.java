package br.ufrpe.wanderlustapp.pessoa.negocio;

import android.database.Cursor;

import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pessoa.persistencia.PessoaDAO;


public class PessoaServices {
    private PessoaDAO pessoaDAO = new PessoaDAO();

    public long cadastrar(Pessoa pessoa) {
        long idPessoa = pessoaDAO.cadastrar(pessoa);
        return idPessoa;
    }

    public Pessoa getPessoa(long idPessoa) {
        Pessoa pessoa;
        pessoa = pessoaDAO.getPessoa(idPessoa);
        return pessoa;
    }

}
