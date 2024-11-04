package com.ulpsoft.inmobiliarialaboratorio3.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentContratosBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInquilinosBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;
import com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos.InquilinosAdapter;
import com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos.InquilinosViewModel;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel mViewModel;

    private FragmentContratosBinding binding;


    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getmInmueblesLista().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {

                ContratosAdapter contratosAdapter = new ContratosAdapter(inmuebles, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);

                binding.rvListaContratos.setAdapter(contratosAdapter);
                binding.rvListaContratos.setLayoutManager(gridLayoutManager);
            }
        });






        /*return inflater.inflate(R.layout.fragment_contratos, container, false); */
        mViewModel.llamarContratosPagos();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);

    }

}