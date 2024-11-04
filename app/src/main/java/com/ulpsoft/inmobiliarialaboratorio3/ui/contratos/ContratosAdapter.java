package com.ulpsoft.inmobiliarialaboratorio3.ui.contratos;

import android.os.Bundle;
import android.util.Log;
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
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inquilino;
import com.ulpsoft.inmobiliarialaboratorio3.model.Pago;
import com.ulpsoft.inmobiliarialaboratorio3.ui.inquilinos.InquilinosAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContratosAdapter extends RecyclerView.Adapter<ContratosAdapter.ViewHolderContratos> {


    private List<Inmueble> inmuebles;
    private List<Contrato> contratos;
    private LayoutInflater li;

    public ContratosAdapter(List<Inmueble> inmuebles, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.li = li;
    }


    @NonNull
    @Override
    public ContratosAdapter.ViewHolderContratos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.card_inquilino, parent, false);
        return new ContratosAdapter.ViewHolderContratos(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContratosAdapter.ViewHolderContratos holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        contratos = inmueble.getContratos();
        // Verificar si hay contratos y obtener el primero si existe
        Contrato contrato = contratos.get(0);
        Inquilino inquilino = contrato.getInquilino();
        List<Pago> pagos = contrato.getPagos();

        // Cargar la imagen del inmueble
        Glide.with(holder.itemView.getContext())
                //.load("http://192.168.1.3:5290/" + inmueble.getAvatar())
                //ger
                .load("http://192.168.0.5:5290"+inmueble.getAvatar())
                //.load("http://192.168.0.3:5290"+inmueble.getAvatar())
                .placeholder(R.drawable.casa)
                .error(R.drawable.casa)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivFoto);

        holder.tvDireccion.setText("Dirección: " + inmueble.getDireccion());

        // Comprobar si hay contrato antes de mostrar datos
        if (contrato != null) {
            //parsear fecha dia-mes-año sin horario
            String fechaFin = contrato.getFechaFin();
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha = null;
            try {
                fecha = formatoEntrada.parse(fechaFin);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String fechaFormateada = formatoSalida.format(fecha);
            holder.tvFechaHasta.setText("Hasta: " + fechaFormateada);
            holder.tvDatosInquilino.setText("Inquilino: " + contrato.getInquilino().getApellido() + " " + contrato.getInquilino().getNombre());
        } else {
            holder.tvFechaHasta.setText("Hasta: No disponible");
            holder.tvDatosInquilino.setText("Inquilino: No disponible");
        }



        // Configurar el click para navegar al detalle del contrato
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("contrato", contrato);
            Navigation.findNavController(view).navigate(R.id.contratoDetalleFragment , bundle);
        });

    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public static class ViewHolderContratos extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvFechaHasta, tvDatosInquilino;
        ImageView ivFoto;

        public ViewHolderContratos(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivCardInquilinos);
            tvDireccion = itemView.findViewById(R.id.tvDomicilioInquilinos);
            tvFechaHasta = itemView.findViewById(R.id.tvFechaFinInquilinos);
            tvDatosInquilino = itemView.findViewById(R.id.tvCardDatosInquilinos);
        }
    }

}
