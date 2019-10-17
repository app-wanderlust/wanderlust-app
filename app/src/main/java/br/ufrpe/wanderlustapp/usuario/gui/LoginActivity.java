package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.usuario.negocio.UsuarioServices;

public class LoginActivity extends AppCompatActivity {
    private TextView TxCadastro;
    private EditText EtEmail;
    private EditText EtSenha;
    private Toast toast = null;
    private final UsuarioServices usuarioServices = new UsuarioServices(this);

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
                if(validaCampos()){
                    try {
                        usuarioServices.login(EtEmail.getText().toString(), EtSenha.getText().toString());
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } catch (Exception e) {

                        if (toast != null){
                            toast.cancel();
                        }
                        toast = Toast.makeText(LoginActivity.this,"E-mail e/ou senha inválidos.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

        TxCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(registerIntent);
            }
        });


    }

    private boolean validaCampos() {
        boolean resultado = false;
        String email = EtEmail.getText().toString();
        String senha = EtSenha.getText().toString();
        if (isEmail(email)){
            resultado = true;
        }else{
            EtEmail.setError("Email inválido");
            EtEmail.requestFocus();
        }
        if (isCampoVazio(senha)){
            EtSenha.setError("Informe uma senha");
        }
        return resultado && EtSenha.getText().length() != 0;
    }
    private boolean isEmail(String email){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }
    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }
}
