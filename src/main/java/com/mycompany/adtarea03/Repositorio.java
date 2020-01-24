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
                + "cantidad INTEGER NOT NULL,"
                + "PRIMARY KEY(idTenda,idProduto))";
        String sqlProvincias = "CREATE TABLE IF NOT EXISTS provincias("
                + "id INTEGER NOT NULL,"
                + "nombre STRING NOT NULL,"
                + "PRIMARY KEY(id))";
        try {
            st = con.createStatement();
            st.execute(sqlTiendas);
            st.execute(sqlProductos);
            st.execute(sqlClientes);
            st.execute(sqlEmpleados);
            st.execute(sqlEmpleadosTiendas);
            st.execute(sqlProductosTiendas);
            st.execute(sqlProvincias);
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//RECUPERAR LISTAS DE DATOS (TIENDAS, EMPLEADOS Y PRODUCTOS)----------------------------------------------

    public ArrayList<Tienda> getAllTiendas() {
        ArrayList<Tienda> tiendas = new ArrayList();
        String sql = "SELECT * FROM tendas ORDER BY id";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cidade = rs.getString("cidade");
                String provincia = rs.getString("provincia");
                Tienda t = new Tienda(id, nome, cidade, provincia);
                tiendas.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiendas;
    }

    public ArrayList<Producto> getAllProductos() {
        ArrayList<Producto> productos = new ArrayList();
        String sql = "SELECT * FROM produtos ORDER BY id";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descripcion = rs.getString("descripcion");
                Float prezo = rs.getFloat("prezo");
                Producto p = new Producto(id, nome, descripcion, prezo);
                productos.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }

    public ArrayList<Empleado> getAllEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList();
        String sql = "SELECT * FROM empregados ORDER BY apelidos ASC";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apelidos");

                Empleado e = new Empleado(id, nombre, apellidos);
                empleados.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    public ArrayList<Cliente> getAllClientes() {
        ArrayList<Cliente> clientes = new ArrayList();
        String sql = "SELECT * FROM clientes ORDER BY apelidos ASC";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String nombre = rs.getString("nome");
                String apellidos = rs.getString("apelidos");
                String email = rs.getString("email");
                Cliente c = new Cliente(nombre, apellidos, email);
                clientes.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    //DE UNA TIENDA CONCRETA
    void getTiendaProductoData(Tienda tienda, ArrayList<Producto> productosTienda, ArrayList<Integer> cantProdTienda) {
        //Vaciamos los arrays, para rellenarlos de nuevo. 
        productosTienda.clear();
        cantProdTienda.clear();
        //Obtener datos de la BD.
        String sql = "SELECT * FROM produtos,produtos_tendas WHERE produtos_tendas.idTenda=? AND produtos.id=produtos_tendas.idproduto";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, tienda.id);//Obtener los productos y cantidades referentes a una tienda en concreto, por id de tienda. 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {//Recuperar datos del resultset.
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descripcion = rs.getString("descripcion");
                Float prezo = rs.getFloat("prezo");
                int cantidad = rs.getInt("cantidad");
                Producto p = new Producto(id, nome, descripcion, prezo);
                //Añadimos los datos a los arraylists.
                productosTienda.add(p);
                cantProdTienda.add(cantidad);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void getTiendaEmpleadoData(Tienda tiendaAux, ArrayList<Empleado> empleadosTienda, ArrayList<Integer> horasEmpTienda) {
        //Vaciamos los arrays, para rellenarlos de nuevo. 
        empleadosTienda.clear();
        horasEmpTienda.clear();
        //Obtener datos de la BD.
        String sql = "SELECT * FROM empregados,empleados_tendas WHERE empleados_tendas.idTenda=? AND empregados.id=empleados_tendas.idEmpregado";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, tiendaAux.id);//Obtener los productos y cantidades referentes a una tienda en concreto, por id de tienda. 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {//Recuperar datos del resultset.
                int id = rs.getInt("id");
                String nome = rs.getString("nombre");
                String apelidos = rs.getString("apelidos");
                int horas = rs.getInt("horas");
                Empleado e = new Empleado(id, nome, apelidos);
                //Añadimos los datos a los arraylists.
                empleadosTienda.add(e);
                horasEmpTienda.add(horas);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/// OPERACIONES SOBRE TIENDAS 

    /*CREAR UNA TIENDA*/
    public void insertTienda(Tienda tienda, ArrayList<Tienda> tiendas) {
        boolean existeYa = false;
        for (Tienda t : tiendas) {
            if (t.equals(tienda)) {
                existeYa = true;//Si hay alguna 
            }
        }
        if (!existeYa) {
            String sql = "INSERT INTO tendas(nome,cidade,provincia) VALUES(?,?,?)";
            try {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, tienda.getNombre());
                pstmt.setString(2, tienda.getCiudad());
                pstmt.setString(3, tienda.getProvincia());
                boolean rtdo = pstmt.execute();
                //Cambiar la id del objeto tienda para que se ajuste al de la tabla slqlite (por si se quiere borrar.
                ResultSet keys = pstmt.getGeneratedKeys();
                System.out.println("Rtdo:" + rtdo);
                keys.next();
                tienda.id = keys.getInt(1);
                tiendas.add(tienda);//Añadir la tienda al arraylist.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "La tienda ya existe", "Error", 0);
        }
    }

    /*ELIMINAR UNA TIENDA*/
    void deleteTienda(Tienda tienda, ArrayList<Tienda> tiendas) {
        String sql = "DELETE FROM tendas WHERE id=?";
        String sqldelRelacionesProd = "DELETE FROM produtos_tendas WHERE idTenda=?";
        String sqldelRelacionesEmp = "DELETE FROM empleados_tendas WHERE idTenda=?";
        try {//Borrar de base de datos.
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, tienda.id);
            pstmt.execute();
            //Borrar relaciones con productos
            pstmt = con.prepareStatement(sqldelRelacionesProd);
            pstmt.setInt(1, tienda.id);
            pstmt.execute();
            //Borrar relaciones con productos
            pstmt = con.prepareStatement(sqldelRelacionesProd);
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

    void deleteProducto(Producto p, ArrayList<Producto> productos) {
        String sql = "DELETE FROM produtos WHERE id=?";//Borrar el producto del listado general de productos. 
        String sql2 = "DELETE FROM produtos_tendas WHERE idproduto=?";//Borrar todas las relaciones de tiendas con el producto.
        try {//Borrar de base de datos.
            //Borrado del producto del listado
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, p.getIdentificador());
            pstmt.execute();
            //Borrado de relaciones`producto-tienda
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, p.getIdentificador());
            pstmt.execute();
            //Borrar del ArrayList
            productos.remove(p);//Añadir la tienda al arraylist.
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void insertEmpleado(Empleado emp, ArrayList<Empleado> empleados) {
        boolean existeYa = false;
        for (Empleado e : empleados) {
            if (e.equals(emp)) {
                existeYa = true;//Si hay alguna 
            }
        }
        if (!existeYa) {//Si no existe, lo insertamos.
            String sql = "INSERT INTO empregados(nombre,apelidos) VALUES(?,?)";
            try {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);//Necesitamos la key de insercion.
                pstmt.setString(1, emp.getNombre());
                pstmt.setString(2, emp.getApellidos());
                pstmt.execute();
                //Cambiar la id del objeto empleado para que se ajuste al de la tabla slqlite (por si se quiere borrar).
                ResultSet keys = pstmt.getGeneratedKeys();
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
        String sql = "DELETE FROM empregados WHERE id=?";
        String sql2 = "DELETE FROM empleados_tendas WHERE idEmpregado=?";//Borrado de relaciones 
        try {//Borrar de base de datos.
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, emp.getId());
            pstmt.execute();
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, emp.getId());
            pstmt.execute();
            //Borrar del ArrayList
            empleados.remove(emp);//Eliminar el empleado de lista general
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// Actuar sobre tiendas concretas

    void insertProductoEnTienda(Tienda tiendaAux, Producto productoAux, int cantidad, ArrayList<Producto> productosTienda, ArrayList<Integer> cantProdTienda) {
        int indiceProd = productosTienda.indexOf(productoAux);
        if (indiceProd != -1) {//Si el producto existe ya en el listado, cambiamos el valor en el ArrayList de cantidades de 
            cantProdTienda.set(indiceProd, cantidad);//usamos el indice del producto, cambiamos el valor a "cantidad".
        } else {//Si aun no tiene el producto, lo añade al final de ambos arrays.
            cantProdTienda.add(cantidad);
            productosTienda.add(productoAux);
        }
        //Actualizamos en base de datos la tabla que recoge relaciones tienda-producto con REPLACE: Si ya existe, reemplaza, si no, inserta.
        String sql = "REPLACE INTO produtos_tendas(idTenda,idproduto,cantidad) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);//Necesitamos la key de insercion.
            pstmt.setInt(1, tiendaAux.getId());
            pstmt.setInt(2, productoAux.getIdentificador());
            pstmt.setInt(3, cantidad);
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.getTiendaProductoData(tiendaAux, productosTienda, cantProdTienda);
    }

    void removeProdFromTienda(Producto p, ArrayList<Producto> productosTienda, Tienda tienda) {
        String sql2 = "DELETE FROM produtos_tendas WHERE idproduto=? and idTenda=?";//Borrar todas las relaciones de tiendas con el producto.
        try {//Borrar de base de datos.
            PreparedStatement pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, p.getIdentificador());
            pstmt.setInt(2, tienda.getId());
            pstmt.execute();
            //Borrar del ArrayList
            productosTienda.remove(p);//Añadir la tienda al arraylist.
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void insertEmpleadoEnTienda(Tienda tiendaAux, Empleado empleadoAux, int horas, ArrayList<Empleado> empleadosTienda, ArrayList<Integer> horasEmpTienda) {
        int indiceEmp = empleadosTienda.indexOf(empleadoAux);//Obtener el indice del empleado en el listado de empleados de la tienda
        if (indiceEmp != -1) {//Si el producto existe ya en el listado, cambiamos el valor en el ArrayList de cantidades de 
            horasEmpTienda.set(indiceEmp, horas);//usamos el indice del empleado, cambiamos el valor a "horas".
        } else {//Si aun no tiene el producto, lo añade al final de ambos arrays.
            horasEmpTienda.add(horas);
            empleadosTienda.add(empleadoAux);
        }
        //Actualizamos en base de datos la tabla que recoge relaciones tienda-producto con REPLACE: Si ya existe, reemplaza, si no, inserta.
        String sql = "REPLACE INTO empleados_tendas(idTenda,idEmpregado,horas) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);//Necesitamos la key de insercion.
            pstmt.setInt(1, tiendaAux.id);
            pstmt.setInt(2, empleadoAux.getId());
            pstmt.setInt(3, horas);
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.getTiendaEmpleadoData(tiendaAux, empleadosTienda, horasEmpTienda);
    }

    void removeEmpFromTienda(Empleado emp, ArrayList<Empleado> empleadosTienda, Tienda tienda) {
        String sql2 = "DELETE FROM empleados_tendas WHERE idEmpregado=? and idTenda=?";//Borrar todas las relaciones de tiendas con el producto.
        try {//Borrar de base de datos.
            PreparedStatement pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, emp.getId());
            pstmt.setInt(2, tienda.getId());
            pstmt.execute();
            //Borrar del ArrayList
            empleadosTienda.remove(emp);//Eliminar empleado del arraylist.
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addCliente(Cliente c, ArrayList<Cliente> clientes) {
        boolean existeYa = false;
        for (Cliente ca : clientes) {
            if (c.equals(ca)) {
                existeYa = true;//Si hay alguna 
            }
        }
        if (!existeYa) {
            String sql = "INSERT INTO clientes(nome,apelidos,email) VALUES(?,?,?)";//Borrar todas las relaciones de tiendas con el producto.
            try {//Borrar de base de datos.
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, c.getNombre());
                pstmt.setString(2, c.getApellidos());
                pstmt.setString(3, c.getEmail());
                pstmt.execute();
                //Borrar del ArrayList
                clientes.add(c);//Añadir cliente al arraylist.
            } catch (SQLException ex) {
                Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El cliente ya existe", "Error", 0);
        }
    }

    void deleteCliente(Cliente c, ArrayList<Cliente> clientes) {
        String sql = "DELETE FROM clientes WHERE nome=? AND apelidos=? AND email=?";
        try {//Borrar de base de datos.
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, c.getNombre());
            pstmt.setString(2, c.getApellidos());
            pstmt.setString(3, c.getEmail());
            pstmt.execute();
            clientes.remove(c);//Quitar el cliente del arraylist.
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void guardarProvincias(provincias prov) {
        String sql = "REPLACE INTO provincias (id, nombre) VALUES (?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            for (Provincia p : prov.provincias) {//Para cada provincia, guardamos su valor con Replace en la tabla.
                pstmt.setInt(1, p.getId());
                pstmt.setString(2, p.getNome());
                pstmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    provincias getProvinciasOBJ() {
        provincias provsOBJ=new provincias();
        //Obtener datos de la BD.
        String sql = "SELECT * FROM provincias";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {//Recuperar datos del resultset.
                Provincia p=new Provincia();
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                p.setId(id);
                p.setNome(nombre);
                //Añadimos los datos a los arraylists.
                provsOBJ.provincias.add(p);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provsOBJ;//Devuelve el objeto de la clase provincias
    }
}
