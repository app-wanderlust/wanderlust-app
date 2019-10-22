package br.ufrpe.wanderlustapp.cidade.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pais.persistencia.PaisDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class CidadeDAO extends AbstractDAO {

    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public CidadeDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public Cidade getCidadeById(long id) {
        Cidade cidade = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_CIDADE + " WHERE " + DBHelper.CAMPO_ID_CIDADE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            cidade = createCidade(cursor);
        }
        super.close(cursor, db);
        return cidade;
    }

    public Cidade getCidadeByNome(String nome) {
        Cidade cidade = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_CIDADE + " WHERE " + DBHelper.CAMPO_NOME_CIDADE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nome});
        if (cursor.moveToFirst()) {
            cidade = createCidade(cursor);
        }
        super.close(cursor, db);
        return cidade;
    }

    public Cidade getCidade(long id) {
        Cidade  cidade = getCidadeById(id);
        if (cidade != null){
            cidade = null;
        }
        return cidade;
    }

    private Cidade createCidade(Cursor cursor) {
        Cidade cidade = new Cidade();
        PaisDAO paisDAO = new PaisDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_CIDADE);
        cidade.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_CIDADE);
        cidade.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_PAIS);
        cidade.setPais(paisDAO.getPais(cursor.getInt(columnIndex)));
        return cidade;
    }

    public long cadastrar(Cidade cidade){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_PAIS,cidade.getPais().getId());
        values.put(DBHelper.CAMPO_NOME_CIDADE,cidade.getNome());
        long id = db.insert(DBHelper.TABELA_CIDADE,null,values);
        super.close(db);

        return id;
    }

    public void updateCidade(Cidade cidade){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_PAIS,cidade.getPais().getId());
        values.put(DBHelper.CAMPO_NOME_CIDADE,cidade.getNome());
        String[] id = new String[]{Long.toString(cidade.getId())};
        db.update(DBHelper.TABELA_CIDADE,values,DBHelper.CAMPO_ID_CIDADE+"=?",id);
        super.close();
    }
}
