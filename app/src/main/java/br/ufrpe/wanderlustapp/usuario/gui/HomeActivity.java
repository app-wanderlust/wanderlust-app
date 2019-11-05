package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosAvaliacao;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class HomeActivity extends AppCompatActivity {

    private TextView textoExibicao;
    private Usuario usuario;
    private Button  btnVisualizarPrato;
    private Button btnAvaliarPrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuario = Sessao.instance.getUsuario();
        String nomeUsuario = usuario.getPessoa().getNome();
        textoExibicao = findViewById(R.id.textView);
        textoExibicao.setText("Olá, "+nomeUsuario+"!");
        btnVisualizarPrato = findViewById(R.id.botaoVisualizarPrato);
        btnAvaliarPrato = findViewById(R.id.botaoAvaliarPrato);

        btnVisualizarPrato.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListaPratosActivity.class));
            }
        });
        btnAvaliarPrato.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListaPratosAvaliacao.class));
            }
        });
    }
}
