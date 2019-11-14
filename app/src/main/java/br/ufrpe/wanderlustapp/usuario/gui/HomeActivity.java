package br.ufrpe.wanderlustapp.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.negocio.PessoaPratoServices;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosAvaliacao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosFavoritos;
import br.ufrpe.wanderlustapp.pratoTipico.gui.PratoFavoritoItemTouchHelperCallback;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosFavoritosAdapter;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;
import br.ufrpe.wanderlustapp.usuario.gui.adapter.ListaPratosSugestaoAdapter;

public class HomeActivity extends AppCompatActivity {
    private Usuario usuario = Sessao.instance.getUsuario();
    RecyclerView recyclerView;
    ArrayList<String> Tela;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition;

    PessoaPratoServices pessoaPratoServices = new PessoaPratoServices(this);
    private Pessoa pessoa = Sessao.instance.getUsuario().getPessoa();
    private ListaPratosSugestaoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview1);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Tela);
        HorizontalLayout = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);
        configuraRecyclerviewSugestao();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(HomeActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    defineIntent();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void configuraRecyclerviewSugestao() {
        RecyclerView listaPratosSugestao = findViewById(R.id.lista_pratos_sugestao_recyclerview);
        setAdapterSugestao(listaPratosSugestao);
    }

    private void setAdapterSugestao(RecyclerView recyclerView) {
        adapter = new ListaPratosSugestaoAdapter(this,geraListaSugestao());
        recyclerView.setAdapter(adapter);
    }

    private List<PessoaPrato> geraListaSugestao(){
        return pessoaPratoServices.getListByIdPessoa(pessoa.getId());
    }













    private void defineIntent() {
        if(RecyclerViewItemPosition == 0){
            Intent iniciarAvaliarPratos =
                    new Intent(HomeActivity.this, ListaPratosAvaliacao.class);
            startActivity(iniciarAvaliarPratos);
        }
        else if(RecyclerViewItemPosition == 1){
            Intent iniciarPratosFavoritos =
                    new Intent(HomeActivity.this, ListaPratosFavoritos.class);
            startActivity(iniciarPratosFavoritos);
        }
        else if(RecyclerViewItemPosition == 2){
            Intent iniciarGerenciarPrato =
                    new Intent(HomeActivity.this, ListaPratosActivity.class);
            startActivity(iniciarGerenciarPrato);
        }
        else if(RecyclerViewItemPosition == 3){
            Sessao.instance.reset();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }

    }

    public void AddItemsToRecyclerViewArrayList(){
        Tela = new ArrayList<>();
        Tela.add("Avaliar prato");
        Tela.add("Pratos favoritos");
        Tela.add("Gerenciar pratos");
        Tela.add("Sair");
    }
}