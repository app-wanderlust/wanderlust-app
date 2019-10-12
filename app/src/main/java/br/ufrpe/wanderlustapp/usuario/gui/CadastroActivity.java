package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.ufrpe.wanderlustapp.R;

public class CadastroActivity extends AppCompatActivity {
    private TextView txVoltar;
    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etConfirmarSenha;
    private EditText etNascimento;
    private Button btnCadastrar;
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txVoltar = findViewById(R.id.textoVoltarId);
        etNome = findViewById(R.id.campoNomeCadastroId);
        etEmail = findViewById(R.id.campoEmailCadastroId);
        etSenha = findViewById(R.id.campoSenhaCadastroId);
        etConfirmarSenha = findViewById(R.id.campoConfirmarSenhaCadastroId);
        etNascimento = findViewById(R.id.campoDataNascimentoCadastroId);
        Button btnCadastrar = findViewById(R.id.botaoCadastrarId);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(cadsatarar()){
                    //Intent registerIntent = new Intent(LoginActivity.this, CadastroActivity.class);
                    //startActivity(registerIntent);
                }else{
                    //Mensagem de erro
                }*/


            }
        });

        txVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(registerIntent);
            }
        });

    }
}
