package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.negocio.CidadeServices;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.negocio.PaisServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CODIGO_RESULTADO_PRATO_CRIADO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.POSICAO_INVALIDA;


public class FormularioPratosAcitivity extends AppCompatActivity {
    public static final String TITULO_APPBAR_INSERE = "Inserir prato";
    public static final String TITULO_APPBAR_ALTERA = "Alterar prato";
    private ImageView imagem;
    private final int GALERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;
    private final int TIRAR_FOTO = 3;
    private int posicaoRecebida;
    CidadeServices cidadeServices = new CidadeServices(this);
    PaisServices paisServices = new PaisServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pratos);
        setTitle(TITULO_APPBAR_INSERE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        imagem = findViewById(R.id.imageView3);
        Button galeria = findViewById(R.id.button);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_IMAGENS);
            }
        });

        Button foto = findViewById(R.id.button2);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent, TIRAR_FOTO);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            imagem.setImageBitmap(thumbnail);
        }
        if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagem.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// A permissão foi concedida. Pode continuar
            } else {
// A permissão foi negada. Precisa ver o que deve ser desabilitado
            }
            return;
        }
    }


    private void recebePrato(Intent dadosRecebidos) {
        PratoTipico pratoRecebido = (PratoTipico) dadosRecebidos.getSerializableExtra(CHAVE_PRATO);
        posicaoRecebida = dadosRecebidos.getIntExtra("posicao", POSICAO_INVALIDA);
        TextView nome = findViewById(R.id.formulario_prato_nome);
        TextView descricao = findViewById(R.id.formulario_prato_descricao);
        nome.setText(pratoRecebido.getNome());
        descricao.setText(pratoRecebido.getDescricao());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_prato_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_prato_ic_salva){
            Intent dadosRecebidos = getIntent();
            PratoTipico pratoRecebido = (PratoTipico) dadosRecebidos.getSerializableExtra(CHAVE_PRATO);
            fetchPratoTipico(pratoRecebido);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchPratoTipico(PratoTipico pratoRecebido) {
        if (verficaCampos()) {
            if (pratoRecebido == null) {
                pratoRecebido = criaPratoTipico();
            } else {
                pratoRecebido = atualizaPrato(pratoRecebido);
            }
            retornaPratoViaExtra(pratoRecebido);
        }
    }

    private PratoTipico criaPratoTipico() {
        PratoTipico pratoTipico = new PratoTipico();
        preencheAtributosPrato(pratoTipico);
        return pratoTipico;
    }

    private PratoTipico atualizaPrato(PratoTipico pratoTipico) {
        preencheAtributosPrato(pratoTipico);
        return pratoTipico;
    }

    private boolean verficaCampos(){
        EditText nome = findViewById(R.id.formulario_prato_nome);
        EditText descricao = findViewById(R.id.formulario_prato_descricao);
        return nome.length() > 0 && descricao.length() > 0;
    }

    private void preencheAtributosPrato(PratoTipico pratoTipico) {
        EditText nome = findViewById(R.id.formulario_prato_nome);
        EditText descricao = findViewById(R.id.formulario_prato_descricao);
        pratoTipico.setNome(nome.getText().toString());
        pratoTipico.setDescricao(descricao.getText().toString());
        pratoTipico.setCidade(createCidadePadrao());
    }

    private void retornaPratoViaExtra(PratoTipico pratoTipico) {
        Intent resultadoInsercao = new Intent();
        TextView novoNome = findViewById(R.id.formulario_prato_nome);
        TextView novoDescricao = findViewById(R.id.formulario_prato_descricao);
        pratoTipico.setNome(novoNome.getText().toString());
        pratoTipico.setDescricao(novoDescricao.getText().toString());
        resultadoInsercao.putExtra(CHAVE_PRATO,pratoTipico);
        resultadoInsercao.putExtra("posicao", posicaoRecebida);
        setResult(CODIGO_RESULTADO_PRATO_CRIADO,resultadoInsercao);
    }

    private Cidade createCidadePadrao() {
        Pais pais = new Pais();
        pais.setNome("Brasil");
        paisServices.cadastrar(pais);
        Cidade cidade = new Cidade();
        cidade.setNome("Recife");
        cidade.setPais(pais);
        cidadeServices.cadastrar(cidade);
        return cidade;
    }
}
