package com.ulpsoft.inmobiliarialaboratorio3.ui.cerrarsesion;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.R;

public class CerrarSesionFragment extends Fragment {


    public static CerrarSesionFragment newInstance() {
        return new CerrarSesionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        new AlertDialog.Builder(getContext())
                .setTitle("Salir")
                .setMessage("¿Estás seguro de querer salir de la app?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* System.exit(0); // salida forzada (no es buena practica) */
                        /* getActivity().finishAffinity(); // cierra todas las activities */
                        getActivity().finish(); // cierra la activity actual
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();

        return inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);    }

}