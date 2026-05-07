package com.bd;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;


public class Main {

    public static byte menuOP(Scanner teclado, int poder){
        byte op;
        System.out.println("1. Fecha");
        if(poder>1){
            System.out.println("2. Crear Usuario");
        }
        if(poder==3){
            System.out.println("3. Crear Admin");
        }
        System.out.println("0. Salir");
        op = teclado.nextByte();
        if(poder==1 && op > 1){
            System.out.println("No tienes permisos para hacer eso");
            op=7;
        }
        if(poder==2 && op > 2){
            System.out.println("No tienes permisos para hacer eso");
            op=7;
        }
        return op;
    }

    public static void main(String[] args) {
        
        int menu=0;
        Scanner teclado = new Scanner(System.in);
        String URL = "jdbc:sqlite:/home/carlos/Prueba.db";
        Usuario user;

        try(Connection con = DriverManager.getConnection(URL)){

            if(con!=null){
                String usuario;
                String contrasenia;
                int permisos=0;
                System.out.print("USUARIO: ");
                usuario = teclado.nextLine();
                System.out.print("CONTRASEÑA: ");
                contrasenia = teclado.nextLine();
                user = new Usuario(usuario, contrasenia);
                if(user.comprobar(con)){
                    do{
                        menu = menuOP(teclado, user.getPermisos());
                        switch (menu) {
                            case 1:
                                LocalDate fecha = LocalDate.now();
                                System.out.println("Fecha: " + fecha);
                                break;
                            case 2:
                                user.crearUsuario(con);
                                break;
                            case 3:
                                user.crearAdmin(con);
                                break;
                            default:
                                break;
                        }
                    }while (menu!=0);
                }
            }

        }catch (SQLException e){
            System.err.println("ERROR " + e);
        }

    }
}