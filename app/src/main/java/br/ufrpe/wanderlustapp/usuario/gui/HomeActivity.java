package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class HomeActivity extends AppCompatActivity {

    private TextView textoExibicao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuario = Sessao.instance.getUsuario();
        String nomeUsuario = usuario.getPessoa().getNome();
        textoExibicao = findViewById(R.id.textView);
        textoExibicao.setText("Olá, "+nomeUsuario);

    }
}
