package br.ufrpe.wanderlustapp.pessoa.dominio;

import java.util.ArrayList;
import java.util.Date;

import br.ufrpe.wanderlustapp.conversa.dominio.Conversa;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class Pessoa {
    private long id;
    private String nome;
    private Date nascimento;
    private Usuario usuario;
    private ArrayList<Pais> paises;
    private ArrayList<Conversa> conversas;

    public ArrayList<Pais> getPaises() {
        return paises;
    }

    public void setPaises(ArrayList<Pais> paises) {
        this.paises = paises;
    }

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

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Conversa> getConversas() {
        return conversas;
    }

    public void setConversas(ArrayList<Conversa> conversas) {
        this.conversas = conversas;
    }
}
