package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class ListaPratosActivity extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos);

        RecyclerView listaPratos = findViewById(R.id.lista_pratos_recyclerview);
        setAdapter(listaPratos);
        configuraBtnInserePrato();

    }

    private void configuraBtnInserePrato() {
        TextView btnInserePrato = findViewById(R.id.lista_pratos_insere_prato);
        btnInserePrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiPraFormularioPratoAcitivity();
            }
        });
    }

    @Override
    protected void onResume() {
        RecyclerView listaPratos = findViewById(R.id.lista_pratos_recyclerview);
        setAdapter(listaPratos);
        super.onResume();
    }

    private void vaiPraFormularioPratoAcitivity() {
        Intent iniciarFormularioPrato =
                new Intent(ListaPratosActivity.this,FormularioPratosAcitivity.class);
        startActivity(iniciarFormularioPrato);

    }

    private List<PratoTipico> geraLista(){
        return pratoTipicoServices.getLista();
    }

    private void setAdapter(RecyclerView recyclerView){
        recyclerView.setAdapter(new ListPratosAdapter(this,geraLista()));

    }


}


