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
public class Cliente {

    private String email;
    private String nombre;
    private String apellidos;

    public Cliente(String nombre, String apellidos, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }
//GETTERS Y SETTERS
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" + "email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + '}';
    }

    @Override
    public boolean equals(Object obj) {//Dos clientes son iguales si tienen el mismo email.
        return ((Cliente)obj).getEmail().equals(this.getEmail()); //To change body of generated methods, choose Tools | Templates.
    }
    

}
