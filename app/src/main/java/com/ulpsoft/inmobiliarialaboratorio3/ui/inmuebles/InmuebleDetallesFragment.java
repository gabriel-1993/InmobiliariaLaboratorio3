package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInmuebleDetallesBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInmueblesBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import javax.security.auth.callback.Callback;

public class InmuebleDetallesFragment extends Fragment {


    private FragmentInmuebleDetallesBinding binding;
    private InmuebleDetallesViewModel vm;

public static InmuebleDetallesFragment newInstance() { return new InmuebleDetallesFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentInmuebleDetallesBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmuebleDetallesViewModel.class);




        vm.getmInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.switchEstadoPublicacion.setChecked(inmueble.isDisponible());

                binding.tvDireccionDetalles.setText("Domicilio: "+ inmueble.getDireccion());
                binding.tvPrecioDetalles.setText("Precio: $ "+inmueble.getPrecio());
                binding.tvAmbientesDetalles.setText("Ambientes: "+inmueble.getAmbientes());
                binding.tvTipoDetalles.setText("Tipo: "+inmueble.getTipo());
                binding.tvUsoDetalles.setText("Uso: "+inmueble.getUso());

                //lapunta
                //.load("http://192.168.1.3:5290/"+inmueble.getAvatar())
                //ger
                //.load("http://192.168.0.5:5290"+inmueble.getAvatar())
                //.load("http://192.168.0.8:5290"+inmueble.getAvatar())
                Glide.with(binding.ivInmuebleDetalles.getContext())
                        //.load("http://192.168.0.5:5290"+inmueble.getAvatar())
                        .load("http://192.168.1.4:5290"+inmueble.getAvatar())
                        .placeholder(R.drawable.casa)
                        .error(R.drawable.casa)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivInmuebleDetalles);
            }
        });

        // Recuperar el bundle pasado al fragmento
         vm.recuperarInmueble(getArguments());






        binding.switchEstadoPublicacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vm.publicarInmuebleOnOff(b);

            }
        });





        return binding.getRoot();

    }





}