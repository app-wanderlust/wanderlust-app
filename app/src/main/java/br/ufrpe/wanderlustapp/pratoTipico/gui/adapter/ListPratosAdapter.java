package br.ufrpe.wanderlustapp.pratoTipico.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;

public class ListPratosAdapter extends RecyclerView.Adapter<ListPratosAdapter.PratoViewHolder> implements Filterable {

    private final Context context;
    private final List<PratoTipico> pratos;
    private final List<PratoTipico> pratosCopia;
    private OnItemClickListener onItemClickListener;

    public ListPratosAdapter(Context context,List<PratoTipico> pratos) {
        this.context = context;
        this.pratos = pratos;
        pratosCopia = new ArrayList<>(pratos);
    }

    public List<PratoTipico> getList(){
        return this.pratos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ListPratosAdapter.PratoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato, parent, false);
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

    public void adiciona(PratoTipico prato){
        pratos.add(prato);
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return pratosFilter;
    }

    private Filter pratosFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PratoTipico> filteredList = new ArrayList<>();
            ListPratosAdapter.this.filter(constraint, filteredList);
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (constraint.length() > 0){
                pratos.clear();
                pratos.addAll((List) results.values);
                notifyDataSetChanged();
            }else {
                pratos.clear();
                pratos.addAll((List) pratosCopia);
                notifyDataSetChanged();
            }
        }
    };

    private void filter(CharSequence constraint, List<PratoTipico> filteredList) {
        if (constraint == null || constraint.length() == 0) {
            filteredList.add((PratoTipico) pratosCopia);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (PratoTipico item : pratosCopia){
                if(item.getNome().toLowerCase().contains(filterPattern)){
                    filteredList.add(item);
                }
            }
        }
    }

    class PratoViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private PratoTipico prato;


        public PratoViewHolder(@NonNull View itemView) {
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
            titulo.setText(this.prato.getNome());
            descricao.setText(this.prato.getDescricao());
        }
    }
}