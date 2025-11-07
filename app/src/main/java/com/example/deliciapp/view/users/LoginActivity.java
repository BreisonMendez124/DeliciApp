package com.example.deliciapp.view.users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciapp.R;
import com.example.deliciapp.controller.UserController;
import com.example.deliciapp.view.ProductsListActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, etPassword;
    private Button btnIngresar, btnRegistro;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar elementos de la vista
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistro = findViewById(R.id.btnRegistro);

        // Inicializar controlador
        userController = new UserController(this);

        // Listener del botón "Iniciar sesión"
        btnIngresar.setOnClickListener(v -> {
            String username = etCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                boolean isValid = userController.login(username, password);
                if (isValid) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    String role = userController.getUserRole(username);

                    // Guardamos los datos de sesión
                    SharedPreferences sharedPref = getSharedPreferences("user_session", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", username);
                    editor.putString("role", role);
                    editor.apply();

                    // Redirige a la lista de productos
                    Intent intent = new Intent(LoginActivity.this, ProductsListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listener del botón "Registrarme"
        btnRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
