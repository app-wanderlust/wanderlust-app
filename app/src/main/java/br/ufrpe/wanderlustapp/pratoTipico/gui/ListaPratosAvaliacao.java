package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.negocio.PessoaPratoServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosAvaliacaoAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class ListaPratosAvaliacao extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    PessoaPratoServices pessoaPratoServices = new PessoaPratoServices(this);
    PessoaPrato pessoaPrato = new PessoaPrato();
    private ListaPratosAvaliacaoAdapter adapter;
    private Usuario usuario  = Sessao.instance.getUsuario();
    private ToggleButton likeButton;
    private ToggleButton dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos_avaliacao);
        configuraRecyclerviewAvaliacao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sessao.instance.resetPrato();
        Sessao.instance.resetImagem();
    }

    private void configuraRecyclerviewAvaliacao() {
        RecyclerView listaPratosAvaliacao = findViewById(R.id.lista_imagens_recyclerview);
        setAdapterAvaliacao(listaPratosAvaliacao);
        likeButton = findViewById(R.id.button_favorite);
        dislikeButton = findViewById(R.id.button_dislike_toggle);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(PratoTipico pratoTipico, int posicao) {
                Sessao.instance.setPratoTipico(pratoTipico);
                startActivity(new Intent(ListaPratosAvaliacao.this, DetalhesPratoActivity.class));
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
                Toast.makeText(ListaPratosAvaliacao.this, "Você curtiu: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }else {
                pessoaPratoServices.update(pessoaPrato);
                Toast.makeText(ListaPratosAvaliacao.this, "Você curtiu2: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(ListaPratosAvaliacao.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void dislikePessoaPrato(PratoTipico prato) {
        pessoaPrato = getPessoaPrato(prato);
        pessoaPrato.setNota(-1);
        try {
            if (pessoaPrato.getId() == 0) {
                pessoaPratoServices.cadastrar(pessoaPrato);
                Toast.makeText(ListaPratosAvaliacao.this, "Você não gostou de: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }else{
                pessoaPratoServices.update(pessoaPrato);
                Toast.makeText(ListaPratosAvaliacao.this, "Você não gostou de2: " + prato.getNome(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(ListaPratosAvaliacao.this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void setAdapterAvaliacao(RecyclerView recyclerView) {
        adapter = new ListaPratosAvaliacaoAdapter(this,geraListaFavoritos());
        recyclerView.setAdapter(adapter);
    }

    private List<PratoTipico> geraListaFavoritos(){
        return pratoTipicoServices.getLista();
    }

}
