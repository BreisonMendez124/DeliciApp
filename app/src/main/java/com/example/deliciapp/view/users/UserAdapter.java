package com.example.deliciapp.view.users;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciapp.R;
import com.example.deliciapp.controller.UserController;
import com.example.deliciapp.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> listaUsuarios;
    private Context context;

    public UserAdapter(Context context, List<User> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = listaUsuarios.get(position);

        holder.tvUserName.setText(u.getUsername());
        holder.tvUserRole.setText("Rol: " + u.getRole());

        // BOTÓN EDITAR
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegisterActivity.class);
            intent.putExtra("id", u.getId());  // Modo edición
            context.startActivity(intent);
        });

        // BOTÓN ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar usuario")
                    .setMessage("¿Seguro que deseas eliminar a " + u.getUsername() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        UserController controller = new UserController(context);
                        controller.deleteUser(u.getId());

                        listaUsuarios.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaUsuarios.size());
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserRole;
        Button btnEditar, btnEliminar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserRole = itemView.findViewById(R.id.tvUserRole);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
