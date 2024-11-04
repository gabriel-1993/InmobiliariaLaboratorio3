package com.ulpsoft.inmobiliarialaboratorio3.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ulpsoft.inmobiliarialaboratorio3.MainActivity;
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.ActivityLoginBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel vm;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);



        // Asignar el evento de clic al botón de iniciar sesión
        binding.btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            vm.llamarLogin(binding.etCorreoLogin.getText().toString(), binding.edPasswordLogin.getText().toString());

                //Limpiar pass de login anterior
                binding.edPasswordLogin.setText("");
            }
        });



        binding.btnRestablecerPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            vm.llamarRestablecerClave(binding.etCorreoLogin.getText().toString());

            }
        });

    }
}