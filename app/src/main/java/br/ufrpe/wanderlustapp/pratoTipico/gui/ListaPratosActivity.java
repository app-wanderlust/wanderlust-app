package br.ufrpe.wanderlustapp.pratoTipico.gui;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_REUISICAO_INSERE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.POSICAO_INVALIDA;

public class ListaPratosActivity extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    public static final String TITULO_APPBAR_LISTA = "Lista de Pratos";
    private ListPratosAdapter adapter;

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
                vaiPraFormularioPratoAcitivity();
            }
        });
    }

    private void vaiPraFormularioPratoAcitivity() {
        Intent iniciarFormularioPrato =
                new Intent(ListaPratosActivity.this,FormularioPratosAcitivity.class);
        startActivityForResult(iniciarFormularioPrato, CODIGO_REUISICAO_INSERE_PRATO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODIGO_REUISICAO_INSERE_PRATO && resultCode == CODIGO_RESULTADO_PRATO_CRIADO && data.hasExtra(CHAVE_PRATO)){
            PratoTipico pratoRecebido = (PratoTipico) data.getSerializableExtra(CHAVE_PRATO);
            inserePrato(pratoRecebido);
        }
        if(requestCode == CODIGO_RESULTADO_PRATO_CRIADO && resultCode == CODIGO_RESULTADO_PRATO_CRIADO && data.hasExtra(CHAVE_PRATO)
                && data.hasExtra("posicao")) {
            PratoTipico pratoRecebido = (PratoTipico) data.getSerializableExtra(CHAVE_PRATO);
            int posicaoRecebida = data.getIntExtra("posicao", POSICAO_INVALIDA);
            pratoTipicoServices.update(pratoRecebido);
            adapter.altera(posicaoRecebida,pratoRecebido);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                Intent abreFormularioComPrato = new Intent(ListaPratosActivity.this,
                        FormularioPratosAcitivity.class);
                abreFormularioComPrato.putExtra(CHAVE_PRATO,prato);
                abreFormularioComPrato.putExtra("posicao",posicao);
                startActivityForResult(abreFormularioComPrato,CODIGO_RESULTADO_PRATO_CRIADO);
            }
        });
    }

    private void configuraRecyclerview() {
        RecyclerView listaPratos = findViewById(R.id.lista_pratos_recyclerview);
        setAdapter(listaPratos);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PratoItemTouchHelperCallback(adapter, pratoTipicoServices));
        itemTouchHelper.attachToRecyclerView(listaPratos);
    }

    private void inserePrato(PratoTipico pratoTipico) {
        try {
            pratoTipicoServices.cadastrar(pratoTipico);
            adapter.adicona(pratoTipico);
        } catch (Exception e) {
            Toast.makeText(ListaPratosActivity.this, "Prato já cadastrado", Toast.LENGTH_LONG).show();
        }

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
}


