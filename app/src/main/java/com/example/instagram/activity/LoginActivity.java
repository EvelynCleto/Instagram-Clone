package com.example.instagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instagram.helper.ConfiguracaoFirebase;
import com.example.instagram.model.Usuario;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import instagram.R;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button buttonEntrar;
    private ProgressBar progressBar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificarUsuarioLogado();
        inicializarCamponentes();

        //Cadastrar usuario
        progressBar.setVisibility(View.GONE);
       buttonEntrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //recuperar os valores
               String textoEmail = campoEmail.getText().toString();
               String textoSenha = campoSenha.getText().toString();

               if (!textoEmail.isEmpty()){

                   if (!textoSenha.isEmpty()){

                       Usuario usuario = new Usuario();
                       usuario.setEmail(textoEmail);
                       usuario.setSenha(textoSenha);
                       validarLogin(usuario);

                   }else {
                       Toast.makeText(LoginActivity.this,
                               "Preencha a senha!",
                               Toast.LENGTH_SHORT).show();
                   }

               }else {
                   Toast.makeText(LoginActivity.this,
                           "Preencha o e-mail!",
                           Toast.LENGTH_SHORT).show();
               }
           }
       });

    }

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void validarLogin(Usuario usuario){
        progressBar.setVisibility(View.VISIBLE);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,
                            "Erro ao fazer login",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void inicializarCamponentes(){
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
        buttonEntrar = findViewById(R.id.buttonEntrar);
        progressBar = findViewById(R.id.progressLogin);

        campoEmail.requestFocus();
    }

    public void abrirCadastro(View view){
        startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
    }

}