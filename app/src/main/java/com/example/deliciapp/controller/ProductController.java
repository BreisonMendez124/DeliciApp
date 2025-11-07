package com.example.deliciapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.deliciapp.db.DBHelper;
import com.example.deliciapp.model.Product;

public class ProductController extends DBHelper {

    Context context;
    public ProductController(@Nullable Context context){
        super(context);
        this.context = context;
    }

    public void insertProduct(String product_name, String description , String price, String ingredients , String status){
        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("product_name" , product_name);
            values.put("price" , price);
            values.put("description" , description);
            values.put("ingredients" , ingredients);
            values.put("status" , status);

            //insertar los valores en la tabla de productos
            db.insert( "products", null , values );
            db.close();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(context, "Error al insertar: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Actualizar producto
    public boolean updateProduct(Product product) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_name", product.getNombre());
        values.put("description", product.getDescripcion());
        values.put("price", product.getPrecio());
        values.put("ingredients", product.getIngredientes());
        values.put("status", product.getEstado());

        int rows = db.update("products", values, "id = ?", new String[]{String.valueOf(product.getId())});
        db.close();
        return rows > 0;
    }

    // Eliminar producto
    public boolean deleteProduct(int productId) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete("products", "id = ?", new String[]{String.valueOf(productId)});
        db.close();
        return rows > 0;
    }
}
