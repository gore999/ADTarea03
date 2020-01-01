/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03;

/**
 *
 * @author Carlos
 */
public class Producto {

    private int identificador;
    private String nombre;
    private String descripcion;
    private double precio;

    public Producto(int identificador, String nombre, String descripcion, double precio) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Producto{" + "identificador=" + identificador + ", descripcion=" + descripcion + ", precio=" + precio + '}';
    }

    @Override
    public boolean equals(Object obj) {
        //Dos productos son iguales si tienen la misma id.
        return ((Producto) obj).identificador == this.identificador; //To change body of generated methods, choose Tools | Templates.
    }
    
}
