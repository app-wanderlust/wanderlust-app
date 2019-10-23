package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.ufrpe.wanderlustapp.R;

public class ListaPratosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Pratos Cadastrados";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos);
        setTitle(TITULO_APPBAR);



    }

}


