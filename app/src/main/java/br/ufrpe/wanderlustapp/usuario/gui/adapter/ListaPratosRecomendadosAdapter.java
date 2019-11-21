package br.ufrpe.wanderlustapp.usuario.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.negocio.PessoaPratoServices;
import br.ufrpe.wanderlustapp.pratoImagem.negocio.PratoImagemServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;

public class ListaPratosRecomendadosAdapter extends RecyclerView.Adapter<ListaPratosRecomendadosAdapter.PratoViewHolder> {
    private final Context context;
    private final List<PratoTipico> pratosAvaliacao;
    private OnItemClickListener onItemClickListener;
    private List<Bitmap> listaDeImagens = new ArrayList<>();
    private PratoImagemServices pratoImagemServices;

    public ListaPratosRecomendadosAdapter(Context context, List<PratoTipico> pratos) {
        this.context = context;
        this.pratosAvaliacao = pratos;
        pratoImagemServices = new PratoImagemServices(this.context);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaPratosRecomendadosAdapter.PratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_prato_slopeone,parent,false);
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
        private final ImageView imagem;
        private PratoTipico prato;
        private Pessoa pessoa = Sessao.instance.getUsuario().getPessoa();
        private PessoaPrato pessoaPrato;
        private ToggleButton likeButton;
        private ToggleButton dislikeButton;
        PessoaPratoServices pessoaPratoServices = new PessoaPratoServices(context);


        public PratoViewHolder(@NonNull final View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_prato_nome_slopeone);
            descricao = itemView.findViewById(R.id.item_prato_descricao_slopeone);
            imagem = itemView.findViewById(R.id.imagem_prato_avaliacao);
            likeButton = itemView.findViewById(R.id.button_favorite);
            dislikeButton = itemView.findViewById(R.id.button_dislike_toggle);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (likeButton.isChecked()) {
                        dislikeButton.setChecked(false);
                        onItemClickListener.onItemClick(prato, getAdapterPosition(), likeButton.isChecked(), dislikeButton.isChecked());
                    }else if(!likeButton.isChecked()){
                        onItemClickListener.onItemClick(prato, getAdapterPosition(), likeButton.isChecked(), dislikeButton.isChecked());
                    }
                }
            });
            dislikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dislikeButton.isChecked()) {
                        likeButton.setChecked(false);
                        onItemClickListener.onItemClick(prato, getAdapterPosition(), likeButton.isChecked(), dislikeButton.isChecked());
                    }else if (!dislikeButton.isChecked()){
                        onItemClickListener.onItemClick(prato, getAdapterPosition(), likeButton.isChecked(), dislikeButton.isChecked());
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(prato,getAdapterPosition());
                }
            });
        }

        public void vincula(PratoTipico prato){
            this.prato = prato;
            titulo.setText(this.prato.getNome());
            descricao.setText(this.prato.getDescricao());
            listaDeImagens = pratoImagemServices.geraImagens(prato);
            if (listaDeImagens.size()>0){
                Bitmap imagens = listaDeImagens.get(0);
                if (imagens != null){
                    imagem.setImageBitmap(imagens);
                }
            }
            this.pessoaPrato = pessoaPratoServices.getPessoaPrato(pessoa.getId(), this.prato.getId());
            if (this.pessoaPrato != null && this.pessoaPrato.getNota() == 1){
                likeButton.setChecked(true);
            }else if (this.pessoaPrato != null && this.pessoaPrato.getNota() == -1){
                dislikeButton.setChecked(true);
            }else{
                dislikeButton.setChecked(false);
                likeButton.setChecked(false);
            }
        }
    }
}
