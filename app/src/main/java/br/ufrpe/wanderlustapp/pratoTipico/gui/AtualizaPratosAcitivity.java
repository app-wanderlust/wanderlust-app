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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.negocio.CidadeServices;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.negocio.PaisServices;
import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoImagem.negocio.PratoImagemServices;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.negocio.PratoTipicoServices;

import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PRATO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.POSICAO_INVALIDA;


public class AtualizaPratosAcitivity extends AppCompatActivity {
    public static final String TITULO_APPBAR_ALTERA = "Alterar prato";
    private ImageView imagem;
    private final int GALERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;
    private final int TIRAR_FOTO = 3;
    private int posicaoRecebida;
    private Button galeria;
    private PratoTipico pratoTipico;
    CidadeServices cidadeServices = new CidadeServices(this);
    PaisServices paisServices = new PaisServices(this);
    PratoTipicoServices pratoTipicoServices = new PratoTipicoServices(this);
    PratoImagemServices pratoImagemServices = new PratoImagemServices(this);
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_pratos);
        setTitle(TITULO_APPBAR_ALTERA);

        Intent dadosRecebidos = getIntent();
        recebePrato(dadosRecebidos);

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
        galeria = findViewById(R.id.button);
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
            imageBitmap = (BitmapFactory.decodeFile(picturePath));
            imagem.setImageBitmap(imageBitmap);
        }
        if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imagem.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            return;
        }
    }


    private void recebePrato(Intent dadosRecebidos) {
        pratoTipico = (PratoTipico) dadosRecebidos.getSerializableExtra(CHAVE_PRATO);
        posicaoRecebida = dadosRecebidos.getIntExtra("posicao", POSICAO_INVALIDA);
        TextView nome = findViewById(R.id.formulario_prato_nome);
        TextView descricao = findViewById(R.id.formulario_prato_descricao);
        nome.setText(pratoTipico.getNome());
        descricao.setText(pratoTipico.getDescricao());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_prato_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_prato_ic_salva){
            if (verficaCampos()){
                preencheAtributosPrato(pratoTipico);
                Sessao.instance.setPratoTipico(pratoTipico);
                Sessao.instance.setPratoImagem(createPratoImagem(pratoTipico, imageBitmap));
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private PratoImagem createPratoImagem(PratoTipico pratoTipico, Bitmap imagem){
        PratoImagem pratoImagem = new PratoImagem();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imagemBytes[] = stream.toByteArray();
        pratoImagem.setPratoTipico(pratoTipico);
        pratoImagem.setImagem(imagemBytes);
        return pratoImagem;
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
