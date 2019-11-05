package br.ufrpe.wanderlustapp.pratoTipico.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;

public class ListPratosAdapterFavoritos extends RecyclerView.Adapter<ListPratosAdapterFavoritos.PratoFavoritoViewHolder>{

    private final Context context;
    private final List<PratoTipico> pratosFavoritos;
    private OnItemClickListener onItemClickListener;

    public ListPratosAdapterFavoritos(Context context, List<PratoTipico> pratosFavoritos) {
        this.context = context;
        this.pratosFavoritos = pratosFavoritos;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListPratosAdapterFavoritos.PratoFavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato, parent, false);
        return new ListPratosAdapterFavoritos.PratoFavoritoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPratosAdapterFavoritos.PratoFavoritoViewHolder holder, int position) {
        PratoTipico prato = pratosFavoritos.get(position);
        holder.vincula(prato);
    }


    @Override
    public int getItemCount() {
        return pratosFavoritos.size();
    }

    class PratoFavoritoViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private PratoTipico prato;


        public PratoFavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_prato_nome);
            descricao = itemView.findViewById(R.id.item_prato_descricao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(prato, getAdapterPosition());
                }
            });
        }

        public void vincula(PratoTipico prato) {
            this.prato = prato;
            titulo.setText(prato.getNome());
            descricao.setText(prato.getDescricao());
        }
    }
}
