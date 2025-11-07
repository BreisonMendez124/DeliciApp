package com.example.deliciapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciapp.R;
import com.example.deliciapp.controller.ProductController;
import com.example.deliciapp.model.Product;

public class products_form extends AppCompatActivity {

    private Button btnCreateProduct, btnVolver;
    private EditText etProductName, etProductDescription, etPrice, etIngredients, etStatus;
    private ProductController productController;
    private int productId = -1; // -1 si es nuevo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_form);

        productController = new ProductController(this);

        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        etPrice = findViewById(R.id.etPrice);
        etIngredients = findViewById(R.id.etIngredients);
        etStatus = findViewById(R.id.etStatus);
        btnCreateProduct = findViewById(R.id.btnCreateProduct);
        btnVolver = findViewById(R.id.btnVolver);

        // 🔹 Verificar si viene en modo edición
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            productId = intent.getIntExtra("id", -1);
            String nombre = intent.getStringExtra("product_name");
            String precio = intent.getStringExtra("price");
            String descripcion = intent.getStringExtra("description");
            String ingredientes = intent.getStringExtra("ingredients");
            String estado = intent.getStringExtra("status");

            // 🔸 Cargar los datos recibidos
            etProductName.setText(nombre);
            etPrice.setText(precio);
            etIngredients.setText(ingredientes);
            etProductDescription.setText(descripcion);
            etStatus.setText(estado);

            // Cambiar texto del botón si estamos editando
            btnCreateProduct.setText("Actualizar producto");
        }

        // 🔹 Botón VOLVER
        btnVolver.setOnClickListener(v -> {
            Intent volverIntent = new Intent(products_form.this, ProductsListActivity.class);
            startActivity(volverIntent);
            finish();
        });

        // 🔹 Botón GUARDAR / CREAR
        btnCreateProduct.setOnClickListener(v -> guardarProducto());
    }

    private void guardarProducto() {
        String name = etProductName.getText().toString().trim();
        String desc = etProductDescription.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String ingredients = etIngredients.getText().toString().trim();
        String status = etStatus.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || ingredients.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los campos obligatorios", Toast.LENGTH_LONG).show();
            return;
        }

        if (productId == -1) {
            // 🔹 CREAR producto nuevo
            productController.insertProduct(name, desc, price, ingredients, status);
            Toast.makeText(this, "Producto creado correctamente", Toast.LENGTH_SHORT).show();
            clearInputs();
        } else {
            Product product = new Product();
            product.setId(productId);
            product.setNombre(name);
            product.setDescripcion(desc);
            product.setPrecio(price);
            product.setIngredientes(ingredients);
            product.setEstado(status);

            boolean updated = productController.updateProduct(product);
            if (updated) {
                Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar producto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearInputs() {
        etProductName.setText("");
        etProductDescription.setText("");
        etPrice.setText("");
        etIngredients.setText("");
        etStatus.setText("");
    }
}
