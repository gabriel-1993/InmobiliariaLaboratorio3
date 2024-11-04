package com.ulpsoft.inmobiliarialaboratorio3.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;
import com.ulpsoft.inmobiliarialaboratorio3.model.Pago;

import java.util.List;

public class ContratoDetalleViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> mContrato;

    private MutableLiveData<List<Pago>> mPagos;

    Context context;


    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public MutableLiveData<Contrato> getmContrato() {
        if(mContrato==null){
            mContrato = new MutableLiveData<>();
        }
        return mContrato;
    }

    public MutableLiveData<List<Pago>> getmPagos() {
        if(mPagos == null){
            mPagos = new MutableLiveData<>();
        }
        return mPagos;
    }

    public void recuperarContratoConpagos(Bundle b){

        Contrato contrato = (Contrato) b.getSerializable("contrato");

        if(contrato != null){
            mContrato.setValue(contrato);
        }
    }





}