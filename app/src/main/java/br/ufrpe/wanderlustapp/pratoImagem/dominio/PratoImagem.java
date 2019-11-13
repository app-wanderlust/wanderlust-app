package br.ufrpe.wanderlustapp.pratoImagem.dominio;

import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class PratoImagem {
    private long id;
    private PratoTipico pratoTipico;
    private byte[] imagem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PratoTipico getPratoTipico() {
        return pratoTipico;
    }

    public void setPratoTipico(PratoTipico pratoTipico) {
        this.pratoTipico = pratoTipico;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
