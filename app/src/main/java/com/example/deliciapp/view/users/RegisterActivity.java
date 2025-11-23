package com.example.deliciapp.view.users;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.deliciapp.R;
import com.example.deliciapp.controller.UserController;
import com.example.deliciapp.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombreCompleto, etCorreo, etPassword;
    private Button btnRegistrar, btnVolver, btnObtenerUbicacion;
    private TextView tvUbicacion;

    private double latitud = 0.0;
    private double longitud = 0.0;

    private UserController userController;

    private FusedLocationProviderClient fusedLocationClient;

    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);

        btnObtenerUbicacion = findViewById(R.id.btnObtenerUbicacion);
        tvUbicacion = findViewById(R.id.tvUbicacion);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        userController = new UserController(this);
        // Ver si la Activity recibe un ID
        userId = getIntent().getIntExtra("id", -1);

        if (userId != -1) {
            cargarUsuario(userId);
            btnRegistrar.setText("Actualizar");
        }

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
        btnVolver.setOnClickListener(v -> finish());
        btnObtenerUbicacion.setOnClickListener(v -> obtenerUbicacion());
    }

    private void obtenerUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();

                tvUbicacion.setText("Ubicación: " + latitud + " , " + longitud);
            } else {
                tvUbicacion.setText("No se pudo obtener ubicación");
            }
        });
    }


    private void cargarUsuario(int id) {
        User u = userController.getUserById(id);

        if (u != null) {
            etNombreCompleto.setText(u.getUsername());
            etCorreo.setText(u.getUsername());
            etPassword.setText(u.getPassword());

            latitud = u.getLatitud();
            longitud = u.getLongitud();
            tvUbicacion.setText("Ubicación: " + latitud + " , " + longitud);
        }
    }

    private void registrarUsuario() {
        String nombre = etNombreCompleto.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (latitud == 0.0 && longitud == 0.0) {
            Toast.makeText(this, "Debe obtener su ubicación antes de registrarse", Toast.LENGTH_SHORT).show();
            return;
        }

        User nuevoUsuario = new User(correo, password, "admin", latitud, longitud);

        boolean exito = userController.insertUser(nuevoUsuario);

        if (exito) {
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

            etNombreCompleto.setText("");
            etCorreo.setText("");
            etPassword.setText("");
            tvUbicacion.setText("Ubicación: sin obtener");

            latitud = 0.0;
            longitud = 0.0;

        } else {
            Toast.makeText(this, "Error: el usuario ya existe", Toast.LENGTH_LONG).show();
        }
    }
}
