package com.ulpsoft.inmobiliarialaboratorio3;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ulpsoft.inmobiliarialaboratorio3.databinding.ActivityMainBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Propietario;
import com.ulpsoft.inmobiliarialaboratorio3.ui.perfil.PerfilViewModel;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configuración del AppBar y el NavController
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_inmueble, R.id.nav_inquilinos, R.id.nav_contratos, R.id.nav_cerrarsesion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Observador para los datos del propietario
        vm.getmPropietario().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                View headerView = navigationView.getHeaderView(0);
                ImageView foto = headerView.findViewById(R.id.ivFotoPerfil);
                TextView saludo = headerView.findViewById(R.id.tvUsuarioPerfil);

                String saludoString = "¡Bienvenido, " + propietario.getNombre() + " " + propietario.getApellido() + "!";
                saludo.setText(saludoString);

                // Carga la imagen del propietario
                Glide.with(MainActivity.this)
                        .load("http://192.168.100.3:5290" + propietario.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.sinfoto)
                        .error(R.drawable.sinfoto)
                        .circleCrop()
                        .into(foto);
            }
        });

        vm.recuperarDatosPropietarioToken();

        // Listener para cuando se abre el menú
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                vm.recuperarDatosPropietarioToken(); // Llama a leerPropietario cuando el menú se abre
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.recuperarDatosPropietarioToken(); // Actualiza los datos cuando la actividad vuelve a estar visible
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
