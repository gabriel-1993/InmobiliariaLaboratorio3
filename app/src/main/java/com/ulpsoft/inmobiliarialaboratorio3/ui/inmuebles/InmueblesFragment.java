/*package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;


import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.R;

public class InmueblesFragment extends Fragment {

    private InmueblesViewModel mViewModel;

    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inmuebles, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);

    }

}


 */

package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.MainActivity;
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInicioBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInmueblesBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private InmueblesViewModel mViewModel;

    private FragmentInmueblesBinding binding;


    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inicializar el ViewModel
        mViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Observar los cambios en los datos de inmuebles
        mViewModel.getmInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {

                InmueblesAdapter inmueblesAdapter = new InmueblesAdapter(inmuebles, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);

//                binding.rvListaInmuebles(inmueblesAdapter);
                binding.rvListaInmuebles.setAdapter(inmueblesAdapter);
                binding.rvListaInmuebles.setLayoutManager(gridLayoutManager);
            }
        });

        //viajar al fragment crear inmueble
         binding.btnCrearInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   mViewModel.crearInmueble();
                Navigation.findNavController(requireView()).navigate(R.id.inmuebleCrearFragment);
            }
        });



         // Llamar al método para recuperar los inmuebles
            mViewModel.llamarListadoMisInmuebles();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);
        // TODO: Use the ViewModel
    }

}
