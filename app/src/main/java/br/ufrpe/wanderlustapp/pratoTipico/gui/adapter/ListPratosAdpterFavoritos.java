package br.ufrpe.wanderlustapp.pratoTipico.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;

public class ListPratosAdpterFavoritos extends RecyclerView.Adapter<ListPratosAdapterFavoritos.PratoViewHolder>{
    private final Context context;
    private final List<PratoTipico> pratosFavoritos;
    private OnItemClickListener onItemClickListener;

    public ListPratosAdpterFavoritos(Context context, List<PratoTipico> pratosFavoritos) {
        this.context = context;
        this.pratosFavoritos = pratosFavoritos;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListPratosAdapterFavoritos.PratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato, parent, false);
        return new ListPratosAdapterFavoritos.PratoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPratosAdapterFavoritos.PratoViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
