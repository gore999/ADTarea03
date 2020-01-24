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
public class Noticia {
    String titular;//Contiene el titular de la noticia.
    String noticia;
    String date;
    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNoticia() {
        return noticia;
    }

    public void setNoticia(String noticia) {
        this.noticia = noticia;
    }

    public String getDate() {
        return date;
    }

    //Clase para albergar los datos de una noticia.
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String salida="NOTICIA: "+titular+"     ("+date+")\n";
        salida+="\n--------------------------------------------------------------------------------------------\n\n";
       
        return salida;
    }
    
    
    
}
