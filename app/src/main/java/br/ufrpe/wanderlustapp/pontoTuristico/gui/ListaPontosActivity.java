package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.negocio.PontoTuristicoServices;
import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoImagem.negocio.PratoImagemServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.AtualizaPratosAcitivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.CadastraPratosAcitivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;
import br.ufrpe.wanderlustapp.pratoTipico.gui.PratoItemTouchHelperCallback;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PONTO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;

public class ListaPontosActivity extends AppCompatActivity {

    PontoTuristicoServices pontoTuristicoServices = new PontoTuristicoServices(this);
    //PontoImagemServices pontoImagemServices = new PontoImagemServices(this);
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
        TextView btnInserePonto = findViewById(R.id.lista_pratos_insere_prato); //ver aqui
        btnInserePonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaPontosActivity.this, CadastraPratosAcitivity.class));
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

    /*private void salvaImagem(PontoImagem pontoImagem) {
        pontoImagemServices.cadastrar(pontoImagem);
    }*/

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

    private void setAdapter(RecyclerView recyclerView){
        adapter = new ListPontosAdapter(this, geraLista());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(PontoTuristico ponto, int posicao) {
                posicaoEnviada = posicao;
                Intent abreFormularioComPonto = getIntent(ponto, posicao);
                startActivityForResult(abreFormularioComPonto,CODIGO_RESULTADO_PRATO_CRIADO); //ver aqui
            }

            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean checked) {
            }

            @Override
            public void onItemClick(PontoTuristico pontoTuristico, int posicao, boolean likeChecked, boolean dislikeChecked) {

            }
        });
    }

    private Intent getIntent(PontoTuristico ponto, int posicao) {
        Intent abreFormularioComPonto = new Intent(ListaPontosActivity.this, AtualizaPontosAcitivity.class);
        abreFormularioComPonto.putExtra(CHAVE_PONTO,ponto);
        abreFormularioComPonto.putExtra("posicao",posicao);
        return abreFormularioComPonto;
    }

    private void configuraRecyclerview() {
        RecyclerView listaPontos = findViewById(R.id.lista_pratos_recyclerview); //ver aqui
        setAdapter(listaPontos);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PontoItemTouchHelperCallback(adapter, pontoTuristicoServices));
        itemTouchHelper.attachToRecyclerView(listaPontos);
    }

}
