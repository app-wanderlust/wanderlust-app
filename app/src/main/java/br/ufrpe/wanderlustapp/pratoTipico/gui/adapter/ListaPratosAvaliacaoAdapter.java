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

public class ListaPratosAvaliacaoAdapter extends RecyclerView.Adapter<ListaPratosAvaliacaoAdapter.PratoViewHolder> {
    private final Context context;
    private final List<PratoTipico> pratosAvaliacao;
    private OnItemClickListener onItemClickListener;

    public ListaPratosAvaliacaoAdapter(Context context, List<PratoTipico> pratos) {
        this.context = context;
        this.pratosAvaliacao = pratos;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaPratosAvaliacaoAdapter.PratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato_avaliacao,parent,false);
        return new PratoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PratoViewHolder holder, int position) {
        PratoTipico prato = pratosAvaliacao.get(position);
        holder.vincula(prato);

    }

    @Override
    public int getItemCount() {
        return pratosAvaliacao.size();
    }

    class PratoViewHolder extends RecyclerView.ViewHolder{
        private final TextView titulo;
        private final TextView descricao;
        private PratoTipico prato;


        public PratoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_prato_nome_avaliacao);
            descricao = itemView.findViewById(R.id.item_prato_descricao_avaliacao);

        }

        public void vincula(PratoTipico prato){
            this.prato = prato;
            titulo.setText(prato.getNome());
            descricao.setText(prato.getDescricao());
        }
    }



}
