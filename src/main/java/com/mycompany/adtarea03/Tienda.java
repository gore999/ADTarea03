/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class Tienda {
    int id;
    private String nombre;
    private String ciudad;
    private String provincia;
    //Creamos 2 Maps
    //Productos: Relacion id del producto con su cantidad. 
    private Map<Integer,Integer> productos;
    //Empleados: Relacion id del empleado con las horas que hace en la tienda.
    private Map <Integer,Double> empleados;
    public Tienda(int id, String nombre, String ciudad, String provincia) {
        this.id=id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.provincia=provincia;
        empleados=new HashMap();
        productos=new HashMap();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    

    public void insertProducto(Producto p){
        
    }
    public void deleteProducto(Producto p){
        this.productos.remove(p);
    }


    @Override
    public String toString() {
        return "Tienda{" + "id=" + id + ", nombre=" + nombre + ", ciudad=" + ciudad + ", provincia=" + provincia + ", productos=" + productos + ", empleados=" + empleados + '}';
    }

    

    @Override
    public boolean equals(Object obj) {
        Tienda t=(Tienda)obj;
       if(this.nombre.equals(t.nombre)&& this.ciudad.equals(t.ciudad)&&this.provincia.equals(t.provincia)){
           return true;
       }else{
           return false;
       }        
    }
    
}
