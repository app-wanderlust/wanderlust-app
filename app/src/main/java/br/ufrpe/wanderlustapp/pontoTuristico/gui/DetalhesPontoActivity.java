package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pontoImagem.negocio.PontoImagemServices;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaImagensAdapter;

public class DetalhesPontoActivity extends AppCompatActivity {

    private ListaImagensAdapter adapterImagem;
    PontoImagemServices pontoImagemServices = new PontoImagemServices(this);
    private TextView nomePonto;
    private PontoTuristico pontoTuristico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_ponto);
        configuraREcyclerviewImagens();
        pontoTuristico = Sessao.instance.getPontoTuristico();
        nomePonto = findViewById(R.id.txtNomePonto);
        nomePonto.setText(pontoTuristico.getNome());
    }

    private void configuraREcyclerviewImagens() {
        RecyclerView listaDeImagens = findViewById(R.id.lista_imagens_recyclerview_ponto);
        setAdapterImagens(listaDeImagens);
    }

    private void setAdapterImagens(RecyclerView listaDeImagens) {
        adapterImagem = new ListaImagensAdapter(this, geraListaImagens());
        listaDeImagens.setAdapter(adapterImagem);
    }

    private List<Bitmap> geraListaImagens() {
        PontoTuristico ponto = Sessao.instance.getPontoTuristico();
        List<Bitmap> listaDeImagens = pontoImagemServices.geraImagens(ponto);
        return listaDeImagens;
    }





}
