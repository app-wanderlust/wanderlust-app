package br.ufrpe.wanderlustapp.pratoTipico.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoImagem.negocio.PratoImagemServices;

public class ListaImagensAdapter extends RecyclerView.Adapter<ListaImagensAdapter.ImagemViewHolder>{
    private final Context context;
    private final List<Bitmap> imagensPrato;
    private PratoImagemServices pratoImagemServices;

    public ListaImagensAdapter(Context context, List<Bitmap> pratos){
        this.context = context;
        this.imagensPrato = pratos;
        pratoImagemServices = new PratoImagemServices(this.context);
    }

    @NonNull
    @Override
    public ListaImagensAdapter.ImagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_lista_imagens_pratos,parent,false);
        return new ImagemViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaImagensAdapter.ImagemViewHolder holder, int position) {
        Bitmap imagem = imagensPrato.get(position);
        holder.vinculaImagem(imagem);

    }

    @Override
    public int getItemCount() {
        return imagensPrato.size();
    }

    class ImagemViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imagemPrato;

        public ImagemViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemPrato = itemView.findViewById(R.id.imagem_prato);

        }

        public void vinculaImagem(Bitmap imagem) {
            this.imagemPrato.setImageBitmap(imagem);

        }
    }
}
