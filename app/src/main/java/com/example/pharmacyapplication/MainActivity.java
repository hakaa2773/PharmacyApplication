package com.example.pharmacyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView nav;
    private FragmentManager frag_man;
    private FragmentTransaction frag_tra;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private String  mail;

//    Intent intent = getIntent();
//    String mail = intent.getStringExtra("email");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        mail = intent.getStringExtra("email");
        System.out.println(mail);

        nav = findViewById(R.id.navview);
        drawerLayout = findViewById(R.id.navdrawer);
        nav.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        loadFragment(new HomeFragment());
        nav.setNavigationItemSelectedListener(this);

        Toast.makeText(this, mail, Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                loadFragment(new HomeFragment());

                break;
            case R.id.nav_search:

                //loadFragment(new SearchFragment());
                startActivity(new Intent(MainActivity.this, SearchDrugActivity.class));

                break;

            case R.id.nav_add_prescription:

                startActivity(new Intent(MainActivity.this, PrescreptionActivity.class));

                break;

            case R.id.nav_profile:

                   loadFragment(new ProfileFragment(mail));
                break;
                }



        return false;
    }
    private void loadFragment(Fragment fragment) {
        frag_man = getSupportFragmentManager();
        frag_tra = frag_man.beginTransaction();
        frag_tra.replace(R.id.frag_container,fragment);
        frag_tra.commit();
    }
}