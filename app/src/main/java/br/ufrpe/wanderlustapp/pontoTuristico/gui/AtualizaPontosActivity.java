package br.ufrpe.wanderlustapp.pontoTuristico.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.io.ByteArrayOutputStream;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.cidade.dominio.Cidade;
import br.ufrpe.wanderlustapp.cidade.negocio.CidadeServices;
import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.pais.dominio.Pais;
import br.ufrpe.wanderlustapp.pais.negocio.PaisServices;
import br.ufrpe.wanderlustapp.pontoImagem.dominio.PontoImagem;
import br.ufrpe.wanderlustapp.pontoImagem.negocio.PontoImagemServices;
import br.ufrpe.wanderlustapp.pontoTuristico.dominio.PontoTuristico;
import br.ufrpe.wanderlustapp.pontoTuristico.negocio.PontoTuristicoServices;


import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.CHAVE_PONTO;
import static br.ufrpe.wanderlustapp.pratoTipico.gui.pratosActivityConstantes.POSICAO_INVALIDA;

public class AtualizaPontosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_ALTERA = "Alterar ponto";
    private ImageView imagem;
    private final int GALERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;
    private final int TIRAR_FOTO = 3;
    private int posicaoRecebida;
    private Button galeria;
    private PontoTuristico pontoTuristico;
    CidadeServices cidadeServices = new CidadeServices(this);
    PaisServices paisServices = new PaisServices(this);
    PontoTuristicoServices pontoTuristicoServices = new PontoTuristicoServices(this);
    PontoImagemServices pontoImagemServices = new PontoImagemServices(this);
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_pontos);
        setTitle(TITULO_APPBAR_ALTERA);

        Intent dadosRecebidos = getIntent();
        recebePonto(dadosRecebidos);

        //NÃO MUDEI NADA DESSE IF PARA BAIXO DENTRO DESSE MÉTODO. É PRA MUDAR?
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

    //TBM NÃO MUDEI NADA NESSE MÉTODO. É PRA MUDAR?
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

    //TBM NÃO MUDEI NESSE. PRECISA?
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

    private void recebePonto(Intent dadosRecebidos) {
        pontoTuristico = (PontoTuristico) dadosRecebidos.getSerializableExtra(CHAVE_PONTO);
        posicaoRecebida = dadosRecebidos.getIntExtra("posicao", POSICAO_INVALIDA);
        TextView nome = findViewById(R.id.formulario_ponto_nome);
        TextView descricao = findViewById(R.id.formulario_ponto_descricao);
        nome.setText(pontoTuristico.getNome());
        descricao.setText(pontoTuristico.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_prato_salva, menu); //ver isso
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_formulario_prato_ic_salva){ //ver isso
            if (verficaCampos()){
                preencheAtributosPonto(pontoTuristico);
                Sessao.instance.setPratoTipico(pontoTuristico);
                if (imageBitmap != null){
                    PontoTuristico pontoImagem = createPontoImagem(pontoTuristico, imageBitmap);
                    Sessao.instance.setPontoImagem(pontoImagem);
                }
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private PontoImagem createPontoImagem(PontoTuristico pontoTuristico, Bitmap imagem){
        PontoImagem pontoImagem = new PontoImagem();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imagemBytes[] = stream.toByteArray();
        pontoImagem.setPontoTuristico(pontoTuristico);
        pontoImagem.setImagem(imagemBytes);
        return pontoImagem;
    }

    private boolean verficaCampos(){
        EditText nome = findViewById(R.id.formulario_ponto_nome);
        EditText descricao = findViewById(R.id.formulario_ponto_descricao);
        return nome.length() > 0 && descricao.length() > 0;
    }

    private void preencheAtributosPonto(PontoTuristico pontoTuristico) {
        EditText nome = findViewById(R.id.formulario_ponto_nome);
        EditText descricao = findViewById(R.id.formulario_ponto_descricao);
        pontoTuristico.setNome(nome.getText().toString());
        pontoTuristico.setDescricao(descricao.getText().toString());
        pontoTuristico.setCidade(createCidadePadrao());
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
