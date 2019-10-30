package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;

class PratoItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListPratosAdapter adapter;

    PratoItemTouchHelperCallback(ListPratosAdapter adapter) {
        this.adapter = adapter;
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
        int posicaoPratoDeslizado = viewHolder.getAdapterPosition();
        long idPratoDeslizado = viewHolder.getItemId();
        System.out.println("MEUDEUS"+idPratoDeslizado);
        adapter.remove(posicaoPratoDeslizado);

    }
}
