package com.ulpsoft.inmobiliarialaboratorio3.ui.perfil;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarClaveViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMsjUsuario;

    public ModificarClaveViewModel(Application application) {
        super(application);
        mMsjUsuario = new MutableLiveData<>();
    }

    public MutableLiveData<String> getmMsjUsuario(){
        if (mMsjUsuario == null) {
            mMsjUsuario = new MutableLiveData<String>();
        }
        return mMsjUsuario;
    }

    public void cambiarClave(String token, String claveActual, String nuevaClave, String repetirClave) {

        // Dato o contraseña vacía
        if (claveActual.isEmpty() || nuevaClave.isEmpty() || repetirClave.isEmpty()) {
            mMsjUsuario.postValue("Debe ingresar las tres contraseñas.");
            return;
        }

        // Validar longitud de la nueva clave (mínimo 3, máximo 10)
        if (nuevaClave.length() < 3 || nuevaClave.length() > 10) {
            mMsjUsuario.postValue("La nueva contraseña debe tener entre 3 y 10 caracteres.");
            return;
        }

        // Validar que la nueva clave y confirmar nueva sean iguales
        if (!nuevaClave.equals(repetirClave)) {
            // Las contraseñas no coinciden
            mMsjUsuario.postValue("Las contraseñas nuevas no coinciden.");
            return;
        }

            // Sin cambio en clave nueva: 3 iguales claveActualIngresada
            if (nuevaClave.equals(claveActual) && repetirClave.equals(claveActual)) {
                /* No especificar info de la clave actual si es correcta o no */
                mMsjUsuario.postValue("Datos incorrectos.");
                return;
            }


            // Llamada a la API para cambiar la contraseña
            Call<String> call = ApiClient.getApiInmobiliaria().cambiarContraseña("Bearer " + token, claveActual, nuevaClave, repetirClave);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        // Contraseña cambiada correctamente
                        mMsjUsuario.postValue("Contraseña cambiada correctamente.");
                    } else {
                        // Error en la respuesta
                        //resultMessage.postValue("Error al cambiar la contraseña: " + response.message());
                        mMsjUsuario.postValue("Contraseña actual incorrecta.");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    // Error en la solicitud
                    mMsjUsuario.postValue("Error de conexión: " + t.getMessage());
                }
            });

    }




}
