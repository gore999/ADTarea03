/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.adtarea03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Repositorio {//Clase singleton para operar con datos.

    private String jdbc = "jdbc:sqlite:";
    private String archivoDatos = "datosTienda.db";
    private Connection con;
    private static Repositorio repoInstancia;

    private Repositorio() {
        try {
            con = DriverManager.getConnection(jdbc + archivoDatos);

        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Repositorio getInstance() {
        if (repoInstancia == null) {
            repoInstancia = new Repositorio();
        }
        return repoInstancia;
    }
    /*
        Crea las tablas de datos de la app con la clausula IF NOT EXISTS, si ya existen, no tienen efecto.
    */
    public void createTables() {
        Statement st = null;
        String sqlTiendas = "CREATE TABLE IF NOT EXISTS tendas("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "cidade TEXT NOT NULL,"
                + "provincia TEXT NOT NULL)";
        String sqlProductos = "CREATE TABLE IF NOT EXISTS produtos("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "descripcion TEXT NOT NULL,"
                + "prezo REAL NOT NULL)";
        String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes("
                + "nome TEXT NOT NULL,"
                + "apelidos TEXT NOT NULL,"
                + "email TEXT NOT NULL)";
        String sqlEmpleados = "CREATE TABLE IF NOT EXISTS empregados("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "apelidos TEXT NOT NULL)";
        //Relaciones entre empleados y tiendas. P.K= la relacion entre tienda y empleado
        String sqlEmpleadosTiendas = "CREATE TABLE IF NOT EXISTS empleados_tendas("
                + "idTenda INTEGER NOT NULL,"
                + "idEmpregado INTEGER NOT NULL,"
                + "horas INTEGER NOT NULL,"
                + "PRIMARY KEY(idTenda,idEmpregado))";
        //Relaciones entre productos y tiendas. P.K= la relacion entre tienda y producto
        String sqlProductosTiendas = "CREATE TABLE IF NOT EXISTS produtos_tendas("
                + "idTenda INTEGER NOT NULL,"
                + "idproduto INTEGER NOT NULL,"
                + "horas INTEGER NOT NULL,"
                + "PRIMARY KEY(idTenda,idProduto))";
        try {
            st = con.createStatement();
            st.execute(sqlTiendas);
            st.execute(sqlProductos);
            st.execute(sqlClientes);
            st.execute(sqlEmpleados);
            st.execute(sqlEmpleadosTiendas);
            st.execute(sqlProductosTiendas);
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Tienda> getAllTiendas() {
        ArrayList<Tienda> tiendas = new ArrayList();
        String sql = "SELECT * FROM tendas ORDER BY id";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql); 
            while(rs.next()){
                int id=rs.getInt("id");
                String nome=rs.getString("nome");
                String cidade=rs.getString("cidade");
                String provincia=rs.getString("provincia");
                Tienda t=new Tienda(id,nome,cidade,provincia);
                tiendas.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("rtdo."+tiendas.size());
        
        return tiendas;
    }
    
    public ArrayList<Producto> getAllProductos() {
        ArrayList<Producto> productos = new ArrayList();
        String sql = "SELECT * FROM produtos ORDER BY id";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql); 
            while(rs.next()){
                int id=rs.getInt("id");
                String nome=rs.getString("nome");
                String descripcion=rs.getString("descripcion");
                Float prezo=rs.getFloat("prezo");
                Producto p=new Producto(id,nome,descripcion,prezo);
                System.out.println(p.toString());
                productos.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("rtdo."+productos.size());
        
        return productos;
    }
    public ArrayList<Empleado> getAllEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList();
        String sql = "SELECT * FROM empregados ORDER BY id";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql); 
            while(rs.next()){
                int id=rs.getInt("id");
                String nombre=rs.getString("nombre");
                String apellidos=rs.getString("apelidos");
                
                Empleado e=new Empleado(id,nombre,apellidos);
                empleados.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }
    
    public void insertTienda(Tienda tienda, ArrayList<Tienda> tiendas){
        boolean existeYa=false;
        for(Tienda t:tiendas){
            if(t.equals(tienda))existeYa=true;//Si hay alguna 
        }
        if(!existeYa){
            String sql="INSERT INTO tendas(nome,cidade,provincia) VALUES(?,?,?)";
            try {
                PreparedStatement pstmt=con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, tienda.getNombre());
                pstmt.setString(2, tienda.getCiudad());
                pstmt.setString(3, tienda.getProvincia());
                boolean rtdo=pstmt.execute();
                //Cambiar la id del objeto tienda para que se ajuste al de la tabla slqlite (por si se quiere borrar.
                ResultSet keys=pstmt.getGeneratedKeys();
                System.out.println("Rtdo:"+rtdo);
                keys.next();
                tienda.id=keys.getInt(1);
                tiendas.add(tienda);//Añadir la tienda al arraylist.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "La tienda ya existe", "Error", 0);
        }
    }

    void deleteTienda(Tienda tienda,ArrayList<Tienda> tiendas) {
        String sql="DELETE FROM tendas WHERE id=?";
            try {//Borrar de base de datos.
                PreparedStatement pstmt=con.prepareStatement(sql);
                pstmt.setInt(1, tienda.id);
                pstmt.execute();
                //Borrar del ArrayList
                tiendas.remove(tienda);//Añadir la tienda al arraylist.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    void insertProducto(Producto producto, ArrayList<Producto> productos) {
        boolean existeYa = false;
        for (Producto p : productos) {
            if (p.equals(producto)) {
                existeYa = true;//Si hay alguna 
            }
        }
        if (!existeYa) {//Si no existe, lo insertamos.
            String sql = "INSERT INTO produtos(id,nome,descripcion,prezo) VALUES(?,?,?,?)";
            try {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, producto.getIdentificador());
                pstmt.setString(2, producto.getNombre());
                pstmt.setString(3, producto.getDescripcion());
                pstmt.setDouble(4, producto.getPrecio());
                pstmt.execute();
                productos.add(producto);//Añadir la tienda al arraylist.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El producto ya existe", "Error", 0);
        }   
    }

    void deleteProducto(Producto p,ArrayList<Producto> productos) {
        String sql="DELETE FROM produtos WHERE id=?";
            try {//Borrar de base de datos.
                PreparedStatement pstmt=con.prepareStatement(sql);
                pstmt.setInt(1, p.getIdentificador());
                pstmt.execute();
                //Borrar del ArrayList
                productos.remove(p);//Añadir la tienda al arraylist.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    void insertEmpleado(Empleado emp,ArrayList<Empleado> empleados) {
        boolean existeYa = false;
        for (Empleado e : empleados) {
            if (e.equals(emp)) {
                existeYa = true;//Si hay alguna 
            }
        }
        if (!existeYa) {//Si no existe, lo insertamos.
            String sql = "INSERT INTO empregados(nombre,apelidos) VALUES(?,?)";
            try {
                PreparedStatement pstmt=con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);//Necesitamos la key de insercion.
                pstmt.setString(1, emp.getNombre());
                pstmt.setString(2, emp.getApellidos());
                pstmt.execute();
                //Cambiar la id del objeto empleado para que se ajuste al de la tabla slqlite (por si se quiere borrar).
                ResultSet keys=pstmt.getGeneratedKeys();
                keys.next();
                emp.setId(keys.getInt(1));
                empleados.add(emp);//Añadir la tienda al arraylist.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El empleado ya existe", "Error", 0);
        }
    }

    void deleteEmpleado(Empleado emp, ArrayList<Empleado> empleados) {
        String sql="DELETE FROM empregados WHERE id=?";
            try {//Borrar de base de datos.
                PreparedStatement pstmt=con.prepareStatement(sql);
                pstmt.setInt(1, emp.getId());
                pstmt.execute();
                //Borrar del ArrayList
                empleados.remove(emp);//Eliminar el empleado.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
