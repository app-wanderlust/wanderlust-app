package br.ufrpe.wanderlustapp.usuario.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosFavoritosAdapter;

public class ListaPratosSugestaoAdapter extends RecyclerView.Adapter<ListaPratosSugestaoAdapter.PratoViewHolder> {

    private final Context context;
    private final List<PessoaPrato> pessoaPratos;

    public ListaPratosSugestaoAdapter(Context context, List<PessoaPrato> pessoaPratos) {
        this.context = context;
        this.pessoaPratos = pessoaPratos;
    }

    public List<PessoaPrato> getList(){
        return this.pessoaPratos;
    }

    @NonNull
    @Override
    public ListaPratosSugestaoAdapter.PratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato_sugestao,parent,false);
        return new ListaPratosSugestaoAdapter.PratoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPratosSugestaoAdapter.PratoViewHolder holder, int position) {
        PratoTipico prato = pessoaPratos.get(position).getPratoTipico();
        holder.vincula(prato);
    }

    @Override
    public int getItemCount() {
        return pessoaPratos.size();
    }

    class PratoViewHolder extends RecyclerView.ViewHolder{
        private final TextView titulo;
        private PratoTipico prato;


        public PratoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_prato_nome_sugestao);
        }

        public void vincula(PratoTipico prato){
            this.prato = prato;
            titulo.setText(this.prato.getNome());
        }
    }

    public void remove(int posicao){
        pessoaPratos.remove(posicao);
        notifyDataSetChanged();
    }


}
