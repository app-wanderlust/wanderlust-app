package br.ufrpe.wanderlustapp.pontoImagem.negocio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.pontoImagem.dominio.PontoImagem;
import br.ufrpe.wanderlustapp.pontoImagem.persistencia.PontoImagemDAO;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;

public class PontoImagemServices {
    private PontoImagemDAO pontoImagemDAO;
    private Context context;

    public PontoImagemServices(Context context){
        this.context = context;
        pontoImagemDAO = new PontoImagemDAO(this.context);
    }

    public void cadastrar(PontoImagem pontoImagem){
        long idPontoImagem = pontoImagemDAO.cadastrar(pontoImagem);
        pontoImagem.setId(idPontoImagem);
    }

    public List<PontoImagem> getList(long id){
        return pontoImagemDAO.getImagemByIdPonto(id);
    }

    public List<Bitmap> geraImagens(PontoTuristico pontoTuristico){
        List<Bitmap> listaImagens = new ArrayList<>();
        List<PontoImagem> listaPontoImagem = this.getList(pontoTuristico.getId());
        for(PontoImagem pontoImagem: listaPontoImagem){
            byte[] outImage = pontoImagem.getImagem();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap imagemBitmap = BitmapFactory.decodeStream(imageStream);
            listaImagens.add(imagemBitmap);
        }
        return listaImagens;
    }








}
