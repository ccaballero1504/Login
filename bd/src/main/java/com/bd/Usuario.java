package com.bd;

import java.sql.*;
import java.util.Scanner;

public class Usuario {
    
    private String  nombre, puesto;
    private int contrasenia, permisos=1;
    private static Scanner teclado = new Scanner(System.in);

    public Usuario(String n, int c){
        this.nombre=n;
        this.contrasenia=c;
    }

    public boolean comprobar(Connection conn){
        String query = "select contrasenia, puesto from usuarios where usuario like "+"'" + this.nombre + "'";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.getInt(1)==this.contrasenia){
                this.puesto = rs.getString(2);
                if(puesto.equalsIgnoreCase("superadmin")){
                    permisos=3;
                }else if(puesto.equalsIgnoreCase("admin")){
                    permisos=2;
                }
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            System.err.println("ERROR "+e);
            return false;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public int getPermisos() {
        return permisos;
    }

    public static void crearUsuario(Connection conn){
        String query = "insert into usuarios values(?,?,?)";
        String nombre;
        int contrasenia;
        System.out.println("Nombre del nuevo usuario: ");
        nombre = teclado.nextLine();
        System.out.println("Contraseña del nuevo usuario: ");
        contrasenia = teclado.nextInt();
        teclado.nextLine();
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setInt(2, contrasenia);
            ps.setString(3, "Corriente");
            ps.executeUpdate();
        }catch (SQLException e){
            System.err.println("ERROR "+e);
        }
    }

    public static void crearAdmin(Connection conn){
        String query = "insert into usuarios values(?,?,?)";
        String nombre;
        int contrasenia;
        System.out.println("Nombre del nuevo admin: ");
        nombre = teclado.nextLine();
        System.out.println("Contraseña del nuevo admin: ");
        contrasenia = teclado.nextInt();
        teclado.nextLine();
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setInt(2, contrasenia);
            ps.setString(3, "Admin");
            ps.executeUpdate();
        }catch (SQLException e){
            System.err.println("ERROR "+e);
        }
    }

}
