/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03.modelos;

import com.mycompany.adtarea03.Cliente;
import com.mycompany.adtarea03.Empleado;
import com.mycompany.adtarea03.Tienda;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class TablaClientesModel implements TableModel {

    ArrayList<Cliente> clientes;

    public TablaClientesModel(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        if (clientes != null) {
            return clientes.size();
        }
        return 0;//Si el array es nulo, devolvemos 0.
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
                salida = "Apelidos";
                break;
            case 2:
                salida = "E-mail";
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
        Cliente cli = this.clientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cli.getNombre();
            case 1:
                return cli.getApellidos();
            case 2:
                return cli.getEmail();

        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Cliente e = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                e.setNombre((String) aValue);
                break;
            case 1:
                e.setApellidos((String) aValue);
                break;
            case 2:
                e.setEmail((String) aValue);
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
