package com.example.instagram.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Postagem;
import com.example.instagram.model.Usuario;


import instagram.R;

public class VisualizarPostagemActivity extends AppCompatActivity {

    private ImageView imagePerfilPostagem;
    private TextView textPerfilPostagem;
    private ImageView imagePostagemSelecionada;

    private TextView textQtdCurtidasPostagem;
    private TextView textDescricaoPostagem;
    private TextView textVisualizarComentariosPostagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_postagem);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Visualizar postagem");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        //Inicializar componentes
        inicializarComponentes();

        //Recupera dados da activity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            Postagem postagem = (Postagem) bundle.getSerializable("postagem");
            Usuario usuario = (Usuario) bundle.getSerializable("usuario") ;

            //Exibe dados de usuário
            Uri uri = Uri.parse(usuario.getCaminhoFoto());
            Glide.with(VisualizarPostagemActivity.this)
            .load(uri)
            .into(imagePerfilPostagem);
            textPerfilPostagem.setText(usuario.getNome());

            //Exibe dados da postagem
            Uri uriPostagem = Uri.parse(postagem.getCaminhoFoto());
            Glide.with(VisualizarPostagemActivity.this)
                    .load(uriPostagem)
                    .into(imagePostagemSelecionada);

            textDescricaoPostagem.setText(postagem.getDescricao());
        }
    }

    public void inicializarComponentes(){
        imagePerfilPostagem = findViewById(R.id.imagePerfilPostagem);
        textPerfilPostagem = findViewById(R.id.textPerfilPostagem);
        imagePostagemSelecionada = findViewById(R.id.imagePostagemSelecionada);

        textQtdCurtidasPostagem = findViewById(R.id.textQtdCurtidasPostagem);
        textDescricaoPostagem = findViewById(R.id.textDescricaoPostagem);


    }

    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}