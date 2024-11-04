package com.ulpsoft.inmobiliarialaboratorio3.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentContratoDetalleBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentContratosBinding;
import com.ulpsoft.inmobiliarialaboratorio3.databinding.FragmentInquilinoDetallesBinding;
import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos.InquilinoDetallesViewModel;
import com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos.InquilinosViewModel;
import com.ulpsoft.inmobiliarialaboratorio3.ui.pagos.PagosFragment;
import com.ulpsoft.inmobiliarialaboratorio3.ui.perfil.PerfilFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContratoDetalleFragment extends Fragment {



    private ContratoDetalleViewModel mViewModel;

    private FragmentContratoDetalleBinding binding;

    public static ContratoDetalleFragment newInstance() {
        return new ContratoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentContratoDetalleBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);

        mViewModel.getmContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {


                    // Parsear fecha de inicio
                    String fechaDesde = contrato.getFechaInicio();
                    String fechaFin = contrato.getFechaFin();

                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");

                    Date fechaDesdeParsed = null;
                    Date fechaFinParsed = null;

                    try {
                        // Parsear fechas
                        fechaDesdeParsed = formatoEntrada.parse(fechaDesde);
                        fechaFinParsed = formatoEntrada.parse(fechaFin);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    // Formatear fechas
                    String fechaDesdeFormateada = formatoSalida.format(fechaDesdeParsed);
                    String fechaFinFormateada = formatoSalida.format(fechaFinParsed);

                    // Asignar textos a los TextView
                    binding.fechaDesdeContratoDetalle.setText("Desde: " + fechaDesdeFormateada);
                    binding.fechaHastaContratoDetalle.setText("Hasta: " + fechaFinFormateada);

                binding.etGaranteContratoDetalles.setText("Garante: "+contrato.getInquilino().getApellidoGarante()+" "+contrato.getInquilino().getNombreGarante());
                binding.InquilinoContratoDetalle.setText("Inquilino: "+ contrato.getInquilino().getApellido()+" "+contrato.getInquilino().getNombre());
               Log.d("precio84", contrato.getPrecio()+"");
                binding.precioContratoDetalles.setText("Precio: $"+ contrato.getPrecio());
                binding.etTelInquilinoContratoDetalles.setText("Inquilino Tel: "+ contrato.getInquilino().getTelefono());
                binding.edTelGaranteContratoDetalles.setText("Garante Tel: "+ contrato.getInquilino().getTelefonoGarante());

                binding.btnPagosContratoDetalles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Contrato contrato = mViewModel.getmContrato().getValue();
                        Log.d("contratoDetalles", contrato.getFechaInicio());
                        Log.d("contratoPAgo", contrato.getPagos().get(0).getImporte()+"");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato", contrato);
                        NavHostFragment.findNavController(ContratoDetalleFragment.this)
                                .navigate(R.id.pagosFragment,bundle);
                    }
                });
            }
        });

        mViewModel.recuperarContratoConpagos(getArguments());

        //return inflater.inflate(R.layout.fragment_contrato_detalle, container, false);
        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);
    }




}