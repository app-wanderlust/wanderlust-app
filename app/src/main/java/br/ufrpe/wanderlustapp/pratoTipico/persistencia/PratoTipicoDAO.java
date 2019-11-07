package br.ufrpe.wanderlustapp.pratoTipico.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.cidade.persistencia.CidadeDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class PratoTipicoDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PratoTipicoDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    private ContentValues getContentValues(PratoTipico prato) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_CIDADE, prato.getCidade().getId());
        values.put(DBHelper.CAMPO_NOME_PRATO, prato.getNome());
        values.put(DBHelper.CAMPO_DESCRICAO, prato.getDescricao());
        return values;
    }

    private Cursor getCursor(List<PratoTipico> pratos, String sql) {
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            pratos.add(createPratoTipico(cursor));
        }
        return cursor;
    }

    private void setsPratoTipico(Cursor cursor, PratoTipico prato, CidadeDAO cidadeDAO) {
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PRATO);
        prato.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_PRATO);
        prato.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DESCRICAO);
        prato.setDescricao(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CIDADE);
        prato.setCidade(cidadeDAO.getCidade(cursor.getInt(columnIndex)));
    }

    public PratoTipico getPratoTipicoById(long id) {
        PratoTipico prato = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO + " WHERE " + DBHelper.CAMPO_ID_PRATO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        prato = getPratoTipico(prato, cursor);
        super.close(cursor, db);
        return prato;
    }

    private PratoTipico getPratoTipico(PratoTipico prato, Cursor cursor) {
        if (cursor.moveToFirst()) {
            prato = createPratoTipico(cursor);
        }
        return prato;
    }

    public PratoTipico getPratoTipicoByNome(String nome) {
        PratoTipico prato = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO + " WHERE " + DBHelper.CAMPO_NOME_PRATO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nome});
        prato = getPratoTipico(prato, cursor);
        super.close(cursor, db);
        return prato;
    }

    public List<PratoTipico> getListPrato(){
        List<PratoTipico> pratos = new ArrayList<PratoTipico>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO;
        Cursor cursor = getCursor(pratos, sql);
        cursor.close();
        db.close();
        return pratos;
    }

    private PratoTipico createPratoTipico(Cursor cursor) {
        PratoTipico prato = new PratoTipico();
        CidadeDAO cidadeDAO = new CidadeDAO(context);
        setsPratoTipico(cursor, prato, cidadeDAO);
        return prato;
    }

    public long cadastrar(PratoTipico prato){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(prato);
        long id = db.insert(DBHelper.TABELA_PRATO,null,values);
        super.close(db);
        return id;
    }

    public void updatePrato(PratoTipico prato){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(prato);
        String[] id = new String[]{Long.toString(prato.getId())};
        db.update(DBHelper.TABELA_PRATO,values,DBHelper.CAMPO_ID_PRATO+"=?",id);
        super.close();
    }

    public void deletePrato(PratoTipico pratoTipico){
        db = helper.getWritableDatabase();
        db.delete("tb_prato","id = ?", new String[] {String.valueOf(pratoTipico.getId())});
        db.close();
    }
}
