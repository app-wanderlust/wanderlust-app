package br.ufrpe.wanderlustapp.infra.gui;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.contato.gui.ContatoUtilFragment;
import br.ufrpe.wanderlustapp.conversa.gui.ChatBotFragment;
import br.ufrpe.wanderlustapp.pratoTipico.gui.CadastrarPratosFragment;
import br.ufrpe.wanderlustapp.pratoTipico.gui.ListaPratosActivity;
import br.ufrpe.wanderlustapp.usuario.gui.HomeActivity;
import br.ufrpe.wanderlustapp.usuario.gui.HomeFragment;
import br.ufrpe.wanderlustapp.usuario.gui.PerfilActivity;
import br.ufrpe.wanderlustapp.usuario.gui.PerfilFragment;

public class BottomNavegationActivity extends AppCompatActivity {

    //private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navegation);
        //this.bottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        //mudarFragment(HomeFragment.newInstance());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Intent intentHome = new Intent(BottomNavegationActivity.this,HomeActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.nav_person:
                        Intent intentPerson = new Intent(BottomNavegationActivity.this, PerfilActivity.class);
                        startActivity(intentPerson);
                        break;
                    case R.id.nav_business:
                        Intent intentContatos = new Intent(BottomNavegationActivity.this,ContatoUtilFragment.class);
                        startActivity(intentContatos);
                        break;
                    case R.id.nav_chat:
                        break;
                    case R.id.nav_add:
                        Intent intentPratos = new Intent(BottomNavegationActivity.this, ListaPratosActivity.class);
                        startActivity(intentPratos);
                        break;

            }
                return false;
            }
        });
    }

}

