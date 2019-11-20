package br.ufrpe.wanderlustapp.pontoImagem.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pontoImagem.dominio.PontoImagem;
import br.ufrpe.wanderlustapp.pontoTuristico.persistencia.PontoTuristicoDAO;
import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class PontoImagemDAO extends AbstractDAO {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PontoImagemDAO(Context context){
        this.context = context;
        helper = new DBHelper(this.context);
    }

    private ContentValues getContentValues(PontoImagem pontoImagem) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_ID_PONTO_TURISTICO, pontoImagem.getPontoTuristico().getId());
        values.put(DBHelper.CAMPO_IMAGEM_PONTO, pontoImagem.getImagem());
        return values;
    }

    private PontoImagem createPontoImagem(Cursor cursor){
        PontoTuristicoDAO pontoturisticoDAO = new PontoTuristicoDAO(context);
        PontoImagem pontoImagem = new PontoImagem();
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PONTO_IMAGEM);
        pontoImagem.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_ID_PONTO_TURISTICO);
        pontoImagem.setPontoTuristico(pontoturisticoDAO.getPontoTuristicoById(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_IMAGEM);
        pontoImagem.setImagem(cursor.getBlob(columnIndex));
        return pontoImagem;
    }

    public List<PontoImagem> getImagemByIdPonto(long id){
        List<PontoImagem> pontoImagens = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PONTO_IMAGEM + " WHERE " + DBHelper.CAMPO_FK_ID_PONTO_TURISTICO + " = ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        while (cursor.moveToNext()){
            pontoImagens.add(createPontoTuristico(cursor));
        }
        cursor.close();
        db.close();
        return pontoImagens;
    }

    public long cadastrar(PontoImagem pontoImagem){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(pontoImagem);
        long id = db.insert(DBHelper.TABELA_PONTO_IMAGEM,null,values);
        super.close(db);
        return id;
    }

    public void deletePontoImagem(PontoImagem pontoImagem){
        db = helper.getWritableDatabase();
        db.delete(DBHelper.TABELA_PONTO_IMAGEM, "id = ?", new String[]{String.valueOf(pontoImagem.getId())});
        super.close();
    }

}
