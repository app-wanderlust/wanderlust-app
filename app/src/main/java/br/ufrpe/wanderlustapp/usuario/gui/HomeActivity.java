package br.ufrpe.wanderlustapp.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.infra.recomendacao.Recomendacao;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.negocio.PessoaPratoServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.DetalhesPratoActivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosAvaliacao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosFavoritos;
import br.ufrpe.wanderlustapp.pratoTipico.gui.OnItemClickListener;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosAvaliacaoAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;
import br.ufrpe.wanderlustapp.usuario.gui.adapter.ListaPratosRecomendadosAdapter;

public class HomeActivity extends Activity implements
        OnItemSelectedListener{
    Spinner s1,s2;
    PratoTipicoServices pratoTipicoServices;
    PessoaPratoServices pessoaPratoServices;
    PessoaPrato pessoaPrato = new PessoaPrato();
    private ListaPratosRecomendadosAdapter adapter;
    private Usuario usuario;
    private Recomendacao recomendacao;
    RecyclerView recyclerView;
    ArrayList<String> Tela;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        s1 = (Spinner)findViewById(R.id.spinner1);
        s2 = (Spinner)findViewById(R.id.spinner2);
        s1.setOnItemSelectedListener(this);
        usuario  = Sessao.instance.getUsuario();
        pratoTipicoServices = new PratoTipicoServices(this);
        pessoaPratoServices = new PessoaPratoServices(this);
        recomendacao = new Recomendacao(this);

        configuraRecyclerviewSlopeOne();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview1);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Tela);
        HorizontalLayout = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);
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
    protected void onResume() {
        super.onResume();
        Sessao.instance.resetPrato();
        Sessao.instance.resetImagem();
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
            Intent iniciarPerfil =
                    new Intent(HomeActivity.this, PerfilActivity.class);
            startActivity(iniciarPerfil);
        }else if(RecyclerViewItemPosition == 4){
            Sessao.instance.reset();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }

    }

    private void configuraRecyclerviewSlopeOne() {
        RecyclerView listaPratosRecomendados = findViewById(R.id.lista_slopeone_recyclerview);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        listaPratosRecomendados.setLayoutManager(RecyclerViewLayoutManager);
        HorizontalLayout = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        listaPratosRecomendados.setLayoutManager(HorizontalLayout);

        setAdapterRecomendados(listaPratosRecomendados);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(PratoTipico pratoTipico, int posicao) {
                Sessao.instance.setPratoTipico(pratoTipico);
                startActivity(new Intent(HomeActivity.this, DetalhesPratoActivity.class));
            }

            @Override
            public void onItemClick(PratoTipico pratoTipico, int posicao, boolean isChecked) {
            }

            @Override
            public void onItemClick(PratoTipico pratoTipico, int posicao, boolean like, boolean dislike) {
                if (like){
                    likePessoaPrato(pratoTipico);
                }else if(dislike){
                    dislikePessoaPrato(pratoTipico);
                }else if(!like && !dislike){
                    zeraNota(pratoTipico);
                }
            }
        });
    }

    private PessoaPrato getPessoaPrato(PratoTipico pratoTipico){
        pessoaPrato = pessoaPratoServices.getPessoaPrato(usuario.getPessoa().getId(), pratoTipico.getId());
        if (pessoaPrato == null){
            pessoaPrato = new PessoaPrato();
            pessoaPrato.setPratoTipico(pratoTipico);
            pessoaPrato.setPessoa(usuario.getPessoa());
        }
        return pessoaPrato;
    }

    private void likePessoaPrato(PratoTipico prato) {
        pessoaPrato = getPessoaPrato(prato);
        pessoaPrato.setNota(1);
        try {
            if (pessoaPrato.getId() == 0) {
                pessoaPratoServices.cadastrar(pessoaPrato);
                Toast.makeText(HomeActivity.this, "Você curtiu: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }else {
                pessoaPratoServices.update(pessoaPrato);
                Toast.makeText(HomeActivity.this, "Você curtiu2: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void dislikePessoaPrato(PratoTipico prato) {
        pessoaPrato = getPessoaPrato(prato);
        pessoaPrato.setNota(-1);
        try {
            if (pessoaPrato.getId() == 0) {
                pessoaPratoServices.cadastrar(pessoaPrato);
                Toast.makeText(HomeActivity.this, "Você não gostou de: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }else{
                pessoaPratoServices.update(pessoaPrato);
                Toast.makeText(HomeActivity.this, "Você não gostou de2: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private  void zeraNota(PratoTipico pratoTipico){
        pessoaPrato = getPessoaPrato(pratoTipico);
        pessoaPrato.setNota(0);
        try {
            if (pessoaPrato.getId() == 0) {
                pessoaPratoServices.cadastrar(pessoaPrato);
            } else {
                pessoaPratoServices.update(pessoaPrato);
            }
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapterRecomendados(RecyclerView recyclerView) {
        adapter = new ListaPratosRecomendadosAdapter(this,geraListaRecomendados());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<PratoTipico> geraListaRecomendados(){
        return recomendacao.getListaPratosRecomendados();
    }


    public void AddItemsToRecyclerViewArrayList(){
        Tela = new ArrayList<>();
        Tela.add("Avaliar prato");
        Tela.add("Pratos favoritos");
        Tela.add("Gerenciar pratos");
        Tela.add("Perfil");
        Tela.add("Sair");
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        String sp1= String.valueOf(s1.getSelectedItem());
        if(sp1.contentEquals("Brasil")) {
            List<String> list = new ArrayList<String>();
            list.add("Recife");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            s2.setAdapter(dataAdapter);
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}