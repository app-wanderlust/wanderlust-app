package br.ufrpe.wanderlustapp.pratoImagem.negocio;

import android.content.Context;

import java.util.List;

import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoImagem.persistencia.PratoImagemDAO;

public class PratoImagemServices {
    private PratoImagemDAO pratoImagemDAO;

    public PratoImagemServices(Context context){
        pratoImagemDAO = new PratoImagemDAO(context);
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
}
