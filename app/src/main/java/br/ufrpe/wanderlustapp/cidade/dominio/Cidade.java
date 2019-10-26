package br.ufrpe.wanderlustapp.cidade.dominio;

import br.ufrpe.wanderlustapp.pais.dominio.Pais;

public class Cidade {

    private long id;
    private String nome;
    private Pais pais;


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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
