package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInmuebleCrearBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentModificarClaveBinding;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;
import com.ulpsoft.inmobiliarialaboratorio3.ui.perfil.ModificarClaveViewModel;

import java.util.function.ObjIntConsumer;

public class InmuebleCrearFragment extends Fragment {

    private FragmentInmuebleCrearBinding binding;
    private InmuebleCrearViewModel mViewModel;
    //foto
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;


    public static InmuebleCrearFragment newInstance() {
        return new InmuebleCrearFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInmuebleCrearBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(InmuebleCrearViewModel.class);
        abrirGaleria();
        View view = binding.getRoot();



        binding.btnGuardarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String direccionn = binding.etDireccionCrear.getText().toString().trim();
                String tipoo = binding.etTipoCrear.getText().toString().trim();
                String usoo = binding.etUso.getText().toString().trim();
                String ambientess = binding.etAmbientes.getText().toString().trim();
                String precioo = binding.etPrecio.getText().toString().trim();

                // Obtener el token (puedes cambiar la forma de obtenerlo según tu lógica)
                String token = ApiClient.Leer(getContext());
                mViewModel.crearInmueble(token, direccionn, tipoo, usoo, ambientess, precioo);

            }
        });

        binding.btnModFotoCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }

        });

        //setear avatar
        mViewModel.getMAvatar().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                // Muestra la imagen seleccionada
                binding.ivInmuebleCrear.setImageURI(uri);

                // Llama al método del ViewModel para actualizar el avatar en el servidor
                mViewModel.actualizarAvatar();
            }
        });

        mViewModel.getmIdInmueble().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String i) {
                //ocultar guardar
                binding.btnGuardarInmueble.setVisibility(View.GONE);
                //mostrar modificar foto
                binding.btnModFotoCrear.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

    //abrir galeria para seleccionar foto
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mViewModel.recibirFoto(result);
            }
        });
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InmuebleCrearViewModel.class);
        // TODO: Use the ViewModel
    }

}