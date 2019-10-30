package br.ufrpe.wanderlustapp.pratoTipico.dominio;

import java.io.Serializable;

import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;

public class PratoTipico implements Serializable {

    private long id;
    private String nome;
    private String descricao;
    private Cidade cidade;
    private StatusAtividade statusAtividade = StatusAtividade.ATIVO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Cidade getCidade() {
        return cidade;
    }


    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public StatusAtividade getStatusAtividade() {
        return statusAtividade;
    }

    public void setStatusAtividade(StatusAtividade statusAtividade) {
        this.statusAtividade = statusAtividade;
    }
}
