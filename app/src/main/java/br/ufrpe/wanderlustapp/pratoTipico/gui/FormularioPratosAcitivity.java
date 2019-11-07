package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.POSICAO_INVALIDA;


public class FormularioPratosAcitivity extends AppCompatActivity {
    public static final String TITULO_APPBAR_INSERE = "Inserir prato";
    public static final String TITULO_APPBAR_ALTERA = "Alterar prato";
    private int posicaoRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pratos);
        setTitle(TITULO_APPBAR_INSERE);

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_PRATO) && dadosRecebidos.hasExtra("posicao") ){
            setTitle(TITULO_APPBAR_ALTERA);
            recebePrato(dadosRecebidos);
        }
    }

    private void recebePrato(Intent dadosRecebidos) {
        PratoTipico pratoRecebido = (PratoTipico) dadosRecebidos.getSerializableExtra(CHAVE_PRATO);
        posicaoRecebida = dadosRecebidos.getIntExtra("posicao", POSICAO_INVALIDA);
        TextView nome = findViewById(R.id.formulario_prato_nome);
        TextView descricao = findViewById(R.id.formulario_prato_descricao);
        nome.setText(pratoRecebido.getNome());
        descricao.setText(pratoRecebido.getDescricao());
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
            fetchPratoTipico(pratoRecebido);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchPratoTipico(PratoTipico pratoRecebido) {
        if (verficaCampos()) {
            if (pratoRecebido == null) {
                pratoRecebido = criaPratoTipico();
            } else {
                pratoRecebido = atualizaPrato(pratoRecebido);
            }
            retornaPratoViaExtra(pratoRecebido);
        }
    }

    private PratoTipico criaPratoTipico() {
        PratoTipico pratoTipico = new PratoTipico();
        preencheAtributosPrato(pratoTipico);
        return pratoTipico;
    }

    private PratoTipico atualizaPrato(PratoTipico pratoTipico) {
        preencheAtributosPrato(pratoTipico);
        return pratoTipico;
    }

    private boolean verficaCampos(){
        EditText nome = findViewById(R.id.formulario_prato_nome);
        EditText descricao = findViewById(R.id.formulario_prato_descricao);
        return nome.length() > 0 && descricao.length() > 0;
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

    private Cidade createCidadePadrao() {
        Cidade cidade = new Cidade();
        cidade.setNome("Recife");
        Pais pais = new Pais();
        pais.setNome("Brasil");
        cidade.setPais(pais);
        return cidade;
    }
}
