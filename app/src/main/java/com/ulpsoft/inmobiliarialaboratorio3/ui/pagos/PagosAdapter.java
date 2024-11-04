package com.ulpsoft.inmobiliarialaboratorio3.ui.pagos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;
import com.ulpsoft.inmobiliarialaboratorio3.model.Pago;
import com.ulpsoft.inmobiliarialaboratorio3.ui.contratos.ContratosAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolderPagos>{

    private List<Pago> pagos;

    private Contrato contrato;

    private LayoutInflater li;


    public PagosAdapter( List<Pago> pagos, LayoutInflater li) {
        this.pagos = pagos;
        this.li = li;
    }


    @NonNull
    @Override
    public PagosAdapter.ViewHolderPagos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.card_pago, parent, false);
        return new PagosAdapter.ViewHolderPagos(view);    }

    @Override
    public void onBindViewHolder(@NonNull PagosAdapter.ViewHolderPagos holder, int position) {
        Pago pago = pagos.get(position);

        // Establecer el número de pago y el importe
        holder.eTnroPago.setText("Número de pago: " + pago.getNroPago());
        holder.etPagoMonto.setText("Monto: $" + pago.getImporte());

        // Parsear y formatear la fecha del pago
        String fechaPago = pago.getFecha();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaPagoParsed = null;

        try {
            // Parsear la fecha
            fechaPagoParsed = formatoEntrada.parse(fechaPago);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Formatear la fecha
        String fechaPagoFormateada = formatoSalida.format(fechaPagoParsed);
        holder.etPagoFecha.setText("Fecha: " + fechaPagoFormateada);

        // Configurar el click para navegar al detalle del contrato
        holder.itemView.setOnClickListener(view -> {
            // Acción al hacer clic
        });
    }


    @Override
    public int getItemCount() {
        return pagos.size();
    }



    public static class ViewHolderPagos extends RecyclerView.ViewHolder {
        EditText eTnroPago, etPagoMonto, etPagoFecha;


        public ViewHolderPagos(@NonNull View itemView) {
            super(itemView);
            eTnroPago = itemView.findViewById(R.id.etPagoNro);
            etPagoMonto = itemView.findViewById(R.id.etPagoMonto);
            etPagoFecha = itemView.findViewById(R.id.etPagoFechaPago);
        }
    }
}
