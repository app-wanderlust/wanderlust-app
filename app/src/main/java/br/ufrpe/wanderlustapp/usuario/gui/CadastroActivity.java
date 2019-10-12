package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;
import br.ufrpe.wanderlustapp.usuario.negocio.UsuarioServices;

public class CadastroActivity extends AppCompatActivity {
    private TextView txVoltar;
    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etConfirmarSenha;
    private EditText etNascimento;
    private Button btnCadastrar;
    UsuarioServices usuarioServices = new UsuarioServices(this);

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
        btnCadastrar = findViewById(R.id.botaoCadastrarId);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampos()){
                    cadastrar();
                }
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

    private void cadastrar() {
        if (validaCampos()){
            Usuario usuario = createUsuario();
            try{
                usuarioServices.cadastrar(usuario);
                startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(CadastroActivity.this, "Esse e-mail já foi cadastrado", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(CadastroActivity.this, "Preencha os campos corretamente",Toast.LENGTH_SHORT).show();
        }
    }

    private void senhasIguais() {
        if (!etSenha.getText().toString().equals(etConfirmarSenha.getText().toString())){
            Toast.makeText(this, "As senhas são diferentes", Toast.LENGTH_LONG).show();
        }
    }

    private Usuario createUsuario(){
        Usuario usuario = new Usuario();
        usuario.setPessoa(createPessoa());
        usuario.setEmail(etEmail.getText().toString());
        usuario.setSenha(etSenha.getText().toString());
        return usuario;
    }

    private Pessoa createPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(etNome.getText().toString());
        pessoa.setNascimento(etNascimento.getText().toString());
        return pessoa;
    }

    private boolean validaCampos() {
        return etNome.getText().toString().length() != 0 &&
                etSenha.getText().toString().length() != 0 &&
                etConfirmarSenha.getText().length() != 0 &&
                etNascimento.getText().toString().length() != 0 &&
                etEmail.getText().toString().length() != 0;
    }
}
