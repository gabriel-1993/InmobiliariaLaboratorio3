package com.ulpsoft.inmobiliarialaboratorio3.ui.pagos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentPagosBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.Pago;
import com.ulpsoft.inmobiliarialaboratorio3.ui.contratos.ContratosAdapter;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel mViewModel;

    private FragmentPagosBinding binding;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        mViewModel.getmPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagosAdapter pagosAdapter = new PagosAdapter(pagos, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);

                binding.rvListaPagos.setAdapter(pagosAdapter);
                binding.rvListaPagos.setLayoutManager(gridLayoutManager);
            }
        });



        // Recuperar el Bundle y el objeto Contrato
            Contrato contrato = (Contrato) getArguments().getSerializable("contrato");
        Log.d("CONTRATO60",contrato.getFechaInicio());
        Log.d("PAGOS61", contrato.getPagos().get(0)+"");
            mViewModel.recuperarPagos(contrato);

        //return inflater.inflate(R.layout.fragment_pagos, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        // TODO: Use the ViewModel
    }

}