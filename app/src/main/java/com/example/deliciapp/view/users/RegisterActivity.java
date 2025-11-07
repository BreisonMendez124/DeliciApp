package com.example.deliciapp.view.users;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciapp.R;
import com.example.deliciapp.controller.UserController;
import com.example.deliciapp.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombreCompleto, etCorreo, etPassword;
    private Button btnRegistrar, btnVolver;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        userController = new UserController(this);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void registrarUsuario() {
        String nombre = etNombreCompleto.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        User nuevoUsuario = new User(correo, password, "user");
        boolean exito = userController.insertUser(nuevoUsuario);

        if (exito) {
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            etNombreCompleto.setText("");
            etCorreo.setText("");
            etPassword.setText("");
        } else {
            Toast.makeText(this, "Error: el usuario ya existe", Toast.LENGTH_LONG).show();
        }
    }
}
