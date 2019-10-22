package br.ufrpe.wanderlustapp.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.wanderlustapp.infra.persistencia.AbstractDAO;
import br.ufrpe.wanderlustapp.infra.persistencia.DBHelper;
import br.ufrpe.wanderlustapp.pessoa.persistencia.PessoaDAO;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class  UsuarioDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public UsuarioDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Usuario getUsuarioById(long id){
        Usuario result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO+ " WHERE " + DBHelper.CAMPO_ID_USUARIO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createUsuario(cursor);
        }
        super.close(cursor, db);

        return result;
    }

    public Usuario getUsuario(String login) {
        Usuario result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " +DBHelper.TABELA_USUARIO+ " WHERE " + DBHelper.CAMPO_EMAIL + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{login});
        if (cursor.moveToFirst()) {
            result = createUsuario(cursor);
        }
        super.close(cursor,db);
        return result;
    }

    public Usuario getUsuario(String login, String senha) {
        Usuario result = getUsuario(login);
        if ((result != null) && !(senha.equals(result.getSenha()))) {
            result = null;
        }
        return result;
    }

    public void updateUsuario(Usuario usuario){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_EMAIL, usuario.getEmail());
        values.put(DBHelper.CAMPO_SENHA, usuario.getSenha());
        values.put(DBHelper.CAMPO_FK_PESSOA, usuario.getPessoa().getId());
        String[] id = new String[]{Long.toString(usuario.getId())};
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.CAMPO_ID_USUARIO+"=?", id);
        super.close();
    }

    public long cadastrar(Usuario usuario) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_PESSOA, usuario.getPessoa().getId());
        values.put(DBHelper.CAMPO_EMAIL, usuario.getEmail());
        values.put(DBHelper.CAMPO_SENHA, usuario.getSenha());

        long id = db.insert(DBHelper.TABELA_USUARIO, null, values);
        super.close(db);

        return id;
    }

    private Usuario createUsuario(Cursor cursor) {
        Usuario result = new Usuario();
        PessoaDAO pessoaDAO = new PessoaDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_USUARIO);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_PESSOA);
        result.setPessoa(pessoaDAO.getPessoa(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_EMAIL);
        result.setEmail(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_SENHA);
        result.setSenha(cursor.getString(columnIndex));
        return result;
    }

}
