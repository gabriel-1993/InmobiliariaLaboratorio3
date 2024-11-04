package com.ulpsoft.inmobiliarialaboratorio3.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.MainActivity;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.ActivityLoginBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.ActivityMainBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentPerfilBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.NavHeaderMainBinding;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel  extends AndroidViewModel {

    private Context context;



    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }


    public void llamarLogin(String usuario, String clave){

        // Validar si los campos están vacíos
        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(context, "Datos vacíos, ingrese Correo y Clave.", Toast.LENGTH_LONG).show();
            return;
        }

        // Validar que el usuario tenga formato de email valido
        if (!Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
            Toast.makeText(context, "El formato del correo no es válido.", Toast.LENGTH_LONG).show();
            return;
        }

        Call<String> llamada = ApiClient.getApiInmobiliaria().login(usuario, clave);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String token = response.body();
                if(response.isSuccessful() && token != null){
                    ApiClient.Guardar(context, token);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error en conexion", Toast.LENGTH_LONG).show();
            }
        });


    };


    public void llamarRestablecerClave(String email){

        // Validar campo vacío
        if (email.isEmpty()) {
            Toast.makeText(context, "Por favor, ingrese el email que desea restablecer.", Toast.LENGTH_LONG).show();
            return;
        }

        // Validar que el usuario tenga formato de email valido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "El formato del correo no es válido.", Toast.LENGTH_LONG).show();
            return;
        }

        Call<String> llamada = ApiClient.getApiInmobiliaria().restablecerClave(email);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String token = response.body();
                if(response.isSuccessful() && token != null){
                    Toast.makeText(context, "Email de restablecimiento enviado con exito, revise su correo electronico.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Error: el correo no esta registrado.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error en conexion", Toast.LENGTH_LONG).show();
            }
        });

    }

}
