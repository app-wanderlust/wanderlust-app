package br.ufrpe.wanderlustapp.pratoImagem.dominio;

import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class PratoImagem {
    private long id;
    private PratoTipico pratoTipico;
    private String imagem;

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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
