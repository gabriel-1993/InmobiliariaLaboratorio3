package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetallesViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble;

    private Context context;


    public InmuebleDetallesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getmInmueble() {
        if(mInmueble == null){
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }


    public void recuperarInmueble(Bundle b){
        //Inmueble inmueble = (Inmueble) bundle.getSerializable("Inmueble", Inmueble.class);

        Inmueble inmueble = (Inmueble) b.getSerializable("inmueble");

        if(inmueble != null){
            Log.d("42 inmueble no es null ", inmueble.toString() );
            mInmueble.setValue(inmueble);
        }
    }

    public void publicarInmuebleOnOff(boolean b) {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        // Verifica que mInmueble tenga un valor antes de acceder a él
        if (mInmueble.getValue() != null) {
            int id = mInmueble.getValue().getIdInmueble();
            String token = "Bearer " + ApiClient.Leer(context);

            // llamada a la API, si el estado del switch es distinto al del inmueble
            if (mInmueble.getValue().isDisponible() != b) {
                Call<String> call = api.publicarOnOff(id, token);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            //  respuesta exitosa
                            String resultado = response.body();
                            //Toast.makeText(context, "Inmueble " + (b ? "publicado" : "despublicado") + " con éxito: " + resultado, Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Inmueble " + (b ? "publicado" : "despublicado") + " con éxito! ", Toast.LENGTH_SHORT).show();

                            //crear inmueble con nueva disponibilidad y setearlo al mutable
                            Inmueble inmuebleActualizado = mInmueble.getValue();
                            inmuebleActualizado.setDisponible(b);
                            mInmueble.setValue(inmuebleActualizado);

                        } else {
                            // Maneja el error de la respuesta (por ejemplo, errores 4xx o 5xx)
                            Toast.makeText(context, "Error al publicar inmueble: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // Maneja el error de la llamada (por ejemplo, problemas de red)
                        Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            // Maneja el caso donde mInmueble es nulo
            Toast.makeText(context, "No se encontró el inmueble.", Toast.LENGTH_SHORT).show();
        }
    }



}