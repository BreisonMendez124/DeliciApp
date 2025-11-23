package com.example.deliciapp.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    private double latitud;
    private double longitud;

    public User(int id, String username, String password, String role , double latitud, double longitud) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Constructor para registro (sin id)
    public User(String username, String password, String role , double latitud, double longitud) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public User(){

    }

    // Getters y Setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }

    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }

    public void setLatitud(double latitud) { this.latitud = latitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
}
