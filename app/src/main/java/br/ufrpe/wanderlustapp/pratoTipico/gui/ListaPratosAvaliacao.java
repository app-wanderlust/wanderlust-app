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
import br.ufrpe.wanderlustapp.pessoaPrato.persistencia.PessoaPratoDAO;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosAvaliacaoAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class ListaPratosAvaliacao extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    PessoaPratoServices pessoaPratoServices = new PessoaPratoServices(this);
    private ListaPratosAvaliacaoAdapter adapter;
    private Usuario usuario  = Sessao.instance.getUsuario();
    private PessoaPratoDAO pessoaPratoDAO;
    private PessoaPrato pessoaPrato;
    private ToggleButton likeButton;
    private ToggleButton dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos_avaliacao);
        configuraRecyclerviewAvaliacao();
        pessoaPratoDAO = new PessoaPratoDAO(this);
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

    private void likePessoaPrato(PratoTipico prato) {
        if(pessoaPratoServices.getPessoaPrato(Sessao.instance.getUsuario().getPessoa().getId(),prato.getId()) == null){
            pessoaPrato = new PessoaPrato();
            pessoaPrato.setPratoTipico(prato);
            pessoaPrato.setPessoa(usuario.getPessoa());
            pessoaPrato.setNota(1);
            Toast.makeText(this, "Você curtiu " + prato.getNome(), Toast.LENGTH_SHORT).show();
            try {
                pessoaPratoServices.cadastrar(pessoaPrato);
            } catch(Exception e){

            }
        }else {
            PessoaPrato pessoaPratoAtual = pessoaPratoServices.getPessoaPrato(Sessao.instance.getUsuario().getPessoa().getId(),prato.getId());
            pessoaPratoAtual.setNota(1);
            pessoaPratoServices.update(pessoaPratoAtual);
            Toast.makeText(this, "Você curtiu " + prato.getNome(), Toast.LENGTH_SHORT).show();
        }
    }

    private void dislikePessoaPrato(PratoTipico prato) {
        if(pessoaPratoServices.getPessoaPrato(Sessao.instance.getUsuario().getPessoa().getId(),prato.getId()) == null){
            pessoaPrato = new PessoaPrato();
            pessoaPrato.setPratoTipico(prato);
            pessoaPrato.setPessoa(usuario.getPessoa());
            pessoaPrato.setNota(-1);
            try {
                pessoaPratoServices.cadastrar(pessoaPrato);
                Toast.makeText(this, "Você  não curtiu " + prato.getNome(), Toast.LENGTH_SHORT).show();
            } catch(Exception e){}
        }else{
            PessoaPrato pessoaPratoAtual = pessoaPratoDAO.getPessoaPrato(Sessao.instance.getUsuario().getPessoa().getId(),prato.getId());
            pessoaPratoAtual.setNota(-1);
            pessoaPratoServices.update(pessoaPratoAtual);
            Toast.makeText(this, "Você  não curtiu " + prato.getNome(), Toast.LENGTH_SHORT).show();
        }


    }
    private  void zeraNota(PratoTipico pratoTipico){
        PessoaPrato pessoaPratoAtual = pessoaPratoDAO.getPessoaPrato(Sessao.instance.getUsuario().getPessoa().getId(),pratoTipico.getId());
        pessoaPratoAtual.setNota(0);
        pessoaPratoServices.update(pessoaPratoAtual);
    }

    private void setAdapterAvaliacao(RecyclerView recyclerView) {
        adapter = new ListaPratosAvaliacaoAdapter(this,geraListaFavoritos());
        recyclerView.setAdapter(adapter);
    }

    private List<PratoTipico> geraListaFavoritos(){
        return pratoTipicoServices.getLista();
    }

}
