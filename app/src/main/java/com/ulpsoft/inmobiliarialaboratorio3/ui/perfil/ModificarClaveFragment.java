package com.ulpsoft.inmobiliarialaboratorio3.ui.perfil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentModificarClaveBinding;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

public class ModificarClaveFragment extends Fragment {

    private ModificarClaveViewModel mViewModel;
    private FragmentModificarClaveBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentModificarClaveBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Configurar ViewModel
        mViewModel = new ViewModelProvider(this).get(ModificarClaveViewModel.class);

        // Observar cambios en el mensaje de resultado
        mViewModel.getmMsjUsuario().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msjUsuario) {
                // Mostrar el mensaje devuelto por el ViewModel
                binding.tvMsjs.setText(msjUsuario);
            }
        });

        // Configurar el evento de click del botón para cambiar la contraseña
        binding.btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveActual = binding.etClaveActual.getText().toString().trim();
                String nuevaClave = binding.etClaveNueva.getText().toString().trim();
                String repetirClave = binding.etConfirmarClave.getText().toString().trim();

                // Obtener el token (puedes cambiar la forma de obtenerlo según tu lógica)
                String token = ApiClient.Leer(getContext());

                // Delegar la lógica al ViewModel
                mViewModel.cambiarClave(token, claveActual, nuevaClave, repetirClave);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null; // Evitar pérdidas de memoria
    }
}
