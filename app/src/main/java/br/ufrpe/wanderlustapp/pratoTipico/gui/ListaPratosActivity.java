package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoImagem.negocio.PratoImagemServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;



public class ListaPratosActivity extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    PratoImagemServices pratoImagemServices = new PratoImagemServices(this);
    public static final String TITULO_APPBAR_LISTA = "Lista de pratos";
    private ListPratosAdapter adapter;
    private int posicaoEnviada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos);
        setTitle(TITULO_APPBAR_LISTA);
        configuraRecyclerview();
        configuraBtnInserePrato();
    }

    private void configuraBtnInserePrato() {
        TextView btnInserePrato = findViewById(R.id.lista_pratos_insere_prato);
        btnInserePrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ListaPratosActivity.this, CadastraPratosAcitivity.class));
            }
        });
    }

    private void inserePrato(PratoTipico pratoTipico) {
        try {
            pratoTipicoServices.cadastrar(pratoTipico);
            adapter.adiciona(pratoTipico);
            Toast.makeText(getApplicationContext(), "Prato cadastrado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Prato já cadastrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizaPrato(PratoTipico pratoTipico){
        try {
            pratoTipicoServices.update(pratoTipico);
            adapter.altera(posicaoEnviada, pratoTipico);
            Toast.makeText(getApplicationContext(), "Prato atualizado", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Prato já atualizado", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvaImagem(PratoImagem pratoImagem) {
        pratoImagemServices.cadastrar(pratoImagem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        }

    @Override
    protected void onResume() {
        PratoTipico pratoTipico = Sessao.instance.getPratoTipico();
        if (pratoTipico != null){
            if (pratoTipico.getId() == 0){
                inserePrato(pratoTipico);
                Sessao.instance.resetPrato();
            }else{
                atualizaPrato(pratoTipico);
                PratoImagem pratoImagem = Sessao.instance.getPratoImagem();
                if (pratoImagem != null){
                    salvaImagem(pratoImagem);
                }
                Sessao.instance.resetPrato();
                Sessao.instance.resetImagem();
            }
        }

        super.onResume();
    }

    private List<PratoTipico> geraLista(){
        return pratoTipicoServices.getLista();
    }

    private void setAdapter(RecyclerView recyclerView){
        adapter = new ListPratosAdapter(this, geraLista());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(PratoTipico prato, int posicao) {
                posicaoEnviada = posicao;
                Intent abreFormularioComPrato = getIntent(prato, posicao);
                startActivityForResult(abreFormularioComPrato,CODIGO_RESULTADO_PRATO_CRIADO);
            }

            @Override
            public void onItemClick(PratoTipico pratoTipico, int posicao, boolean checked) {
            }
        });
    }

    private Intent getIntent(PratoTipico prato, int posicao) {
        Intent abreFormularioComPrato = new Intent(ListaPratosActivity.this, AtualizaPratosAcitivity.class);
        abreFormularioComPrato.putExtra(CHAVE_PRATO,prato);
        abreFormularioComPrato.putExtra("posicao",posicao);
        return abreFormularioComPrato;
    }

    private void configuraRecyclerview() {
        RecyclerView listaPratos = findViewById(R.id.lista_pratos_recyclerview);
        setAdapter(listaPratos);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PratoItemTouchHelperCallback(adapter, pratoTipicoServices));
        itemTouchHelper.attachToRecyclerView(listaPratos);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar_lista_pratos, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public List<Bitmap> geraImagens(PratoTipico pratoTipico){
        List<Bitmap> listaImagens = new ArrayList<>();
        List<PratoImagem> listaPratoImagem = pratoImagemServices.getList(pratoTipico.getId());
        for(PratoImagem pratoImagem: listaPratoImagem){
            byte[] outImage = pratoImagem.getImagem();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap imagemBitmap = BitmapFactory.decodeStream(imageStream);
            listaImagens.add(imagemBitmap);
        }
        return listaImagens;
    }

}