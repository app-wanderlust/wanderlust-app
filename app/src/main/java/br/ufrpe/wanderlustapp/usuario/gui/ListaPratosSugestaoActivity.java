package br.ufrpe.wanderlustapp.usuario.gui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.negocio.PessoaPratoServices;
import br.ufrpe.wanderlustapp.usuario.gui.adapter.ListaPratosSugestaoAdapter;

public class ListaPratosSugestaoActivity extends AppCompatActivity {

    PessoaPratoServices pessoaPratoServices = new PessoaPratoServices(this);
    private Pessoa pessoa = Sessao.instance.getUsuario().getPessoa();
    private ListaPratosSugestaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos_sugestao);
        configuraRecyclerviewFavoritos();
    }

    private void configuraRecyclerviewFavoritos() {
        RecyclerView listaPratosSugestao = findViewById(R.id.lista_pratos_sugestao_recyclerview);
        setAdapterSugestao(listaPratosSugestao);
    }

    private void setAdapterSugestao(RecyclerView recyclerView) {
        adapter = new ListaPratosSugestaoAdapter(this,geraListaFavoritos());
        recyclerView.setAdapter(adapter);
    }

    private List<PessoaPrato> geraListaFavoritos(){
        return pessoaPratoServices.getListByIdPessoa(pessoa.getId());
    }
}
