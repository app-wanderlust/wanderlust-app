package br.ufrpe.wanderlustapp.pontoImagem.dominio;

import java.io.Serializable;

import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;


public class PontoImagem implements Serializable {

    private long id;
    private PontoTuristico pontoTuristico;
    private byte[] imagem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PontoTuristico getPontoTuristico() {
        return pontoTuristico;
    }

    public void setPontoTuristico(PontoTuristico pontoTuristico) { this.pontoTuristico = pontoTuristico;}

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }





}
