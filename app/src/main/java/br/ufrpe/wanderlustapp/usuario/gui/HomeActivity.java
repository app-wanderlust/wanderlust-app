package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class HomeActivity extends AppCompatActivity {

    private TextView textoExibicao;
    private Usuario usuario;
    private Button  btnInserirPrato;
    private Button  btnVisualizarPrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuario = Sessao.instance.getUsuario();
        String nomeUsuario = usuario.getPessoa().getNome();
        textoExibicao = findViewById(R.id.textView);
        textoExibicao.setText("Olá, "+nomeUsuario+"!");
        btnInserirPrato = (Button)findViewById(R.id.botaoCadastrarPrato);
        btnVisualizarPrato = (Button)findViewById(R.id.botaoVisualizarPrato);


        btnInserirPrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(HomeActivity.this, CadastroPratosActivity.class);
                startActivity(registerIntent);
            }
        });

        btnVisualizarPrato.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(HomeActivity.this,ListaPratosActivity.class);
                startActivity(registerIntent);
            }
        });






    }
}
