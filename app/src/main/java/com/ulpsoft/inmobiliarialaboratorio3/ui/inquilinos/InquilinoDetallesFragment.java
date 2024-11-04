package com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInmuebleDetallesBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInquilinoDetallesBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInquilinosBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;
import com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles.InmuebleDetallesViewModel;

public class InquilinoDetallesFragment extends Fragment {

    private InquilinoDetallesViewModel mViewModel;
    private FragmentInquilinoDetallesBinding binding;

    public static InquilinoDetallesFragment newInstance() {
        return new InquilinoDetallesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentInquilinoDetallesBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(InquilinoDetallesViewModel.class);


        mViewModel.getmInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.etNombreInqDetalles.setText("Nombre: "+inquilino.getNombre());
                binding.etApellidoInqDetalles.setText("Apellido: "+inquilino.getApellido());
                binding.etTelefonoInqDetalles.setText("Tel: "+inquilino.getTelefono());
                binding.etDniInqDetalles.setText("Dni: "+inquilino.getDni()+"");
                binding.etNombreGaranteInqDetalles.setText("Garante-Nombre: "+inquilino.getNombreGarante());
                binding.etApellidoGaranteInqDetalles.setText("Garante-Apellido: "+inquilino.getApellidoGarante());
                binding.etTelGaranteInqDetalles.setText("Garante-Telefono: "+inquilino.getTelefonoGarante());
            }
        });

        //recuperar el inquilino recibido en el bundle
        mViewModel.recuperarInquilino(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InquilinoDetallesViewModel.class);
        // TODO: Use the ViewModel
    }

}