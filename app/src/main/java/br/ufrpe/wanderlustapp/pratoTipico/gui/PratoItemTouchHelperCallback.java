package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

class PratoItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListPratosAdapter adapter;
    private PratoTipicoServices services;


    PratoItemTouchHelperCallback(ListPratosAdapter adapter, PratoTipicoServices services) {
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
        List<PratoTipico> pratos = adapter.getList();
        int posicaoPratoDeslizado = viewHolder.getAdapterPosition();
        PratoTipico pratoDeslizado = pratos.get(posicaoPratoDeslizado);
        services.delete(pratoDeslizado);
        adapter.remove(posicaoPratoDeslizado);
    }
}
