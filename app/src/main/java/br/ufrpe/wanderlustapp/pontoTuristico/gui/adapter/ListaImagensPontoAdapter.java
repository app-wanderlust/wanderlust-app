package br.ufrpe.wanderlustapp.pontoTuristico.gui.adapter;

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
import br.ufrpe.wanderlustapp.pontoImagem.negocio.PontoImagemServices;

public class ListaImagensPontoAdapter extends RecyclerView.Adapter<ListaImagensPontoAdapter.ImagemViewHolder>{

    private final Context context;
    private final List<Bitmap> imagensPonto;
    private PontoImagemServices pontoImagemServices;

    public ListaImagensPontoAdapter(Context context, List<Bitmap> pontos){
        this.context = context;
        this.imagensPonto = pontos;
        pontoImagemServices = new PontoImagemServices(this.context);
    }

    @NonNull
    @Override
    public ListaImagensPontoAdapter.ImagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_lista_imagens_pontos,parent,false);
        return new ImagemViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaImagensPontoAdapter.ImagemViewHolder holder, int position) {
        Bitmap imagem = imagensPonto.get(position);
        holder.vinculaImagem(imagem);

    }

    @Override
    public int getItemCount() {
        return imagensPonto.size();
    }

    class ImagemViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imagemPonto;

        public ImagemViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemPonto = itemView.findViewById(R.id.imagem_prato);

        }

        public void vinculaImagem(Bitmap imagem) {
            this.imagemPonto.setImageBitmap(imagem);

        }
    }



}
