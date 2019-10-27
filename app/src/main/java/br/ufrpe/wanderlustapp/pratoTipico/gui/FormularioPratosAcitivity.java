package br.ufrpe.wanderlustapp.pratoTipico.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class FormularioPratosAcitivity extends AppCompatActivity {
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pratos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_prato_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_prato_ic_salva){
            EditText nome = findViewById(R.id.formulario_prato_nome);
            EditText descricao = findViewById(R.id.formulario_prato_descricao);
            PratoTipico  pratoTipico = new PratoTipico();
            pratoTipico.setNome(nome.getText().toString());
            pratoTipico.setDescricao(descricao.getText().toString());
            try {
                pratoTipicoServices.cadastrar(pratoTipico);
            } catch (Exception e) {
                Toast.makeText(FormularioPratosAcitivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
