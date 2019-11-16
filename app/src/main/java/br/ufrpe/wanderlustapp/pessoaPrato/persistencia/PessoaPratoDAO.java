package br.ufrpe.wanderlustapp.pessoaPrato.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pessoa.persistencia.PessoaDAO;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class PessoaPratoDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PessoaPratoDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    private PessoaPrato getPessoaPrato(long idPessoa, long idPrato, PessoaPrato pessoaPrato, String sql) {
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(idPessoa), Long.toString(idPrato)});
        if (cursor.moveToFirst()){
            pessoaPrato = createPessoaPrato(cursor);
        }
        return pessoaPrato;
    }

    private Cursor getCursor(long id, List<PessoaPrato> pessoaPratos, String sql) {
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        while (cursor.moveToNext()) {
            pessoaPratos.add(createPessoaPrato(cursor));
        }
        return cursor;
    }

    private Cursor getCursor(List<PessoaPrato> pessoaPratos, String sql) {
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            pessoaPratos.add(createPessoaPrato(cursor));
        }
        return cursor;
    }

    private ContentValues getContentValues(PessoaPrato pessoaPrato) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_ID_PESSOA, pessoaPrato.getPessoa().getId());
        values.put(DBHelper.CAMPO_FK_ID_PRATO, pessoaPrato.getPratoTipico().getId());
        values.put(DBHelper.CAMPO_NOTA, pessoaPrato.getNota());
        return values;
    }

    private void setsPessoaPrato(Cursor cursor, PessoaPrato pessoaPrato, PessoaDAO pessoaDAO, PratoTipicoDAO pratoTipicoDAO) {
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PESSOA_PRATO);
        pessoaPrato.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_ID_PESSOA);
        pessoaPrato.setPessoa(pessoaDAO.getPessoa(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_ID_PRATO);
        pessoaPrato.setPratoTipico(pratoTipicoDAO.getPratoTipicoById(cursor.getInt(columnIndex)));
    }

    public PessoaPrato getPessoaPrato(long idPessoa, long idPrato){
        PessoaPrato pessoaPrato = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA_PRATO + " WHERE " + DBHelper.CAMPO_FK_ID_PESSOA + " LIKE ? AND " + DBHelper.CAMPO_FK_ID_PRATO + " LIKE ?;";
        pessoaPrato = getPessoaPrato(idPessoa, idPrato, pessoaPrato, sql);
        super.close(db);
        return pessoaPrato;
    }

    public PessoaPrato getPessoaPratoById(long id){
        PessoaPrato pessoaPrato = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA_PRATO + " WHERE " + DBHelper.CAMPO_ID_PESSOA_PRATO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()){
            pessoaPrato = createPessoaPrato(cursor);
        }
        super.close(db);
        return pessoaPrato;
    }

    public List<PessoaPrato> getPratoByIdPessoa(long id){
        List<PessoaPrato> pessoaPratos = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA_PRATO + " WHERE " + DBHelper.CAMPO_FK_ID_PESSOA + " LIKE ?;";
        Cursor cursor = getCursor(id, pessoaPratos, sql);
        cursor.close();
        db.close();
        return pessoaPratos;
    }

    public List<PessoaPrato> getPessoaPratoByIdPrato(long id){
        List<PessoaPrato> pessoaPratos = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA_PRATO + " WHERE " + DBHelper.CAMPO_FK_ID_PRATO + " LIKE ?;";
        Cursor cursor = getCursor(id, pessoaPratos, sql);
        cursor.close();
        db.close();
        return pessoaPratos;
    }

    public List<PessoaPrato> getListPessoaPrato(){
        List<PessoaPrato> pessoaPratos = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA_PRATO;
        Cursor cursor = getCursor(pessoaPratos, sql);
        cursor.close();
        db.close();
        return pessoaPratos;
    }

    private PessoaPrato createPessoaPrato(Cursor cursor) {
        PessoaPrato pessoaPrato = new PessoaPrato();
        PessoaDAO pessoaDAO = new PessoaDAO(context);
        PratoTipicoDAO pratoTipicoDAO = new PratoTipicoDAO(context);
        setsPessoaPrato(cursor, pessoaPrato, pessoaDAO, pratoTipicoDAO);
        return pessoaPrato;
    }

    public long cadastrar(PessoaPrato pessoaPrato){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(pessoaPrato);
        long id = db.insert(DBHelper.TABELA_PESSOA_PRATO,null,values);
        super.close(db);
        return id;
    }

    public void updatePessoaPrato(PessoaPrato pessoaPrato){
        db = helper.getWritableDatabase();
        ContentValues values = getContentValues(pessoaPrato);
        String[] id = new String[]{Long.toString(pessoaPrato.getId())};
        db.update(DBHelper.TABELA_PESSOA_PRATO,values,DBHelper.CAMPO_ID_PESSOA_PRATO+"=?",id);
        super.close();
    }

    public void deletePessoaPrato(PessoaPrato pessoaPrato){
        db = helper.getWritableDatabase();
        db.delete("tb_pessoa_prato", "id = ?", new String[]{String.valueOf(pessoaPrato.getId())});
        super.close();
    }
}
