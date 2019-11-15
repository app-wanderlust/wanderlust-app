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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;

public class ListaPratosActivity extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    public static final String TITULO_APPBAR_LISTA = "Lista de pratos";
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
               startActivity(new Intent(ListaPratosActivity.this, CadastraPratosAcitivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
}


