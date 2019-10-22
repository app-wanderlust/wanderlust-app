package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

public class CadastroPratosActivity extends AppCompatActivity {

    private EditText etNomeDoPrato;
    private EditText etDescricaoDoPrato;
    private Button btnSalvarPrato;
    private Button btnSalvarCadastrarNovoPrato;
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pratos);

        etNomeDoPrato = findViewById(R.id.campoNomeDoPrato);
        etDescricaoDoPrato = findViewById(R.id.campoDescricaoDoPrato);
        btnSalvarPrato = findViewById(R.id.botaoSalvarPrato);
        btnSalvarCadastrarNovoPrato = findViewById(R.id.botaoSalvarCadastrarNovoPrato);

        btnSalvarPrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PratoTipico prato = createPratoTipico();
                try{
                    cadastrarPrato(prato);
                }catch (Exception e){
                    Toast.makeText(CadastroPratosActivity.this, " Prato cadastrado", Toast.LENGTH_LONG).show();
                }

            }
        });

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
        cidade.setNome(etNomeDoPrato.getText().toString());
        return cidade;
    }

    private void cadastrarPrato(PratoTipico pratoTipico) throws Exception {
        pratoTipicoServices.cadastrar(pratoTipico);
        startActivity(new Intent(CadastroPratosActivity.this, ListaPratosActivity.class));
        Toast.makeText(CadastroPratosActivity.this, " Prato cadastrado", Toast.LENGTH_LONG).show();
    }
}
