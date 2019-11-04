package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class HomeActivity extends AppCompatActivity {

    private TextView textoExibicao;
    private Usuario usuario;
    private Button  btnVisualizarPrato;

    int[] images = {R.drawable.cuscuz, R.drawable.tapioca, R.drawable.lasanha, R.drawable.feijoada};

    String[] version = {"Cuscuz", "Tapioca", "Lasanha", "Feijoada"};

    String[] versionNumber = {"Farinha", "Farinha tbm", "Macarrão", "Feijão"};

    ListView lView;

    ListAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuario = Sessao.instance.getUsuario();
        String nomeUsuario = usuario.getPessoa().getNome();
        btnVisualizarPrato = (Button)findViewById(R.id.botaoVisualizarPrato);

        btnVisualizarPrato.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListaPratosActivity.class));
            }
        });
        lView = (ListView) findViewById(R.id.androidList);

        lAdapter = new ListAdapter(HomeActivity.this, version, versionNumber, images);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(HomeActivity.this, version[i]+" "+versionNumber[i], Toast.LENGTH_SHORT).show();

            }
        });
        }
}
