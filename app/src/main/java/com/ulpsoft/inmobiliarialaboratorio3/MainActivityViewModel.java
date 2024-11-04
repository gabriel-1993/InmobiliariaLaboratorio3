package com.ulpsoft.inmobiliarialaboratorio3;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliarialaboratorio3.model.Propietario;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityViewModel extends AndroidViewModel {

    Context context;
    private MutableLiveData<Propietario> mPropietario;



    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public MutableLiveData<Propietario> getmPropietario(){
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<Propietario>();
        }
        return mPropietario;
    }





    /*    recuperar datos con el id del token */
    public void recuperarDatosPropietarioToken(){

        Call<Propietario> propietarioCall = ApiClient.getApiInmobiliaria().GetPerfilPropietario("Bearer "+ApiClient.Leer(context));

        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    Propietario propietario = response.body();
                  //  Log.d("SALiDA", response.body().toString());
                    //setear en mutable de mainactivity
                    mPropietario.postValue(propietario);
                }else{
                    Log.d("SALiDA", response.message());
                    Toast.makeText(context, "onResponse: Error de respuesta API getPerfilPropietario()", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(context, "onFaailure: Error de respuesta API getPerfilPropietario()", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
