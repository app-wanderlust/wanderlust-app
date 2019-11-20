package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;

public interface OnItemClickListener {

    void onItemClick(PontoTuristico pontoTuristico, int posicao);
    void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean isChecked);
    void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean likeChecked, boolean dislikeChecked);
}