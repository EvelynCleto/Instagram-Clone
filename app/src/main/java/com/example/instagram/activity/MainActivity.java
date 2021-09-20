package com.example.instagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.instagram.fragment.FeedFragment;
import com.example.instagram.fragment.PerfilFragment;
import com.example.instagram.fragment.PesquisaFragment;
import com.example.instagram.fragment.PostagemFragment;
import com.example.instagram.helper.ConfiguracaoFirebase;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import instagram.R;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Instagram");
        setSupportActionBar(toolbar);

        //configuracoes d eobjetos
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Configurar bottom navigation view
        configuraBottomNavigationView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();
    }

    private void configuraBottomNavigationView(){

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        //faz configurações inicias do Bottom NavigationView
        bottomNavigationView.setAnimationCacheEnabled(false);



        //Habilitar navegação
        habilitarNavegacao(bottomNavigationView);

        //Configura item selecionado inicialmente
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);


    }

    /**
     * Método responsável por tratar eventos d eclick na BottomNavigation
     * @param viewEx
     */
    private void habilitarNavegacao(BottomNavigationView viewEx){

      viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {

              FragmentManager fragmentManager = getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

              switch (item.getItemId()){
                  case R.id.ic_home:
                      fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();
                      return true;

                  case R.id.ic_pesquisa:
                      fragmentTransaction.replace(R.id.viewPager, new PesquisaFragment()).commit();
                      return true;

                  case R.id.ic_postagem:
                      fragmentTransaction.replace(R.id.viewPager, new PostagemFragment()).commit();
                      return true;

                  case R.id.ic_perfil:
                      fragmentTransaction.replace(R.id.viewPager, new PerfilFragment()).commit();
                      return true;
              }
              return false;
          }
      });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_Sair:
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}