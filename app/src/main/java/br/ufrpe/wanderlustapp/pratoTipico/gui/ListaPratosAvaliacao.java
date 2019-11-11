package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.os.Bundle;
import android.widget.CompoundButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos_avaliacao);
        configuraRecyclerviewAvaliacao();
    }

    private void configuraRecyclerviewAvaliacao() {
        RecyclerView listaPratosAvaliacao = findViewById(R.id.lista_pratos_avaliacao_recyclerview);
        setAdapterAvaliacao(listaPratosAvaliacao);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(PratoTipico prato, int posicao) {
                criaPessoaPrato(prato);
            }
        });
    }

    private void criaPessoaPrato(PratoTipico prato) {
        pessoaPrato.setPratoTipico(prato);
        pessoaPrato.setPessoa(usuario.getPessoa());
        pessoaPrato.setNota(1);
        try {
            pessoaPratoServices.cadastrar(pessoaPrato);
            Toast.makeText(ListaPratosAvaliacao.this, "Você curtiu: " + prato.getNome(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(ListaPratosAvaliacao.this, "Você já curtiu", Toast.LENGTH_LONG).show();
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
