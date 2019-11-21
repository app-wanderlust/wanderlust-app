package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.gui.adapter.ListaPontosVisualizacaoAdapter;
import br.ufrpe.wanderlustapp.pontoTuristico.negocio.PontoTuristicoServices;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class ListaPontosVisualizacao extends AppCompatActivity {

    PontoTuristicoServices pontoTuristicoServices = new PontoTuristicoServices(this);
    private ListaPontosVisualizacaoAdapter adapter;
    private Usuario usuario  = Sessao.instance.getUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pontos_avaliacao);
        configuraRecyclerviewAvaliacao();
    }

    private void configuraRecyclerviewAvaliacao() {
        RecyclerView listaPontosAvaliacao = findViewById(R.id.lista_imagens_recyclerview_ponto);
        setAdapterAvaliacao(listaPontosAvaliacao);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao) {
                Sessao.instance.setPontoTuristico(pontoTuristico);
                startActivity(new Intent(ListaPontosVisualizacao.this, DetalhesPontoActivity.class));
            }

            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean isChecked) {

            }

            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean likeChecked, boolean dislikeChecked) {

            }
        });
    }

    private void setAdapterAvaliacao(RecyclerView recyclerView) {
        adapter = new ListaPontosVisualizacaoAdapter(this,geraListaFavoritos());
        recyclerView.setAdapter(adapter);

    }

    private List<PontoTuristico> geraListaFavoritos(){ return pontoTuristicoServices.getLista(); }

}
