package br.ufrpe.wanderlustapp.usuario.gui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.ufrpe.wanderlustapp.R;

public class AlteraPerfilActivity extends AppCompatActivity {
    public static final String TITULO_APPBAR_LISTA = "Meu perfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_perfil);
        setTitle(TITULO_APPBAR_LISTA);
    }
}
