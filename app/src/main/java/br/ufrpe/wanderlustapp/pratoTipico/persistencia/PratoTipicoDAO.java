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
import br.ufrpe.wanderlustapp.pratoTipico.dominio.StatusAtividade;

public class PratoTipicoDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PratoTipicoDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public PratoTipico getPratoTipicoById(long id) {
        PratoTipico prato = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO + " WHERE " + DBHelper.CAMPO_ID_PRATO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            prato = createPratoTipico(cursor);
        }
        super.close(cursor, db);
        return prato;
    }

    public PratoTipico getPratoTipicoByNome(String nome) {
        PratoTipico prato = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO + " WHERE " + DBHelper.CAMPO_NOME_PRATO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nome});
        if (cursor.moveToFirst()) {
            prato = createPratoTipico(cursor);
        }
        super.close(cursor, db);
        return prato;
    }

    public List<PratoTipico> getListPrato(){
        List<PratoTipico> pratos = new ArrayList<PratoTipico>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            pratos.add(createPratoTipico(cursor));
        }
        cursor.close();
        db.close();
        return pratos;
    }

    public PratoTipico getPratoTipico(long id) {
        PratoTipico prato = getPratoTipicoById(id);
        if (prato != null){
            prato = null;
        }
        return prato;
    }

    private PratoTipico createPratoTipico(Cursor cursor) {
        PratoTipico prato = new PratoTipico();
        CidadeDAO cidadeDAO = new CidadeDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PRATO);
        prato.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_PRATO);
        prato.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DESCRICAO);
        prato.setDescricao(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CIDADE);
        prato.setCidade(cidadeDAO.getCidade(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_STATUS_ATIVIDADE);
        prato.setStatusAtividade(StatusAtividade.stringToEnum(cursor.getString(columnIndex)));
        return prato;
    }

    public long cadastrar(PratoTipico prato){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_CIDADE,prato.getCidade().getId());
        values.put(DBHelper.CAMPO_NOME_PRATO,prato.getNome());
        values.put(DBHelper.CAMPO_DESCRICAO,prato.getDescricao());
        values.put(DBHelper.CAMPO_STATUS_ATIVIDADE,prato.getStatusAtividade().toString());
        long id = db.insert(DBHelper.TABELA_PRATO,null,values);
        super.close(db);
        return id;
    }

    public void updatePrato(PratoTipico prato){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_CIDADE,prato.getCidade().getId());
        values.put(DBHelper.CAMPO_NOME_PRATO,prato.getNome());
        values.put(DBHelper.CAMPO_DESCRICAO,prato.getDescricao());
        values.put(DBHelper.CAMPO_STATUS_ATIVIDADE,prato.getStatusAtividade().toString());
        String[] id = new String[]{Long.toString(prato.getId())};
        db.update(DBHelper.TABELA_PRATO,values,DBHelper.CAMPO_ID_PRATO+"=?",id);
        super.close();
    }

}
