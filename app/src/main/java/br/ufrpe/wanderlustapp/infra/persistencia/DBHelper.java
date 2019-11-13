package br.ufrpe.wanderlustapp.infra.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database.db";

    //TABELA USUARIO
    public static final String TABELA_USUARIO = "tb_usuario";
    public static final String CAMPO_ID_USUARIO = "id";
    public static final String CAMPO_FK_PESSOA = "fk_pessoa";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_SENHA = "senha";

    //Tabela Pessoa
    public static final String TABELA_PESSOA = "tb_pessoa";
    public static final String CAMPO_ID_PESSOA = "id";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_NASCIMENTO = "nascimento";

    //TabelaPais
    public static final String TABELA_PAIS = "tb_pais";
    public static final String CAMPO_ID_PAIS = "id";
    public static final String CAMPO_NOME_PAIS = "nome_pais";

    //TabelaCidade
    public static final String TABELA_CIDADE = "tb_cidade";
    public static final String CAMPO_ID_CIDADE = "id";
    public static final String CAMPO_NOME_CIDADE = "nome_cidade";
    public static final String CAMPO_FK_PAIS = "fk_pais";

    //Tabela Prato
    public static final String TABELA_PRATO = "tb_prato";
    public static final String CAMPO_ID_PRATO = "id";
    public static final String CAMPO_NOME_PRATO = "nome_prato";
    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String CAMPO_FK_CIDADE = "fk_cidade";

    //Tabela PessoaPrato
    public static final String TABELA_PESSOA_PRATO = "tb_pessoa_prato";
    public static final String CAMPO_ID_PESSOA_PRATO = "id";
    public static final String CAMPO_FK_ID_PESSOA = "fk_pessoa";
    public static final String CAMPO_FK_ID_PRATO = "fk_prato";
    public static final String CAMPO_NOTA = "nota";

    //Tabela PratoImagem
    public static final String TABELA_PRATO_IMAGEM = "tb_prato_imagem";
    public static final String CAMPO_ID_PRATO_IMAGEM = "id";
    public static final String CAMPO_FK_ID_PRATO_TIPICO = "fk_prato";
    public static final String CAMPO_IMAGEM = "imagem";

    private static final String[] TABELAS = {
            TABELA_PESSOA, TABELA_USUARIO, TABELA_PAIS, TABELA_CIDADE, TABELA_PRATO, TABELA_PESSOA_PRATO, TABELA_PRATO_IMAGEM
    };

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUsuario(db);
        createTablePessoa(db);
        createTablePais(db);
        createTableCidade(db);
        createTablePrato(db);
        createTablePessoaPrato(db);
        createTablePratoImagem(db);
    }


    private void createTableUsuario(SQLiteDatabase db) {
        String sqlTbUsuario =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL UNIQUE, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbUsuario = String.format(sqlTbUsuario,
                TABELA_USUARIO, CAMPO_ID_USUARIO, CAMPO_FK_PESSOA, CAMPO_EMAIL, CAMPO_SENHA);
        db.execSQL(sqlTbUsuario);
    }

    private void createTablePessoa(SQLiteDatabase db){
        String sqlTbPessoa =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbPessoa = String.format(sqlTbPessoa,
                TABELA_PESSOA, CAMPO_ID_PESSOA, CAMPO_NOME, CAMPO_NASCIMENTO);
        db.execSQL(sqlTbPessoa);
    }

    private void createTablePais(SQLiteDatabase db){
        String sqlTbPais =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL " +
                        ");";
        sqlTbPais = String.format(sqlTbPais,
                TABELA_PAIS, CAMPO_ID_PAIS, CAMPO_NOME_PAIS);
        db.execSQL(sqlTbPais);
    }

    private void createTableCidade(SQLiteDatabase db){
        String sqlTbCidade =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbCidade = String.format(sqlTbCidade,
                TABELA_CIDADE, CAMPO_ID_CIDADE, CAMPO_NOME_CIDADE, CAMPO_FK_PAIS);
        db.execSQL(sqlTbCidade);
    }

    private void createTablePrato(SQLiteDatabase db){
        String sqlTbPrato =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbPrato = String.format(sqlTbPrato,
                TABELA_PRATO, CAMPO_ID_PRATO, CAMPO_NOME_PRATO, CAMPO_DESCRICAO, CAMPO_FK_CIDADE);
        db.execSQL(sqlTbPrato);
    }

    private void createTablePessoaPrato(SQLiteDatabase db) {
        String sqlTbPessoaPrato =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbPessoaPrato = String.format(sqlTbPessoaPrato,
                TABELA_PESSOA_PRATO, CAMPO_ID_PESSOA_PRATO, CAMPO_FK_ID_PESSOA, CAMPO_FK_ID_PRATO, CAMPO_NOTA);
        db.execSQL(sqlTbPessoaPrato);
    }


    private void createTablePratoImagem(SQLiteDatabase db) {
        String sqlTbPratoImagem =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s INTEGER NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " FOREIGN KEY(%5$s) REFERENCES %6$s(%7$s)" +
                        ");";
        sqlTbPratoImagem = String.format(sqlTbPratoImagem,
                TABELA_PRATO_IMAGEM, CAMPO_ID_PRATO_IMAGEM, CAMPO_FK_ID_PRATO_TIPICO, CAMPO_IMAGEM,
                CAMPO_FK_ID_PRATO_TIPICO, TABELA_PRATO, CAMPO_ID_PRATO);
        db.execSQL(sqlTbPratoImagem);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    private void dropTables(SQLiteDatabase db){
        StringBuilder dropTables = new StringBuilder();
        for (String tabela: TABELAS){
            dropTables.append(" DROP TABLE IF EXISTS ");
            dropTables.append(tabela);
            dropTables.append("; ");
        }
        db.execSQL(dropTables.toString());
    }
}
