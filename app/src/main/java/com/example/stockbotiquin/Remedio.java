package com.example.stockbotiquin;

public class Remedio {
    private String id;
    private String nombre;
    private int cantidad;
    private String fechaVencimiento;
    private int mg;
    private String presentacion;
    private String descripcion;
    private boolean isNearExpiration;

    // Constructor
    public Remedio(String id, String nombre, int cantidad, String fechaVencimiento, int mg, String presentacion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.mg = mg;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getMg() {
        return mg;
    }

    public void setMg(int mg) {
        this.mg = mg;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isNearExpiration() {
        return isNearExpiration;
    }

    public void setNearExpiration(boolean nearExpiration) {
        isNearExpiration = nearExpiration;
    }
}
