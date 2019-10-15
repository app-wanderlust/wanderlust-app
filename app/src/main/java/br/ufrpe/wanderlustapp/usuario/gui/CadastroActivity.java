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
        etEmail = findViewById(R.id.campoEmailCadastroId);
        etSenha = findViewById(R.id.campoSenhaCadastroId);
        etConfirmarSenha = findViewById(R.id.campoConfirmarSenhaCadastroId);
        etNascimento = findViewById(R.id.campoDataNascimentoCadastroId);
        etNascimento.addTextChangedListener(new MaskWatcher("##/##/####"));
        Button btnCadastrar = findViewById(R.id.botaoCadastrarId);

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
        }else{
            Toast.makeText(CadastroActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
        }

    private boolean senhasIguais(String senha, String confimarsenha) {
        boolean resultado = (etSenha.getText().toString().equals(etConfirmarSenha.getText().toString()));
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

    private boolean validarCampos() {
        boolean resultado = false;
        boolean nome1 = false;
        boolean senha1 = false;
        boolean confirmarsenha1= false;
        boolean email1 = false;
        boolean nascimento1 = false;
        boolean senhasiguais1 = false;
        String nome = etNome.getText().toString();
        String senha = etSenha.getText().toString();
        String confimarsenha = etConfirmarSenha.getText().toString();
        String email = etEmail.getText().toString();
        String nascimento = etNascimento.getText().toString();

        if (!isCampoVazio(nome)){
            nome1 = true;
        }else{
            etNome.setError("Informe o nome");
            etNome.requestFocus();
        }

        if (!isCampoVazio(senha)){
            senha1 = true;
        }else{
            etSenha.setError("Informe uma senha");
            etSenha.requestFocus();
        }
        if (!isCampoVazio(confimarsenha)){
            confirmarsenha1 = true;
        }else{
            etConfirmarSenha.setError("Por favor, confirme a senha");
            etConfirmarSenha.requestFocus();
        }
        if (!isCampoVazio(email)){
            email1 = true;
        }else{
            etEmail.setError("E-mail deve estar preenchido");
            etEmail.requestFocus();
        }
         if (!isCampoVazio(nascimento)){
            nascimento1 = true;
        }else{
            etNascimento.setError("Informe a data de nascimento");
            etNascimento.requestFocus();
        }
        if (isEmail(email)){
            email1 = true;
        }else{
            email1 = false;
            etEmail.setError("Este e-mail é inválido");
            etEmail.requestFocus();
        }
        if (senhasIguais(senha,confimarsenha)){
            senhasiguais1 = true;
        }else{
            etSenha.setError("As senhas devem ser iguais");
            etSenha.requestFocus();
        }
        if (isCampoVazio(nome) |  (isCampoVazio(email)) | (isCampoVazio(nascimento)) | (isCampoVazio(senha)) | (isCampoVazio(confimarsenha))){
            resultado = false;
        }
        if (nome1 & senha1 & confirmarsenha1 & email1 & nascimento1 & senhasiguais1){
            resultado = true;
        }
        return resultado;
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
