package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosAvaliacaoAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosFavoritosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

public class ListaPratosFavoritos extends AppCompatActivity {

    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    private ListaPratosFavoritosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos_favoritos);
        configuraRecyclerviewFavoritos();
    }

    private void configuraRecyclerviewFavoritos() {
        RecyclerView listaPratosFavoritos = findViewById(R.id.lista_pratos_favoritos_recyclerview);
        setAdapterFavoritos(listaPratosFavoritos);
    }

    private void setAdapterFavoritos(RecyclerView recyclerView) {
        adapter = new ListaPratosFavoritosAdapter(this,geraListaFavoritos());
        recyclerView.setAdapter(adapter);

    }

    private List<PratoTipico> geraListaFavoritos(){return pratoTipicoServices.getLista();}

}

