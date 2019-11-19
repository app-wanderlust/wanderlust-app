package br.ufrpe.wanderlustapp.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosFavoritos;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private TextView nomeUsuario;
    private TextView emailUsuario;
    private TextView listaFavoritosUsuarios;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        usuario = Sessao.instance.getUsuario();
        nomeUsuario = findViewById(R.id.textoNomeUsuario);
        emailUsuario = findViewById(R.id.textoEmailUsuario);
        listaFavoritosUsuarios = findViewById(R.id.textoVerFavoritos);

        nomeUsuario.setText(usuario.getPessoa().getNome());
        emailUsuario.setText(usuario.getEmail());

        listaFavoritosUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this, ListaPratosFavoritos.class));
            }
        });








    }
}
