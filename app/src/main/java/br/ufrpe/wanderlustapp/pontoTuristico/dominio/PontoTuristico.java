package br.ufrpe.wanderlustapp.pontoTuristico.dominio;

import java.util.ArrayList;

import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;

public class PontoTuristico {

    private long id;
    private String nome;
    private String descricao;
    private ArrayList<Cidade> cidades;

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

    public ArrayList<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<Cidade> cidades) {
        this.cidades = cidades;
    }
}
