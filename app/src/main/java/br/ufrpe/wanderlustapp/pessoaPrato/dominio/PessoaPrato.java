package br.ufrpe.wanderlustapp.pessoaPrato.dominio;

import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class PessoaPrato {
    private long id;
    private Pessoa pessoa;
    private PratoTipico pratoTipico;
    private int nota;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PratoTipico getPratoTipico() {
        return pratoTipico;
    }

    public void setPratoTipico(PratoTipico pratoTipico) {
        this.pratoTipico = pratoTipico;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
