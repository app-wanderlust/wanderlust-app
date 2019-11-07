package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pessoaPrato.dominio.PessoaPrato;
import br.ufrpe.wanderlustapp.pessoaPrato.negocio.PessoaPratoServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosAvaliacaoAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListaPratosFavoritosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class ListaPratosFavoritos extends AppCompatActivity {

    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    PessoaPratoServices pessoaPratoServices = new PessoaPratoServices(this);
    private Pessoa pessoa = Sessao.instance.getUsuario().getPessoa();
    private ListaPratosFavoritosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos_favoritos);
        configuraRecyclerviewFavoritos();
    }

    private void configuraRecyclerviewFavoritos() {
        RecyclerView listaPratosFavoritos = findViewById(R.id.lista_pratos_favoritos_recyclerview);
        setAdapterFavoritos(listaPratosFavoritos);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PratoFavoritoItemTouchHelperCallback(adapter, pessoaPratoServices));
        itemTouchHelper.attachToRecyclerView(listaPratosFavoritos);
    }

    private void setAdapterFavoritos(RecyclerView recyclerView) {
        adapter = new ListaPratosFavoritosAdapter(this,geraListaFavoritos());
        recyclerView.setAdapter(adapter);

    }

    private List<PessoaPrato> geraListaFavoritos(){
        return pessoaPratoServices.getListByIdPessoa(pessoa.getId());
    }
}

