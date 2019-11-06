package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosAvaliacaoAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

public class ListaPratosAvaliacao extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    private ListaPratosAvaliacaoAdapter adapter;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos_avaliacao);
        configuraRecyclerviewAvaliacao();
    }

    private void configuraRecyclerviewAvaliacao() {
        RecyclerView listaPratosAvaliacao = findViewById(R.id.lista_pratos_avaliacao_recyclerview);
        setAdapterAvaliacao(listaPratosAvaliacao);
    }

    private void setAdapterAvaliacao(RecyclerView recyclerView) {
        adapter = new ListaPratosAvaliacaoAdapter(this,geraListaFavoritos());
        recyclerView.setAdapter(adapter);
    }

    private List<PratoTipico> geraListaFavoritos(){return pratoTipicoServices.getLista();}

}
