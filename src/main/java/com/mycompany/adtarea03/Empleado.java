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
public class Empleado {
    int id;
    private String nombre;
    private String apellidos;

    public Empleado(int id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + '}';
    }

   
    /**/
    @Override
    public boolean equals(Object obj) {
        Empleado emp=(Empleado)obj; //To change body of generated methods, choose Tools | Templates.
        if(emp.nombre.equals(this.nombre) && emp.apellidos.equals(this.apellidos)){
            return true;
        }else{
            return false;
        }
    }
    
}
