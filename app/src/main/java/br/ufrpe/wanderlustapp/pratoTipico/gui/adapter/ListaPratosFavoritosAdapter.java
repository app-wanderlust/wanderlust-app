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
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosFavoritos;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;

public class ListaPratosFavoritosAdapter extends RecyclerView.Adapter<ListaPratosFavoritosAdapter.PratoViewHolder> {
    private final Context context;
    //private final List<PratoTipico> pratosFavoritos;
    private final List<PessoaPrato> pessoaPratos;
    private OnItemClickListener onItemClickListener;

    public ListaPratosFavoritosAdapter(Context context, List<PessoaPrato> pessoaPratos) {
        this.context = context;
        this.pessoaPratos = pessoaPratos;
    }

    public List<PessoaPrato> getList(){
        return this.pessoaPratos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaPratosFavoritosAdapter.PratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato_favoritos,parent,false);
        return new PratoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PratoViewHolder holder, int position) {
        PratoTipico prato = pessoaPratos.get(position).getPratoTipico();
        holder.vincula(prato);
    }

    @Override
    public int getItemCount() {
        return pessoaPratos.size();
    }

    class PratoViewHolder extends RecyclerView.ViewHolder{
        private final TextView titulo;
        private final TextView descricao;
        private PratoTipico prato;


        public PratoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_prato_nome_favoritos);
            descricao = itemView.findViewById(R.id.item_prato_descricao_favoritos);
        }

        public void vincula(PratoTipico prato){
            this.prato = prato;
            titulo.setText(prato.getNome());
            descricao.setText(prato.getDescricao());
        }
    }

    public void remove(int posicao){
        pessoaPratos.remove(posicao);
        notifyDataSetChanged();
    }

}
