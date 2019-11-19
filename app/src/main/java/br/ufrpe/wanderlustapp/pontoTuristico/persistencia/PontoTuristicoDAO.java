package br.ufrpe.wanderlustapp.pontoTuristico.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;


public class PontoTuristicoDAO  extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PontoTuristicoDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    private ContentValues getContentValues(PontoTuristico pontoTuristico) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_CIDADE_PONTO, pontoTuristico.getCidade().getId());
        values.put(DBHelper.CAMPO_NOME_PONTO, pontoTuristico.getNome());
        values.put(DBHelper.CAMPO_DESCRICAO_PONTO, pontoTuristico.getDescricao());
        return values;
    }

    private Cursor getCursor(List<PontoTuristico> pontoTuristico, String sql) {
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            pontoTuristico.add(createPontoTuristico(cursor));
        }
        return cursor;
    }

    private void setsPontoTuristico(Cursor cursor, PontoTuristico pontoTuristico, CidadeDAO cidadeDAO) {
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PONTO);
        pontoTuristico.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_PONTO);
        pontoTuristico.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DESCRICAO);
        pontoTuristico.setDescricao(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CIDADE);
        pontoTuristico.setCidade(cidadeDAO.getCidade(cursor.getInt(columnIndex)));
    }

    public PontoTuristico getPontoTuristicoById(long id) {
        PontoTuristico pontoTuristico = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PONTO + " WHERE " + DBHelper.CAMPO_ID_PONTO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        pontoTuristico = getPontoTuristico(pontoTuristico, cursor);
        super.close(cursor, db);
        return pontoTuristico;
    }

    private PontoTuristico getPontoTuristico(PontoTuristico pontoTuristico, Cursor cursor) {
        if (cursor.moveToFirst()) {
            pontoTuristico = createPontoTuristico(cursor);
        }
        return pontoTuristico;
    }

    public PontoTuristico getPontoTuristicoByNome(String nome) {
        PontoTuristico pontoTuristico = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PONTO + " WHERE " + DBHelper.CAMPO_NOME_PONTO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nome});
        pontoTuristico = getPontoTuristico(pontoTuristico, cursor);
        super.close(cursor, db);
        return pontoTuristico;
    }

    public List<PontoTuristico> getListPontoTuristico(){
        List<PontoTuristico> pontos = new ArrayList<PontoTuristico>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PONTO;
        Cursor cursor = getCursor(pontos, sql);
        cursor.close();
        db.close();
        return pontos;
    }

    private PontoTuristico createPontoTuristico(Cursor cursor) {
        PontoTuristico pontoTuristico = new PontoTuristico();
        CidadeDAO cidadeDAO = new CidadeDAO(context);
        setsPontoTuristico(cursor, pontoTuristico, cidadeDAO);
        return pontoTuristico;
    }

    public long cadastrar(PontoTuristico pontoTuristico){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(pontoTuristico);
        long id = db.insert(DBHelper.TABELA_PONTO,null,values);
        super.close(db);
        return id;
    }

    public void updatePontoTuristico(PontoTuristico pontoTuristico){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(pontoTuristico);
        String[] id = new String[]{Long.toString(pontoTuristico.getId())};
        db.update(DBHelper.TABELA_PONTO,values,DBHelper.CAMPO_ID_PONTO+"=?",id);
        super.close();
    }

    public void deletePontoTuristico(PontoTuristico pontoTuristico){
        db = helper.getWritableDatabase();
        db.delete("tb_ponto","id = ?", new String[] {String.valueOf(pontoTuristico.getId())});
        db.close();
    }
}

