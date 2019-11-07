package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.negocio.PessoaPratoServices;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosFavoritosAdapter;

public class PratoFavoritoItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private ListaPratosFavoritosAdapter adapter;
    private PessoaPratoServices services;


    PratoFavoritoItemTouchHelperCallback(ListaPratosFavoritosAdapter adapter, PessoaPratoServices services) {
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
        List<PessoaPrato> pratos = adapter.getList();
        int posicaoPratoDeslizado = viewHolder.getAdapterPosition();
        PessoaPrato pratoDeslizado = pratos.get(posicaoPratoDeslizado);
        services.delete(pratoDeslizado);
        adapter.remove(posicaoPratoDeslizado);
    }
}
