package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.negocio.CidadeServices;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.negocio.PaisServices;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class CadastraPontosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_INSERE = "Inserir ponto";
    CidadeServices cidadeServices = new CidadeServices(this);
    PaisServices paisServices = new PaisServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_pontos);
        setTitle(TITULO_APPBAR_INSERE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_prato_salva, menu); // ver isso
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_prato_ic_salva){ //ver isso
            PontoTuristico pontoTuristico = criaPontoTuristico();
            if(verficaCampos()) {
                Sessao.instance.setPontoTuristico(pontoTuristico);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private PontoTuristico criaPontoTuristico() {
        PontoTuristico pontoTuristico = new PontoTuristico();
        if (verficaCampos()){
            preencheAtributosPonto(pontoTuristico);
        }
        return pontoTuristico;
    }

    private boolean verficaCampos(){
        EditText nome = findViewById(R.id.formulario_ponto_nome);
        EditText descricao = findViewById(R.id.formulario_ponto_descricao);
        return nome.length() > 0 && descricao.length() > 0;
    }

    private void preencheAtributosPonto(PontoTuristico pontoTuristico) {
        EditText nome = findViewById(R.id.formulario_ponto_nome);
        EditText descricao = findViewById(R.id.formulario_ponto_descricao);
        pontoTuristico.setNome(nome.getText().toString());
        pontoTuristico.setDescricao(descricao.getText().toString());
        pontoTuristico.setCidade(createCidadePadrao());
    }

    private Cidade createCidadePadrao() {
        Pais pais = new Pais();
        pais.setNome("Brasil");
        paisServices.cadastrar(pais);
        Cidade cidade = new Cidade();
        cidade.setNome("Recife");
        cidade.setPais(pais);
        cidadeServices.cadastrar(cidade);
        return cidade;
    }



















}
