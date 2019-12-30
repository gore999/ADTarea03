/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03;

import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class Tienda {
    int id;
    private String nombre;
    private String ciudad;
    private String provincia;
    private ArrayList <Producto> productos;
    private ArrayList <Empleado> empleados;
    public Tienda(int id, String nombre, String ciudad, String provincia) {
        this.id=id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.provincia=provincia;
        empleados=new ArrayList();
        productos=new ArrayList();
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

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }
    public void insertProducto(Producto p){
        this.productos.add(p);
    }
    public void deleteProducto(Producto p){
        this.productos.remove(p);
    }
    public Producto searchProducto(int identificador){
        Producto pAux=null;
        for (Producto p:productos){
            if(p.getIdentificador()==identificador){
                pAux=p;
                break;//rompo el bucle si encuentro una coincidencia.
            }
        }
        return pAux;
    }
    public Empleado searchEmpregado(String nombre, String Apellido){
        Empleado eAux=null;
        for (Empleado e:empleados){
            if(e.getNombre()==nombre && e.getApellidos()==Apellido){
                eAux=e;
                break;
            }
        }
        return eAux;
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
