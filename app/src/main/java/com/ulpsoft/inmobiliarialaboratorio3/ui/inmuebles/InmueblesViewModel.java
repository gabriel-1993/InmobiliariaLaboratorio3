/*
package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Propietario;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmuebles;

    private Context context;


    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public MutableLiveData<Inmueble> getmInmuebles(){
        if (mInmuebles == null) {
            mInmuebles = new MutableLiveData<Inmueble>();
        }
        return mInmuebles;
    }

        recuperar datos con el id del token
    public void recuperarDatosPropietarioToken(){

        Call<List<Inmueble>> inmueblesCall = ApiClient.getApiInmobiliaria().getMisInmuebles("Bearer " + ApiClient.Leer(context));

        inmueblesCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Inmueble> inmuebles = response.body();
                    // Procesa la lista de inmuebles aquí, por ejemplo:
                    Log.d("SALIDA", "Inmuebles recibidos: " + inmuebles.size());
                    for (Inmueble inmueble : inmuebles) {
                        Log.d("Inmueble", "Dirección: " + inmueble.getDireccion());
                    }
                    // Si necesitas hacer algo específico con los datos, puedes actualizar una variable o enviar a LiveData, etc.
                } else {
                    Log.d("SALIDA", "Error en la respuesta: " + response.message());
                    Toast.makeText(context, "Error de respuesta API GetMisInmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("SALIDA", "Error de conexión: " + throwable.getMessage());
                Toast.makeText(context, "Error de respuesta API GetMisInmuebles", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

*/

package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.MainActivity;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Propietario;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> mInmuebles;

    private MutableLiveData<Boolean> mCrearBool;

    private Context context;

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getmInmuebles() {
        if (mInmuebles == null) {
            mInmuebles = new MutableLiveData<>();
        }
        return mInmuebles;
    }

    public MutableLiveData<Boolean> getmCrearBool() {
        if (mCrearBool == null) {
            mCrearBool = new MutableLiveData<>();
        }
        return mCrearBool;
    }

    public void llamarListadoMisInmuebles() {
        String stringToken = "Bearer " + ApiClient.Leer(context);
        //Log.d("Token", "Token enviado: " + stringToken); // Verifica el token

        Call<List<Inmueble>> inmueblesCall = ApiClient.getApiInmobiliaria().getMisInmuebles(stringToken);

        inmueblesCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
               Log.d("SALIDA", "Código de estado: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    //postValue para setear resultado de un llamado async
                    mInmuebles.postValue(response.body());
                    Toast.makeText(context, "Seleccione un item para consultar Detalles.", Toast.LENGTH_LONG).show();

                } else {
                    Log.d("SALIDA", "Error en la respuesta: " + response.message());
                    // Manejo específico de errores
                    if (response.code() == 401) {
                        Toast.makeText(context, "No autorizado. Verifica tu token.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error de respuesta API: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("SALIDA", "Error de conexión: " + throwable.getMessage());
                Toast.makeText(context, "Error de respuesta API GetMisInmuebles", Toast.LENGTH_SHORT).show();
            }
        });
    }



}


