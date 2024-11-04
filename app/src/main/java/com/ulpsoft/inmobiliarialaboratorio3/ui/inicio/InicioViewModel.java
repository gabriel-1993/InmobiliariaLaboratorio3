package com.ulpsoft.inmobiliarialaboratorio3.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<Mapa> mMap;

    public InicioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Mapa> getMMap(){
        if(mMap == null){
            mMap = new MutableLiveData<>();
        }
        return mMap;
    }

    public void obtenerMapa(){
        Mapa m = new Mapa();
        mMap.setValue(m);
    }




    public class Mapa implements OnMapReadyCallback {
        LatLng inmobiliaria = new LatLng(-33.184613, -66.312385);
        private final float ZOOM_LEVEL = 15.0f;
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(inmobiliaria).title("Inmobiliaria LP").snippet("lun a sab 8hs a 15hs"));

            // Mover la cámara a la ubicación de la inmobiliaria con un nivel de zoom
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inmobiliaria, ZOOM_LEVEL));
        }
    }


}