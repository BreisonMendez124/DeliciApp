package com.example.deliciapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciapp.R;
import com.example.deliciapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> listaProductos;
    private String userRole;
    private OnProductActionListener listener;

    // Interfaz para notificar acciones a la Activity
    public interface OnProductActionListener {
        void onEdit(Product product);
        void onDelete(Product product);
        void onAddToCart(Product product);
    }

    // Constructor
    public ProductAdapter(List<Product> listaProductos, String userRole, OnProductActionListener listener) {
        this.listaProductos = listaProductos;
        this.userRole = userRole;
        this.listener = listener;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio;
        Button btnEditar, btnEliminar , btnAgregarCarrito;
        LinearLayout layoutAdminButtons;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnAgregarCarrito = itemView.findViewById(R.id.btnAgregarCarrito);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            layoutAdminButtons = itemView.findViewById(R.id.layoutAdminButtons);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product producto = listaProductos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText("$" + producto.getPrecio());

        if ("admin".equalsIgnoreCase(userRole)) {
            holder.layoutAdminButtons.setVisibility(View.VISIBLE);
            holder.btnAgregarCarrito.setVisibility(View.GONE);
        } else {
            holder.layoutAdminButtons.setVisibility(View.GONE);
            holder.btnAgregarCarrito.setVisibility(View.VISIBLE);
        }
        holder.btnAgregarCarrito.setOnClickListener(v -> listener.onAddToCart(producto));
        holder.btnEditar.setOnClickListener(v -> listener.onEdit(producto));
        holder.btnEliminar.setOnClickListener(v -> listener.onDelete(producto));
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }
}
