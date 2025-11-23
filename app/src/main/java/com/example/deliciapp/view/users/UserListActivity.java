package com.example.deliciapp.view.users;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciapp.R;
import com.example.deliciapp.controller.UserController;
import com.example.deliciapp.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerUsuarios;
    private UserController userController;

    private FloatingActionButton btnAgregarUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        userController = new UserController(this);

        cargarUsuarios();

        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);

        btnAgregarUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, RegisterActivity.class);
            intent.putExtra("isNew", true); // modo crear
            startActivity(intent);
        });

    }

    private void cargarUsuarios() {
        List<User> lista = userController.getAllUsers();

        UserAdapter adapter = new UserAdapter(this, lista);
        recyclerUsuarios.setAdapter(adapter);
    }
}
