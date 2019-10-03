package br.ufrpe.wanderlustapp.contato.dominio;

import java.util.ArrayList;

import br.ufrpe.wanderlustapp.pais.dominio.Pais;

public class Contato {

    private long id;
    private String nome;
    private String numero;
    private ArrayList<Pais> paises;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ArrayList<Pais> getPaises() {
        return paises;
    }

    public void setPaises(ArrayList<Pais> paises) {
        this.paises = paises;
    }
}
