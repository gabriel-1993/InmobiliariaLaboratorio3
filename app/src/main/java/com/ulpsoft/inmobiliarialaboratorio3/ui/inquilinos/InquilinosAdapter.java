package com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.type.DateTime;
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InquilinosAdapter extends RecyclerView.Adapter<InquilinosAdapter.ViewHolderInmueble> {

    private List<Inmueble> inmuebles;
    private LayoutInflater li;
    private Inquilino inquilino ;

    public InquilinosAdapter( List<Inmueble> inmuebles, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.card_inquilino, parent, false);
        return new ViewHolderInmueble(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {

        Inmueble inmueble = inmuebles.get(position);
        Contrato contrato = inmueble.getContratos().get(0);
        Inquilino inquilino = contrato.getInquilino();

        // Cargar la imagen del inmueble
        Glide.with(holder.ivFoto.getContext())
                //sanluis
               // .load("http://192.168.0.8:5290" + inmueble.getAvatar())
                //.load("http://192.168.0.3:5290" + inmueble.getAvatar())
                .load("http://192.168.0.5:5290" + inmueble.getAvatar())
                .placeholder(R.drawable.casa)
                .error(R.drawable.casa)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivFoto);

        holder.tvDireccion.setText("Dirección: " + inmueble.getDireccion());

        // Comprobar si el contrato tiene fecha de fin
        if (contrato.getFechaFin() != null) {
            // Parsear fecha día-mes-año sin horario
            String fechaFin = contrato.getFechaFin();
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha;
            try {
                fecha = formatoEntrada.parse(fechaFin+"");
                String fechaFormateada = formatoSalida.format(fecha);
                holder.tvFechaHasta.setText("Hasta: " + fechaFormateada);
            } catch (ParseException e) {
                holder.tvFechaHasta.setText("Hasta: No disponible");
            }
        } else {
            holder.tvFechaHasta.setText("Hasta: No disponible");
        }

        // Mostrar datos del inquilino
        if (inquilino != null) {
            holder.tvDatosInquilino.setText("Inquilino: " + inquilino.getApellido() + " " + inquilino.getNombre());
        } else {
            holder.tvDatosInquilino.setText("Inquilino: No disponible");
        }

        // Configurar el click para navegar al detalle del inquilino
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inquilino", inquilino);
            Navigation.findNavController(view).navigate(R.id.inquilinoDetallesFragment, bundle);
        });
    }


    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    // Puente entre XML y adapter
    public static class ViewHolderInmueble extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvFechaHasta, tvDatosInquilino;
        ImageView ivFoto;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivCardInquilinos);
            tvDireccion = itemView.findViewById(R.id.tvDomicilioInquilinos);
            tvFechaHasta = itemView.findViewById(R.id.tvFechaFinInquilinos);
            tvDatosInquilino = itemView.findViewById(R.id.tvCardDatosInquilinos);
        }
    }


}

