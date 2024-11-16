package com.example.stockbotiquin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RemedioAdapter extends RecyclerView.Adapter<RemedioAdapter.RemedioViewHolder> {

    private List<Remedio> remedios;
    private OnRemedioClickListener listener;

    // Interfaz para manejar clics en remedios
    public interface OnRemedioClickListener {
        void onRemedioClick(Remedio remedio);
    }

    // Constructor
    public RemedioAdapter(List<Remedio> remedios, OnRemedioClickListener listener) {
        this.remedios = remedios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RemedioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remedio, parent, false);
        return new RemedioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemedioViewHolder holder, int position) {
        Remedio remedio = remedios.get(position);

        holder.tvNombre.setText("Nombre: " + remedio.getNombre());
        holder.tvCantidad.setText("Cantidad: " + remedio.getCantidad());
        holder.tvFechaVencimiento.setText("Fecha de Vencimiento: " + remedio.getFechaVencimiento());
        holder.tvMg.setText("MG: " + remedio.getMg());
        holder.tvPresentacion.setText("Presentación: " + remedio.getPresentacion());
        holder.tvDescripcion.setText("Descripción: " + remedio.getDescripcion());

        // Configurar clic en el item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemedioClick(remedio);
            }
        });
    }

    @Override
    public int getItemCount() {
        return remedios.size();
    }

    static class RemedioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCantidad, tvFechaVencimiento, tvMg, tvPresentacion, tvDescripcion;

        public RemedioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvFechaVencimiento = itemView.findViewById(R.id.tvFechaVencimiento);
            tvMg = itemView.findViewById(R.id.tvMg);
            tvPresentacion = itemView.findViewById(R.id.tvPresentacion);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}
