package com.ulpsoft.inmobiliarialaboratorio3.ui.inicio;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.ulpsoft.inmobiliarialaboratorio3.R;

public class InicioFragment extends Fragment {

    private InicioViewModel vm;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        vm.getMMap().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.Mapa>() {
            @Override
            public void onChanged(InicioViewModel.Mapa mapa) {
                ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapa);
            //((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapa);
            }
        });
        vm.obtenerMapa();
        return inflater.inflate(R.layout.fragment_inicio, container, false);    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        // TODO: Use the ViewModel
    }

}