package com.example.deliciapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NOMBRE = "deliciapp_db";
    private static final String TABLE_PRODUCTS = "products";

    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NOMBRE, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase ){
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUCTS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_name TEXT NOT NULL," +
                "description TEXT," +
                "price TEXT NOT NULL," +
                "ingredients TEXT NOT NULL," +
                "status TEXT NOT NULL)");

        //Creacion de la tabla de usuarios
        sqLiteDatabase.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "role TEXT NOT NULL," +
                "latitud REAL," +
                "longitud REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }

}
