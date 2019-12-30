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

    public void addTienda(String nombre, String ciudad, String provincia) {
        String sql = "INSERT INTO tendas(nome, cidade,provincia) values(?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, ciudad);
            pstmt.setString(3, provincia);
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
        System.out.println("GetAlltiendas");
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
                System.out.println(t.toString());
                tiendas.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("rtdo."+tiendas.size());
        
        return tiendas;
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
}
