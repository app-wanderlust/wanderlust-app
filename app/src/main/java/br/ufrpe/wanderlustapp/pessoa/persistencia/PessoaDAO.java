package br.ufrpe.wanderlustapp.pessoa.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;

public class PessoaDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
//    private Context context;
//
//    public PessoaDAO(Context context){
//        this.context = context;
//        helper = new DBHelper(context);
//    }

    public Pessoa getPessoa(long id) {
        Pessoa result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA+ " WHERE " + DBHelper.CAMPO_ID_PESSOA + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createPessoa(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(Pessoa pessoa) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_NOME, pessoa.getNome());
        values.put(DBHelper.CAMPO_NASCIMENTO, pessoa.getNascimento());
        long id = db.insert(DBHelper.TABELA_PESSOA, null, values);
        super.close(db);
        return id;
    }

    private Pessoa createPessoa(Cursor cursor){
        Pessoa result = new Pessoa();
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PESSOA);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME);
        result.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NASCIMENTO);
        result.setNascimento(cursor.getString(columnIndex));
        return result;
    }
}
