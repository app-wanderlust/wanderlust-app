package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pontoImagem.dominio.PontoImagem;
import br.ufrpe.wanderlustapp.pontoImagem.negocio.PontoImagemServices;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.gui.adapter.ListPontosAdapter;
import br.ufrpe.wanderlustapp.pontoTuristico.negocio.PontoTuristicoServices;

import static br.ufrpe.wanderlustapp.pontoTuristico.gui.pontosActivityConstantes.CHAVE_PONTO;
import static br.ufrpe.wanderlustapp.pontoTuristico.gui.pontosActivityConstantes.CODIGO_RESULTADO_PONTO_CRIADO;

public class ListaPontosActivity extends AppCompatActivity {

    PontoTuristicoServices pontoTuristicoServices = new PontoTuristicoServices(this);
    PontoImagemServices pontoImagemServices = new PontoImagemServices(this);
    public static final String TITULO_APPBAR_LISTA = "Lista de pontos";
    private ListPontosAdapter adapter;
    private int posicaoEnviada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pontos);
        setTitle(TITULO_APPBAR_LISTA);
        configuraRecyclerview();
        configuraBtnInserePonto();
    }

    private void configuraBtnInserePonto() {
        TextView btnInserePonto = findViewById(R.id.lista_pontos_insere_ponto);
        btnInserePonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaPontosActivity.this, CadastraPontosActivity.class));
            }
        });
    }

    private void inserePonto(PontoTuristico pontoTuristico) {
        try {
            pontoTuristicoServices.cadastrar(pontoTuristico);
            adapter.adiciona(pontoTuristico);
            Toast.makeText(getApplicationContext(), "Ponto cadastrado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ponto já cadastrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizaPonto(PontoTuristico pontoTuristico){
        try {
            pontoTuristicoServices.update(pontoTuristico);
            adapter.altera(posicaoEnviada, pontoTuristico);
            Toast.makeText(getApplicationContext(), "Ponto atualizado", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Ponto já atualizado", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvaImagem(PontoImagem pontoImagem) {
        pontoImagemServices.cadastrar(pontoImagem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        PontoTuristico pontoTuristico = Sessao.instance.getPontoTuristico();
        if (pontoTuristico != null){
            if (pontoTuristico.getId() == 0){
                inserePonto(pontoTuristico);
                Sessao.instance.resetPonto();
            }else{
                atualizaPonto(pontoTuristico);
                PontoImagem pontoImagem = Sessao.instance.getPontoImagem();
                if (pontoImagem != null){
                    salvaImagem(pontoImagem);
                }
                Sessao.instance.resetPonto();
                Sessao.instance.resetImagem();
            }
        }

        super.onResume();
    }

    private List<PontoTuristico> geraLista(){
        return pontoTuristicoServices.getLista();
    }

    private void setAdapter (RecyclerView recyclerView){
        adapter = new ListPontosAdapter(this,geraLista());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao) {
                posicaoEnviada = posicao;
                Intent abreFormularioComPonto = getIntent(pontoTuristico, posicao);
                startActivityForResult(abreFormularioComPonto,CODIGO_RESULTADO_PONTO_CRIADO);
            }
            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean isChecked) {
            }
            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean likeChecked, boolean dislikeChecked) {

            }
        });
    }


    private Intent getIntent(PontoTuristico ponto, int posicao) {
        Intent abreFormularioComPonto = new Intent(ListaPontosActivity.this, AtualizaPontosActivity.class);
        abreFormularioComPonto.putExtra(CHAVE_PONTO,ponto);
        abreFormularioComPonto.putExtra("posicao",posicao);
        return abreFormularioComPonto;
    }

    private void configuraRecyclerview() {
        RecyclerView listaPontos = findViewById(R.id.lista_pontos_recyclerview);
        setAdapter(listaPontos);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PontoItemTouchHelperCallback(adapter, pontoTuristicoServices));
        itemTouchHelper.attachToRecyclerView(listaPontos);
    }

}
