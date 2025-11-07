package com.example.deliciapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.deliciapp.db.DBHelper;
import com.example.deliciapp.model.User;

public class UserController  {

    private DBHelper dbHelper;

    public UserController(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Registrar usuario
    public boolean insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

    public String getUserRole(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM users WHERE username = ?", new String[]{username});

        String role = "";
        if (cursor.moveToFirst()) {
            role = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return role;
    }

    // Validar login
    public boolean login(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM users WHERE username = ? AND password = ?",
                new String[]{ username, password }
        );

        boolean isValid = cursor.moveToFirst();

        cursor.close();
        db.close();

        return isValid;
    }

}
