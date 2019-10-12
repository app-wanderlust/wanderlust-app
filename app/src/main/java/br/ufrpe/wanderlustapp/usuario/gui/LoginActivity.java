package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.wanderlustapp.R;

public class LoginActivity extends AppCompatActivity {
    private TextView TxCadastro;
    private EditText EtEmail;
    private EditText EtSenha;
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        TxCadastro = findViewById(R.id.textoCadastroId);
        EtEmail = findViewById(R.id.campoEmailId);
        EtSenha = findViewById(R.id.campoSenhaId);
        Button loginbtn = findViewById(R.id.botaoLoginId);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Clicou no botão login", Toast.LENGTH_SHORT).show();
            }
        });

        TxCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Clicou no botão cadastro", Toast.LENGTH_SHORT).show();
                Intent registerIntent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(registerIntent);
            }
        });


    }
}
