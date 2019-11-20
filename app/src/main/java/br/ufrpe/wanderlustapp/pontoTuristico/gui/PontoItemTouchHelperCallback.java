package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.negocio.PontoTuristicoServices;

public class PontoItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListPontosAdapter adapter;
    private PontoTuristicoServices services;


    PontoItemTouchHelperCallback(ListPontosAdapter adapter, PontoTuristicoServices services) {
        this.adapter = adapter;
        this.services = services;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT;
        return makeMovementFlags(0,marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        List<PontoTuristico> pontos = adapter.getList();
        int posicaoPontoDeslizado = viewHolder.getAdapterPosition();
        PontoTuristico pontoDeslizado = pontos.get(posicaoPontoDeslizado);
        services.delete(pontoDeslizado);
        adapter.remove(posicaoPontoDeslizado);
    }

}
