/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03.modelos;

import com.mycompany.adtarea03.Tienda;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class TableTendaModel implements TableModel {

    ArrayList<Tienda> tiendas;

    public TableTendaModel(ArrayList<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    @Override
    public int getRowCount() {
        if (tiendas != null) {
            return tiendas.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String salida = null;
        switch (columnIndex) {
            case 0:
                salida = "Nome";
                break;
            case 1:
                salida = "Cidade";
                break;
            case 2:
                salida = "Provincia";
                break;
        }
        return salida;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class; //todos son cadenas.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; //No se van a poder editar.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tienda t = this.tiendas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return t.getNombre();
            case 1:
                return t.getCiudad();
            case 2:
                return t.getProvincia();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tienda t = this.tiendas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                t.setNombre((String) aValue);
                break;
            case 1:
                t.setCiudad((String) aValue);
                break;
            case 2:
                t.setProvincia((String) aValue);
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
