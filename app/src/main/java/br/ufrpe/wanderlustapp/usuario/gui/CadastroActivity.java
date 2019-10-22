package br.ufrpe.wanderlustapp.usuario.gui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
    private Toast toast = null;
    UsuarioServices usuarioServices = new UsuarioServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txVoltar = findViewById(R.id.textoVoltarId);
        etNome = findViewById(R.id.campoNomeCadastroId);
        etEmail = findViewById(R.id.campoDescricaoDoPrato);
        etSenha = findViewById(R.id.campoSenhaCadastroId);
        etConfirmarSenha = findViewById(R.id.campoConfirmarSenhaCadastroId);
        etNascimento = findViewById(R.id.campoDataNascimentoCadastroId);
        etNascimento.addTextChangedListener(new MaskWatcher("##/##/####"));
        btnCadastrar = findViewById(R.id.botaoCadastrarId);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarCadastro();
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

    private void cadastrar(Usuario usuario) throws Exception {
        usuarioServices.cadastrar(usuario);
        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
        Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
    }

    private void tentarCadastro(){
        if (validarCampos()){
            Usuario usuario = createUsuario();
            try{
                cadastrar(usuario);
            }catch (Exception e){
                Toast.makeText(CadastroActivity.this, "Esse login já existe", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean senhasIguais() {
        boolean resultado = (etSenha.getText().toString().equals(etConfirmarSenha.getText().toString()));
        if (!resultado){
            etSenha.setError("Campo Inválido");
            etConfirmarSenha.setError("Campo Inválido");
        }
        return resultado;
    }

    private Usuario createUsuario() {
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

    private boolean validarCampos(){
        boolean resultado = validar(etNome,etSenha,etConfirmarSenha,etEmail,etNascimento);
        if (resultado){
            if (!isEmailValido()){
                resultado = false;
            }
        }   if (!senhasIguais()){
            resultado = false;
        }
        return resultado;
    }


    private boolean isEmailValido(){
        boolean resultado = ((validar(etEmail)) & Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches());
        if (!resultado){
            etEmail.setError("Email Inválido");
        }
        return resultado;
    }

    private boolean validar(EditText...args){
        boolean resultado = true;
        for (EditText editText:args ) {
            if (TextUtils.isEmpty((editText.getText().toString()))){
                resultado = false;
                editText.setError("Campo Inválido");
            }

        }
        return  resultado;
    }
}
