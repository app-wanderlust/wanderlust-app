package br.ufrpe.wanderlustapp.pontoTuristico.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pontoImagem.negocio.PontoImagemServices;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.gui.OnItemClickListener;
import br.ufrpe.wanderlustapp.pontoTuristico.negocio.PontoTuristicoServices;

public class ListaPontosAvaliacaoAdapter extends RecyclerView.Adapter<ListaPontosAvaliacaoAdapter.PratoViewHolder> {

    private final Context context;
    private final List<PontoTuristico> pontosAvaliacao;
    private OnItemClickListener onItemClickListener;
    private List<Bitmap> listaDeImagens = new ArrayList<>();
    private PontoTuristicoServices pontoImagemServices;

    public ListaPontosAvaliacaoAdapter(Context context, List<PontoTuristico> pratos) {
        this.context = context;
        this.pontosAvaliacao = pratos;
        pontoImagemServices = new PontoImagemServices(this.context);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaPontosAvaliacaoAdapter.PratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_ponto_avaliacao,parent,false);
        return new PratoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull PratoViewHolder holder, int position) {
        PontoTuristico ponto = pontosAvaliacao.get(position);
        holder.vincula(ponto);
    }

    @Override
    public int getItemCount() {
        return pontosAvaliacao.size();
    }

    class PratoViewHolder extends RecyclerView.ViewHolder {
        private final TextView titulo;
        private final TextView descricao;
        private final ImageView imagem;
        private PontoTuristico ponto;
        private Pessoa pessoa = Sessao.instance.getUsuario().getPessoa();

        public PratoViewHolder(@NonNull final View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_prato_nome_avaliacao);
            descricao = itemView.findViewById(R.id.item_prato_descricao_avaliacao);
            imagem = itemView.findViewById(R.id.imagem_prato_avaliacao);

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