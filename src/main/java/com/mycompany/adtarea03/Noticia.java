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
public class Noticia {//Clase para albergar los datos de una noticia. 
    String texto;//Contiene el titular de la noticia.
    //Dejamos el constructor por defecto.
    //Getters y Setters 
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "************************************************************************************************\nNoticia:\n" + texto 
                + "\n************************************************************************************************\n";
    }
    
    
}
