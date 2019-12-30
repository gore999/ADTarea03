/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03.modelos;

import com.mycompany.adtarea03.Producto;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class TableProductosModel implements TableModel {

    ArrayList<Producto> productos;

    public TableProductosModel(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public int getRowCount() {
        if (productos != null) {
            return productos.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String salida = null;
        switch (columnIndex) {
            case 0:
                salida = "Id.";
                break;
            case 1:
                salida = "Nome";
                break;
            case 2:
                salida = "Descripcion";
                break;
            case 3:
                salida = "Prezo";
                break;
        }
        return salida;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Float.class;
        }
        return null;//Para cerrar sin error. 
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; //No se van a poder editar.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto p = this.productos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getIdentificador();
            case 1:
                return p.getNombre();
            case 2:
                return p.getDescripcion();
            case 3:
                return p.getPrecio();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Producto p = this.productos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                p.setIdentificador((Integer) aValue);
                break;
            case 1:
                p.setDescripcion((String) aValue);
                break;
            case 2:
                p.setDescripcion((String) aValue);
                break;
            case 3:
                p.setPrecio((Float) aValue);
                break;
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

}
