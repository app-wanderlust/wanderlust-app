package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class ListaPratosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Pratos Cadastrados";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PratoTipico> pratoTipicoList;
    private PratoTipicoDAO pratoTipicoDAO;
    private Context context = this;
    private PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(context);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos);
        setTitle(TITULO_APPBAR);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        pratoTipicoList = pratoTipicoServices.getLista();
        if(pratoTipicoList.size()==0){
            Toast.makeText(ListaPratosActivity.this, "Nada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ListaPratosActivity.this, "foi "+pratoTipicoList.size(), Toast.LENGTH_SHORT).show();
        }

    }

}


