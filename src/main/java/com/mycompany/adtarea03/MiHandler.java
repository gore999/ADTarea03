/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Carlos
 */
class MiHandler extends DefaultHandler {

    ArrayList<Noticia> noticias = new ArrayList();
    Noticia noticiaAux;
    StringBuffer buffer = new StringBuffer();

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "item":
                break;
            case "title":
                if (noticiaAux != null) {
                    String titulo = buffer.toString();
                    noticiaAux.setTitular(titulo);
                }
                break;
            case "pubDate":
                if (noticiaAux != null) {
                    String date = buffer.toString();
                    noticiaAux.setDate(date);
                }
                break;
            case "content:encoded":
                String contenido = buffer.toString();
                noticiaAux.setNoticia(contenido);
                break;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "item":
                noticiaAux = new Noticia();
                noticias.add(noticiaAux);
                break;
            case "title":
            case "pubDate":
            case "content:encoded":
                buffer.delete(0, buffer.length());//vaciar buffer
        }
    }
    public String noticias(){
        String salida="";
        for(Noticia n:noticias){
            
        }
        return salida;
    }
}
