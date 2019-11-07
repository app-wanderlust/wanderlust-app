package br.ufrpe.wanderlustapp.pessoa.dominio;

import java.util.ArrayList;

import br.ufrpe.wanderlustapp.conversa.dominio.Conversa;

public class Pessoa {
    private long id;
    private String nome;
    private String nascimento;
    private ArrayList<Conversa> conversas;

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

    public String getNascimento() {
        return this.nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public ArrayList<Conversa> getConversas() {
        return conversas;
    }

    public void setConversas(ArrayList<Conversa> conversas) {
        this.conversas = conversas;
    }
}
