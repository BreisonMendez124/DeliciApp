package com.example.deliciapp.view;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciapp.R;
import com.example.deliciapp.db.DBHelper;
import com.example.deliciapp.model.Product;
import com.example.deliciapp.controller.ProductController;

import java.util.ArrayList;
import java.util.List;

public class ProductsListActivity extends AppCompatActivity {

    private RecyclerView recyclerProductos;
    private ProductAdapter productoAdapter;
    private List<Product> listaProductos;
    private FloatingActionButton btnAgregarProducto;

    private ProductController productController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productController = new ProductController(this);

        recyclerProductos = findViewById(R.id.recyclerProductos);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPref = getSharedPreferences("user_session", MODE_PRIVATE);
        String role = sharedPref.getString("role", "");
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        // Mostrar botón solo si es admin
        if ("admin".equals(role)) {
            btnAgregarProducto.setVisibility(View.VISIBLE);
        } else {
            btnAgregarProducto.setVisibility(View.GONE);
        }

        btnAgregarProducto.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsListActivity.this, products_form.class);
            startActivity(intent);
        });

        cargarProductos(role);
    }

    private void cargarProductos(String userRole) {
        listaProductos = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, product_name, description, price, ingredients, status FROM products", null);

        if (cursor.moveToFirst()) {
            do {
                Product producto = new Product();
                producto.setId(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setDescripcion(cursor.getString(2));
                producto.setPrecio(cursor.getString(3));
                producto.setIngredientes(cursor.getString(4));
                producto.setEstado(cursor.getString(5));

                listaProductos.add(producto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        productoAdapter = new ProductAdapter(listaProductos, userRole, new ProductAdapter.OnProductActionListener() {
            @Override
            public void onEdit(Product product) {
                // 🔹 Enviar todos los datos del producto al formulario
                Intent intent = new Intent(ProductsListActivity.this, products_form.class);
                intent.putExtra("id", product.getId());
                intent.putExtra("product_name", product.getNombre());
                intent.putExtra("description", product.getDescripcion());
                intent.putExtra("price", product.getPrecio());
                intent.putExtra("ingredients", product.getIngredientes());
                intent.putExtra("status", product.getEstado());
                startActivity(intent);
            }

            @Override
            public void onDelete(Product product) {
                // 🔹 Eliminar producto desde el controlador
                productController.deleteProduct(product.getId());
                cargarProductos(userRole); // recargar lista
            }

            @Override
            public void onAddToCart(Product product) {
                // 🔹 Aquí puedes guardar el producto en una tabla "cart" en SQLite
                // o en SharedPreferences (dependiendo de tu arquitectura)
                Toast.makeText(ProductsListActivity.this,
                        product.getNombre() + " agregado al carrito 🛒",
                Toast.LENGTH_SHORT).show();
            }
        });

        recyclerProductos.setAdapter(productoAdapter);
    }



    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("user_session", MODE_PRIVATE);
        String role = sharedPref.getString("role", "");
        cargarProductos(role); // recargar cada vez que se vuelve a la vista
    }
}
