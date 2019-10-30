package br.ufrpe.wanderlustapp.pratoTipico.gui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;

public class ListPratosAdapter extends RecyclerView.Adapter<ListPratosAdapter.PratoViewHolder> {

    private final Context context;
    private final List<PratoTipico> pratos;
    private OnItemClickListener onItemClickListener;

    public ListPratosAdapter(Context context,List<PratoTipico> pratos) {

        this.context = context;
        this.pratos = pratos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ListPratosAdapter.PratoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_nota, parent, false);
        return new PratoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PratoViewHolder holder, int position) {
        PratoTipico prato = pratos.get(position);
        holder.vincula(prato);

    }

    @Override
    public int getItemCount() {
        return pratos.size();
    }

    public void altera(int posicao, PratoTipico prato) {
        pratos.set(posicao,prato);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        pratos.remove(posicao);
        notifyDataSetChanged();
    }

    class PratoViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private PratoTipico prato;


        public PratoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
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
    public void adicona(PratoTipico prato){
        pratos.add(prato);
        notifyDataSetChanged();
    }
}