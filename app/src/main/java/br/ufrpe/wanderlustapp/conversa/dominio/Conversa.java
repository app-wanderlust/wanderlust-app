package br.ufrpe.wanderlustapp.conversa.dominio;

import java.util.Date;

import br.ufrpe.wanderlustapp.pais.dominio.Pais;

public class Conversa {
    private long id;
    private String texto;
    private Date data;
    private Pais pais;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
