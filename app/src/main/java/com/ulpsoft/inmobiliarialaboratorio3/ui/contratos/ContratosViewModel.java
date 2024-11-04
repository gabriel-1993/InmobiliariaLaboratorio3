package com.ulpsoft.inmobiliarialaboratorio3.ui.contratos;

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

import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;
import com.ulpsoft.inmobiliarialaboratorio3.model.Pago;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> mInmueblesLista;

    private MutableLiveData<Inquilino> mInquilino;

    Context context;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<Inmueble>> getmInmueblesLista() {
      if (mInmueblesLista == null){
          mInmueblesLista = new MutableLiveData<>();
      }
        return mInmueblesLista;
    }

    public MutableLiveData<Inquilino> getmInquilino() {
        if(mInquilino==null){
            mInquilino = new MutableLiveData<>();
        }
        return mInquilino;
    }



    //trae una lista de inmuebles con contrato y pagos
    public void llamarContratosPagos() {
        String stringToken = "Bearer " + ApiClient.Leer(context);

        // Llamado actualizado al endpoint correcto
        Call<List<Inmueble>> inmueblesCall = ApiClient.getApiInmobiliaria().contratoEnCursoInmuebleInquilinoPagos(stringToken);

        inmueblesCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                Log.d("SALIDA", "Código de estado: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    // Procesar la lista de inmuebles y sus contratos en curso
                    List<Inmueble> inmuebles = response.body();

                    for (Inmueble inmueble : inmuebles) {
                        List<Contrato> contratos = inmueble.getContratos();
                        for (Contrato contrato : contratos) {

                            // Datos del inquilino
                            Inquilino inquilino = contrato.getInquilino();

                            // Procesar los pagos del contrato
                            List<Pago> pagos = contrato.getPagos();
                        }
                    }

                    Toast.makeText(context, "Seleccione un item para consultar detalles.", Toast.LENGTH_LONG).show();
                    // Postea la lista de inmuebles (o contratos) si es necesario
                    mInmueblesLista.postValue(inmuebles);
                } else {
                    Log.d("SALIDA", "Error en la respuesta: " + response.message());
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
                Toast.makeText(context, "Error de respuesta API obtenerInquilinosConContratoEnCurso()", Toast.LENGTH_SHORT).show();
            }
        });
    }





}