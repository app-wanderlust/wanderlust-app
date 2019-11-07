package br.ufrpe.wanderlustapp.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosAvaliacao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosFavoritos;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class HomeActivity extends AppCompatActivity {

    private Usuario = Sessao.instance.getUsuario();
    private TextView textoExibicao;
    private Button  btnVisualizarPrato;
    private Button btnAvaliarPrato;
    private Button btnVisualizarPratoFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findById();

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
        btnVisualizarPratoFavoritos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListaPratosFavoritos.class));
            }
        });
    }

    private void findById() {
        String nomeUsuario = usuario.getPessoa().getNome();
        textoExibicao = findViewById(R.id.textView);
        textoExibicao.setText("Olá, "+nomeUsuario+"!");
        btnVisualizarPrato = findViewById(R.id.botaoVisualizarPrato);
        btnAvaliarPrato = findViewById(R.id.botaoAvaliarPrato);
        btnVisualizarPratoFavoritos = findViewById(R.id.botaoVisualizarPratoFavoritos);
    }
}
