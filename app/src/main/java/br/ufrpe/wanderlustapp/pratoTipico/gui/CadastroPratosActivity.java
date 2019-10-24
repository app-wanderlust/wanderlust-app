package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.negocio.CidadeServices;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.negocio.PaisServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

public class CadastroPratosActivity extends AppCompatActivity {

    private EditText etNomeDoPrato;
    private EditText etDescricaoDoPrato;
    private EditText etNomeDaCidade;
    private EditText etNomeDoPais;
    private Button btnSalvarPrato;
    private Button btnSalvarCadastrarNovoPrato;
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    PaisServices paisServices = new PaisServices(this);
    CidadeServices cidadeServices = new CidadeServices(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pratos);
        etNomeDoPrato = findViewById(R.id.campoNomeDoPrato);
        etNomeDaCidade = findViewById(R.id.campoNomeCidade);
        etNomeDoPais = findViewById(R.id.campoNomePais);
        etDescricaoDoPrato = findViewById(R.id.campoDescricaoDoPrato);
        btnSalvarPrato = findViewById(R.id.botaoSalvarPrato);
        btnSalvarCadastrarNovoPrato = findViewById(R.id.botaoSalvarCadastrarNovoPrato);

        btnSalvarPrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarCadastro();

            }
        });

    }

    private void tentarCadastro() {
        PratoTipico prato = createPratoTipico();
        try{
            cadastrarPrato(prato);
        }catch (Exception e){
            Toast.makeText(CadastroPratosActivity.this, " Prato Já cadastrado", Toast.LENGTH_LONG).show();
        }
    }

    private PratoTipico createPratoTipico() {
        PratoTipico  pratoTipico = new PratoTipico();
        pratoTipico.setCidade(createCidade());
        pratoTipico.setNome(etNomeDoPrato.getText().toString());
        pratoTipico.setDescricao(etDescricaoDoPrato.getText().toString());
        return pratoTipico;
    }

    private Cidade createCidade() {
        Cidade cidade = new Cidade();
        cidade.setPais(createPais());
        cidade.setNome(etNomeDaCidade.getText().toString());
        return cidade;
    }

    private Pais createPais() {
        Pais pais = new Pais();
        pais.setNome(etNomeDoPais.getText().toString());
        return pais;
    }

    private void cadastrarPrato(PratoTipico pratoTipico) throws Exception {
        paisServices.cadastrar(pratoTipico.getCidade().getPais());
        cidadeServices.cadastrar(pratoTipico.getCidade());
        pratoTipicoServices.cadastrar(pratoTipico);
        startActivity(new Intent(CadastroPratosActivity.this, ListaPratosActivity.class));
        Toast.makeText(CadastroPratosActivity.this, " Prato cadastrado", Toast.LENGTH_LONG).show();
    }
}
