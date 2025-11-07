package com.example.deliciapp.model;

public class Product {
    private int id;
    private String nombre;
    private String descripcion;
    private String precio;
    private String ingredientes;
    private String estado;

    public Product() {
    }

    public Product(String nombre, String precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Product(int id, String nombre, String descripcion, String precio, String ingredientes, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.estado = estado;
    }
    public Product(String nombre, String descripcion, String precio, String ingredientes, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }

    public String getIngredientes() { return ingredientes; }
    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
