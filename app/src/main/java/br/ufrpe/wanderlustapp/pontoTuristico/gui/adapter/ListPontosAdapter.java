package br.ufrpe.wanderlustapp.pontoTuristico.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pontoImagem.negocio.PontoImagemServices;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.gui.ListaPontosActivity;
import br.ufrpe.wanderlustapp.pontoTuristico.gui.OnItemClickListener;

public class ListPontosAdapter extends RecyclerView.Adapter<ListPontosAdapter.PontoViewHolder>{

    private final Context context;
    private final List<PontoTuristico> pontos;
    private OnItemClickListener onItemClickListener;
    private ListaPontosActivity listaPontos = new ListaPontosActivity();
    private List<Bitmap> listaDeImagens = new ArrayList<>();
    private PontoImagemServices pontoImagemServices;

    public ListPontosAdapter(Context context,List<PontoTuristico> pontos) {
        this.context = context;
        this.pontos = pontos;
        pontoImagemServices = new PontoImagemServices(this.context);
    }

    public List<PontoTuristico> getList(){
        return this.pontos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ListPontosAdapter.PontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_ponto, parent, false);
        return new PontoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PontoViewHolder holder, int position) {
        PontoTuristico ponto = pontos.get(position);
        holder.vincula(ponto);
    }

    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public void altera(int posicao, PontoTuristico ponto) {
        pontos.set(posicao,ponto);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        pontos.remove(posicao);
        notifyDataSetChanged();
    }

    public void adiciona(PontoTuristico ponto){
        pontos.add(ponto);
        notifyDataSetChanged();
    }

    class PontoViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private final ImageView imagem;
        private PontoTuristico ponto;

        public PontoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_ponto_nome);
            descricao = itemView.findViewById(R.id.item_ponto_descricao);
            imagem = itemView.findViewById(R.id.imagem_ponto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(ponto, getAdapterPosition());
                }
            });
        }

        public void vincula(PontoTuristico ponto) {
            this.ponto = ponto;
            titulo.setText(this.ponto.getNome());
            descricao.setText(this.ponto.getDescricao());
            listaDeImagens = pontoImagemServices.geraImagens(ponto);
            if (listaDeImagens.size() > 0) {
                Bitmap imagens = listaDeImagens.get(0);
                if (imagens != null) {
                    imagem.setImageBitmap(imagens);
                }
            }
        }
    }
}
