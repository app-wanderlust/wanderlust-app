package br.ufrpe.wanderlustapp.pais.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;

public class PaisDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PaisDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public Pais getPaisById(long id) {
        Pais pais = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PAIS + " WHERE " + DBHelper.CAMPO_ID_PAIS + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            pais = createPais(cursor);
        }
        super.close(cursor, db);
        return pais;
    }

    public Pais getPaisByNome(String nome) {
        Pais pais = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PAIS + " WHERE " + DBHelper.CAMPO_NOME_PAIS + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nome});
        if (cursor.moveToFirst()) {
            pais = createPais(cursor);
        }
        super.close(cursor, db);
        return pais;
    }

    public Pais getPais(long id) {
        Pais pais = getPaisById(id);
        if (pais != null){
            pais = null;
        }
        return pais;
    }

    private Pais createPais(Cursor cursor) {
        Pais pais = new Pais();
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PAIS);
        pais.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_PAIS);
        pais.setNome(cursor.getString(columnIndex));
        return pais;
    }

    public long cadastrarPais(Pais pais){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_NOME_PAIS,pais.getNome());
        long id = db.insert(DBHelper.TABELA_PAIS,null,values);
        super.close(db);

        return id;
    }

    public void updatePais(Pais pais){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_NOME_PAIS,pais.getNome());
        String[] id = new String[]{Long.toString(pais.getId())};
        db.update(DBHelper.TABELA_PAIS,values,DBHelper.CAMPO_ID_PAIS+"=?",id);
        super.close();
    }

}
