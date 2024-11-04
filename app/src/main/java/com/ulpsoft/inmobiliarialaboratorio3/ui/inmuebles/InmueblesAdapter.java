package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

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
import com.ulpsoft.inmobiliarialaboratorio3.R;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;

import java.util.List;



public class InmueblesAdapter extends RecyclerView.Adapter<InmueblesAdapter.ViewHolderInmueble>
{

    private List<Inmueble> inmuebles;
    private LayoutInflater li;

    public InmueblesAdapter(List<Inmueble> inmuebles, LayoutInflater li){
        this.inmuebles = inmuebles;
        this.li = li;
    }


    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.card_inmueble, parent, false);
        return new ViewHolderInmueble(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {

        Inmueble inmueble = inmuebles.get(position);
        //lapunta
        //.load("http://192.168.1.3:5290/"+inmueble.getAvatar())
        //ger
        // .load("http://192.168.0.5:5290"+inmueble.getAvatar())
        //.load("http://192.168.0.8:5290"+inmueble.getAvatar())
        Glide.with(holder.ivFoto.getContext())
                    .load("http://192.168.0.5:5290"+inmueble.getAvatar())
                    .placeholder(R.drawable.casa) // Un recurso de imagen de carga
                    .error(R.drawable.casa) // Un recurso de imagen de error
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //guardar en cache del telefono
                    .into(holder.ivFoto);

            holder.tvDireccion.setText("Dirección: " + inmueble.getDireccion());
            holder.tvDisponibilidad.setText("Disponibilidad: "+(inmueble.isDisponible()? "Publicado" : "Sin publicar"));
            holder.tvPrecioDetalle.setText("Precio: $" + inmueble.getPrecio());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("inmueble", inmueble);
                    Navigation.findNavController(view).navigate(R.id.detalleInmuebleFragment, bundle);

                }
            });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }


    //puente entre xml y adapter
    public class ViewHolderInmueble extends RecyclerView.ViewHolder {

        TextView tvDireccion, tvDisponibilidad, tvPrecioDetalle;
        ImageView ivFoto;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivCardInmueble);
            tvDireccion = itemView.findViewById(R.id.tvDomicilio);
            tvDisponibilidad = itemView.findViewById(R.id.tvDisponibilidad);
            tvPrecioDetalle = itemView.findViewById(R.id.tvPrecio);
        }


    }

}
