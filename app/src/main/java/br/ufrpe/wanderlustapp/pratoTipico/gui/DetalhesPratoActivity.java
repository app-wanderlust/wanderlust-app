package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pratoImagem.negocio.PratoImagemServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaImagensAdapter;


public class DetalhesPratoActivity extends AppCompatActivity {
    private ListaImagensAdapter adapterImagem;
    PratoImagemServices pratoImagemServices = new PratoImagemServices(this);
    public static final String TITULO_APPBAR_LISTA = "Detalhes do prato";
    private TextView nomePrato;
    private TextView descPrato;
    private PratoTipico pratoTipico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prato);
        setTitle(TITULO_APPBAR_LISTA);
        configuraREcyclerviewImagens();
        pratoTipico = Sessao.instance.getPratoTipico();
        nomePrato = findViewById(R.id.txtNomePrato);
        nomePrato.setText(pratoTipico.getNome());
        descPrato = findViewById(R.id.txtDescPrato);
        descPrato.setText(pratoTipico.getDescricao());


    }

    private void configuraREcyclerviewImagens() {
        RecyclerView listaDeImagens = findViewById(R.id.lista_imagens_recyclerview);
        setAdapterImagens(listaDeImagens);

    }

    private void setAdapterImagens(RecyclerView listaDeImagens) {
        adapterImagem = new ListaImagensAdapter(this, geraListaImagens());
        listaDeImagens.setAdapter(adapterImagem);
    }

    private List<Bitmap> geraListaImagens() {
        PratoTipico prato = Sessao.instance.getPratoTipico();
        List<Bitmap> listaDeImagens = pratoImagemServices.geraImagens(prato);
        return listaDeImagens;

    }
}
