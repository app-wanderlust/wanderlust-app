package br.ufrpe.wanderlustapp.pais.dominio;

import java.util.ArrayList;

import br.ufrpe.wanderlustapp.estado.dominio.Estado;

public class Pais {

    private long id;
    private String nome;
    private ArrayList<Estado> estados;

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

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }
}
