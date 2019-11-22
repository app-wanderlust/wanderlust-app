package br.ufrpe.wanderlustapp.usuario.gui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pessoa.dominio.Pessoa;
import br.ufrpe.wanderlustapp.pessoa.negocio.PessoaServices;
import br.ufrpe.wanderlustapp.usuario.negocio.UsuarioServices;
import br.ufrpe.wanderlustapp.usuario.persistencia.UsuarioDAO;

public class AlteraPerfilActivity extends AppCompatActivity {
    private EditText etNome;
    private EditText etEmail;
    private EditText etNascimento;
    private String nome;
    private String email;
    private String nascimento;
    UsuarioServices usuarioServices = new UsuarioServices(this);
    PessoaServices pessoaServices = new PessoaServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_perfil);
    }

    public void findEditTexts() {

        etNome = findViewById(R.id.edit_username);
        etEmail = findViewById(R.id.edit_email);
        etNascimento = findViewById(R.id.edit_nascimento);
    }

    public void capturaTextos() {
        findEditTexts();
        nome = etNome.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        nascimento = etNascimento.getText().toString().trim();
    }


}
