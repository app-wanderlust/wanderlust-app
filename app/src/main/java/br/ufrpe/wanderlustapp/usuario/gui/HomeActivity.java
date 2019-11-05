package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class HomeActivity extends AppCompatActivity {

    private TextView textoExibicao;
    private Usuario usuario;
    private Button  btnVisualizarPrato;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuario = Sessao.instance.getUsuario();
        String nomeUsuario = usuario.getPessoa().getNome();
        textoExibicao = findViewById(R.id.textView);
        textoExibicao.setText("Olá, "+nomeUsuario+"!");
        btnVisualizarPrato = (Button)findViewById(R.id.botaoVisualizarPrato);

        btnVisualizarPrato.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListaPratosActivity.class));
            }
        });

    }
}
