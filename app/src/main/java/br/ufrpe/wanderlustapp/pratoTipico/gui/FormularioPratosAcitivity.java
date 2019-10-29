package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.POSICAO_INVALIDA;


public class FormularioPratosAcitivity extends AppCompatActivity {
    private int posicaoRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pratos);

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_PRATO) && dadosRecebidos.hasExtra("posicao") ){
            PratoTipico pratoRecebido = (PratoTipico) dadosRecebidos.getSerializableExtra(CHAVE_PRATO);
            posicaoRecebida = dadosRecebidos.getIntExtra("posicao", POSICAO_INVALIDA);
            TextView nome = findViewById(R.id.formulario_prato_nome);
            nome.setText(pratoRecebido.getNome());
            TextView descricao = findViewById(R.id.formulario_prato_descricao);
            descricao.setText(pratoRecebido.getDescricao());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_prato_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_prato_ic_salva){
            Intent dadosRecebidos = getIntent();
            PratoTipico pratoRecebido = (PratoTipico) dadosRecebidos.getSerializableExtra(CHAVE_PRATO);
            if (pratoRecebido == null){
                pratoRecebido = criaPratoTipico();
            }
            pratoRecebido = atualizaPrato(pratoRecebido);
            retornaPratoViaExtra(pratoRecebido);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private PratoTipico criaPratoTipico() {
        PratoTipico pratoTipico = new PratoTipico();
        preencheAtributosPrato(pratoTipico);
        return pratoTipico;
    }

    private void preencheAtributosPrato(PratoTipico pratoTipico) {
        EditText nome = findViewById(R.id.formulario_prato_nome);
        EditText descricao = findViewById(R.id.formulario_prato_descricao);
        pratoTipico.setNome(nome.getText().toString());
        pratoTipico.setDescricao(descricao.getText().toString());
        pratoTipico.setCidade(createCidadePadrao());
    }

    private void retornaPratoViaExtra(PratoTipico pratoTipico) {
        Intent resultadoInsercao = new Intent();
        TextView novoNome = findViewById(R.id.formulario_prato_nome);
        TextView novoDescricao = findViewById(R.id.formulario_prato_descricao);
        pratoTipico.setNome(novoNome.getText().toString());
        pratoTipico.setDescricao(novoDescricao.getText().toString());
        resultadoInsercao.putExtra(CHAVE_PRATO,pratoTipico);
        resultadoInsercao.putExtra("posicao", posicaoRecebida);
        setResult(CODIGO_RESULTADO_PRATO_CRIADO,resultadoInsercao);
    }

    private PratoTipico atualizaPrato(PratoTipico pratoTipico) {
        preencheAtributosPrato(pratoTipico);
        return pratoTipico;
    }

    private Cidade createCidadePadrao() {
        Cidade cidade = new Cidade();
        cidade.setNome("Recife");
        Pais pais = new Pais();
        pais.setNome("Brasil");
        cidade.setPais(pais);
        return cidade;
    }
}
