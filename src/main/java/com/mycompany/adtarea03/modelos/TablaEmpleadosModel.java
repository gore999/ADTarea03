/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03.modelos;

import com.mycompany.adtarea03.Empleado;
import com.mycompany.adtarea03.Tienda;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class TablaEmpleadosModel implements TableModel {

    ArrayList<Empleado> empleados;

    public TablaEmpleadosModel(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public int getRowCount() {
        if (empleados != null) {
            return empleados.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 2;
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
        Empleado emp = this.empleados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return emp.getNombre();
            case 1:
                return emp.getApellidos();
            
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Empleado e = empleados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                e.setNombre((String) aValue);
                break;
            case 1:
                e.setApellidos((String) aValue);
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
