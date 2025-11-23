package com.example.deliciapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.deliciapp.db.DBHelper;
import com.example.deliciapp.model.User;

import java.util.ArrayList;
import java.util.List;

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

    // Nuevo: Obtener todos los usuarios
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users", null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setRole(cursor.getString(3));
                users.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return users;
    }

    //Obtener usuario por ID
    public User getUserById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", new String[]{String.valueOf(id)});
        User user = null;

        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setRole(cursor.getString(3));
        }

        cursor.close();
        db.close();
        return user;
    }

    // ----------------------------
    // UPDATE
    // ----------------------------
    public boolean updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        int rows = db.update("users", values, "id = ?", new String[]{String.valueOf(user.getId())});
        db.close();

        return rows > 0;
    }

    // ----------------------------
    // DELETE
    // ----------------------------
    public boolean deleteUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete("users", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }


}
