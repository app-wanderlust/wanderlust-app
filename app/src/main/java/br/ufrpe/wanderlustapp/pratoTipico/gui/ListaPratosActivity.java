package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.gui.adapter.ListPratosAdapter;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_REUISICAO_INSERE_PRATO;

public class ListaPratosActivity extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    private ListPratosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pratos);
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
        if(requestCode == 2 && resultCode == CODIGO_RESULTADO_PRATO_CRIADO && data.hasExtra(CHAVE_PRATO)
                && data.hasExtra("posicao")) {
            PratoTipico pratoRecebido = (PratoTipico) data.getSerializableExtra(CHAVE_PRATO);
            Toast.makeText(this, pratoRecebido.getNome(), Toast.LENGTH_SHORT).show();
            int posicaoRecebida = data.getIntExtra("posicao", -1);
            System.out.println("vlau"+pratoRecebido.getId());
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
                startActivityForResult(abreFormularioComPrato,2);
            }
        });
    }

    private void configuraRecyclerview() {
        RecyclerView listaPratos = findViewById(R.id.lista_pratos_recyclerview);
        setAdapter(listaPratos);
    }

    private void inserePrato(PratoTipico pratoTipico) {
        try {
            pratoTipicoServices.cadastrar(pratoTipico);
            adapter.adicona(pratoTipico);
        } catch (Exception e) {
            Toast.makeText(ListaPratosActivity.this, "Prato já cadastrado", Toast.LENGTH_LONG).show();
        }
    }



}


