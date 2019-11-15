package br.ufrpe.wanderlustapp.pratoImagem.negocio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoImagem.persistencia.PratoImagemDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class PratoImagemServices {
    private PratoImagemDAO pratoImagemDAO;
    private Context context;

    public PratoImagemServices(Context context){
        this.context = context;
        pratoImagemDAO = new PratoImagemDAO(this.context);
    }

    public void cadastrar(PratoImagem pratoImagem) throws Exception{
      // if(pratoImagemDAO.getImagemByIdPrato(pratoImagem.getPratoTipico().getId()) != null){
           // throw new Exception();
     //  }else{
            long idPratoImagem = pratoImagemDAO.cadastrar(pratoImagem);
            pratoImagem.setId(idPratoImagem);
       //}
    }

    public List<PratoImagem> getList(long id){
        return pratoImagemDAO.getImagemByIdPrato(id);
    }

    public List<Bitmap> geraImagens(PratoTipico pratoTipico){
        List<Bitmap> listaImagens = new ArrayList<>();
        List<PratoImagem> listaPratoImagem = this.getList(pratoTipico.getId());
        for(PratoImagem pratoImagem: listaPratoImagem){
            byte[] outImage = pratoImagem.getImagem();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap imagemBitmap = BitmapFactory.decodeStream(imageStream);
            listaImagens.add(imagemBitmap);
        }
        return listaImagens;
    }
}
