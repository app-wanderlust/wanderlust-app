package br.ufrpe.wanderlustapp.pontoTuristico.dominio;

import java.io.Serializable;
import java.util.ArrayList;

import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;

public class PontoTuristico implements Serializable {

    private long id;
    private String nome;
    private String descricao;
    private Cidade cidade;

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
}
