package com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;

public class InquilinoDetallesViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> mInquilino;

    private Context context;

    public InquilinoDetallesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public LiveData<Inquilino> getmInquilino() {
        if(mInquilino == null){
            mInquilino = new MutableLiveData<>();
        }
        return mInquilino;
    }

    public void recuperarInquilino(Bundle b){

        Inquilino inquilino = (Inquilino) b.getSerializable("inquilino");

        if(inquilino != null){
            Log.d("42 inmueble no es null ", inquilino.toString() );
            mInquilino.setValue(inquilino);
        }
    }



}