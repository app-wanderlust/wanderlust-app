package br.ufrpe.wanderlustapp.pratoImagem.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class PratoImagemDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PratoImagemDAO(Context context){
        this.context = context;
        helper = new DBHelper(this.context);
    }

    private ContentValues getContentValues(PratoImagem pratoImagem) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_ID_PRATO_TIPICO, pratoImagem.getPratoTipico().getId());
        values.put(DBHelper.CAMPO_IMAGEM, pratoImagem.getImagem());
        return values;
    }

    private void setsPratoImagem(Cursor cursor, PratoImagem pratoImagem, PratoTipicoDAO pratoTipicoDAO) {
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PRATO_IMAGEM);
        pratoImagem.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_ID_PRATO_TIPICO);
        pratoImagem.setPratoTipico(pratoTipicoDAO.getPratoTipicoById(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_IMAGEM);
        pratoImagem.setImagem(cursor.getBlob(columnIndex));
    }

    private PratoImagem createPratoImagem(Cursor cursor){
        PratoImagem pratoImagem = new PratoImagem();
        PratoTipicoDAO pratoTipicoDAO = new PratoTipicoDAO(context);
        setsPratoImagem(cursor, pratoImagem, pratoTipicoDAO);
        return pratoImagem;
    }

    private Cursor getCursor(long id, List<PratoImagem> pratoImagens, String sql){
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        while (cursor.moveToNext()){
            pratoImagens.add(createPratoImagem(cursor));
        }
        return cursor;
    }

    public List<PratoImagem> getImagemByIdPrato(long id){
        List<PratoImagem> pratoImagens = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO_IMAGEM + " WHERE " + DBHelper.CAMPO_FK_ID_PRATO_TIPICO + " LIKE ?;";
        Cursor cursor = getCursor(id, pratoImagens, sql);
        db.close();
        return pratoImagens;
    }

    public long cadastrar(PratoImagem pratoImagem){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(pratoImagem);
        long id = db.insert(DBHelper.TABELA_PRATO_IMAGEM,null,values);
        super.close(db);
        return id;
    }

    public void deletePratoImagem(PratoImagem pratoImagem){
        db = helper.getWritableDatabase();
        db.delete(DBHelper.TABELA_PRATO_IMAGEM, "id = ?", new String[]{String.valueOf(pratoImagem.getId())});
        super.close();
    }
}
