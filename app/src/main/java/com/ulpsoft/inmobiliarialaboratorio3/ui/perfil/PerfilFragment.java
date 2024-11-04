package com.ulpsoft.inmobiliarialaboratorio3.ui.perfil;


import static retrofit2.Response.error;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentPerfilBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Propietario;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;

    private FragmentPerfilBinding binding;
    //foto
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;


    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        abrirGaleria();

        mViewModel.getmPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etDocumento.setText(propietario.getDni());
                binding.etApellido.setText(propietario.getApellido());
                binding.etNombre.setText(propietario.getNombre());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etEmail.setText(propietario.getEmail());
                Log.d("avatar url : " , propietario.getAvatar());

                //binding.ivPerfilModificar.setImageResource(R.drawable.ic_launcher_background);
                Glide.with(getContext())
                       //lapunta
                        //.load("http://192.168.1.3:5290/"+propietario.getAvatar())
                        //sanluis
                        //.load("http://192.168.0.8:5290"+propietario.getAvatar())
                       //ger
                        .load("http://192.168.0.5:5290"+propietario.getAvatar())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.sinfoto)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivPerfilModificar);
            }
        });

        mViewModel.recuperarDatosPropietarioToken();

        mViewModel.getmMsjUsuario().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("perfilfragment", s);
                binding.tvMsjsPerfil.setText(s);
            }
        });


        // Configura el botón para modificar datos (Documento, Nombre, Apellido, Telefono e email)
        binding.btnModificarPerfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mViewModel.modificarPerfil(binding.etDocumento.getText().toString().trim(), binding.etApellido.getText().toString().trim(), binding.etNombre.getText().toString().trim(), binding.etTelefono.getText().toString().trim(), binding.etEmail.getText().toString().trim());
            // mViewModel.modificarPerfil(binding.etDocumento.getText().toString().trim(), binding.etNombre.getText().toString().trim(), binding.etApellido.getText().toString().trim(), binding.etTelefono.getText().toString().trim(), binding.etEmail.getText().toString().trim());
            }
        });

        //setear avatar
        mViewModel.getMAvatar().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                // Muestra la imagen seleccionada
                binding.ivPerfilModificar.setImageURI(uri);

                // Llama al método del ViewModel para actualizar el avatar en el servidor
                mViewModel.actualizarAvatar();
            }
        });




        // Configura el botón para modificar clave
        binding.btnModificarClave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Navega al fragmento de modificar clave
                NavHostFragment.findNavController(PerfilFragment.this)
                        .navigate(R.id.modificarClaveFragment);
            }
        });

        //listener boton subir foto
        binding.btnModificarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        return root;
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
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

}