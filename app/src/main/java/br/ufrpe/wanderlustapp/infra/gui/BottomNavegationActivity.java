package br.ufrpe.wanderlustapp.infra.gui;


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
import br.ufrpe.wanderlustapp.usuario.gui.HomeActivity;
import br.ufrpe.wanderlustapp.usuario.gui.HomeFragment;
import br.ufrpe.wanderlustapp.usuario.gui.PerfilFragment;

public class BottomNavegationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navegation);
        this.bottomNavigationView = findViewById(R.id.bottomNavigationView);

        mudarFragment(HomeFragment.newInstance());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        mudarFragment(HomeFragment.newInstance());
                        return true;
                    case R.id.nav_person:
                        mudarFragment(PerfilFragment.newInstance());
                        return true;
                    case R.id.nav_business:
                        mudarFragment(ContatoUtilFragment.newInstance());
                        return true;
                    case R.id.nav_chat:
                        mudarFragment(ChatBotFragment.newInstance());
                        return true;
                    case R.id.nav_add:
                        mudarFragment(CadastrarPratosFragment.newInstance());
                        return true;
            }
                return false;
            }
        });
    }

    private void mudarFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}

