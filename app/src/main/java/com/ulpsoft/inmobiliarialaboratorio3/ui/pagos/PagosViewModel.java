package com.ulpsoft.inmobiliarialaboratorio3.ui.pagos;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.Pago;

import java.util.List;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> mPagos;

    Context context;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<Pago>> getmPagos() {
        if(mPagos == null){
            mPagos = new MutableLiveData<>();
        }
        return mPagos;
    }

    public void recuperarPagos(Contrato contrato) {
        mPagos.setValue(contrato.getPagos());
    }





}