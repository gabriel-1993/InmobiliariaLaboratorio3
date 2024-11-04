package com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos;

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

import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInquilinosBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;

import java.util.List;

public class InquilinosFragment extends Fragment {


    private InquilinosViewModel mViewModel;

    private FragmentInquilinosBinding binding;

    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        mViewModel.getmInmueblesContratoInquilino().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> contratoCursos) {
                InquilinosAdapter inquilinosAdapter = new InquilinosAdapter(contratoCursos , inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);

                binding.rvListaInquilinos.setAdapter(inquilinosAdapter);
                binding.rvListaInquilinos.setLayoutManager(gridLayoutManager);
            }
        });

        mViewModel.llamarInquilinosConContratosEnCurso();
        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);

    }



}

