package com.example;

import java.util.Scanner;
import java.net.URI;
import java.net.http.*;
import com.google.gson.*;

public class Main {
    public static int campo(Scanner teclado){
        int respuesta;
        System.out.println("Campos:\n1- Nombre\n2- Laboratorio\n\nElige el campo que quieres buscar");
        respuesta = teclado.nextInt();
        teclado.nextLine();
        return respuesta;
    }

    public static void main(String[] args) {

        boolean seguir = true;
        Scanner teclado = new Scanner(System.in);
        String URL="https://cima.aemps.es/cima/rest/medicamentos?";
        String respuesta;
        //Crear cliente
        HttpClient client = HttpClient.newHttpClient();

        do{
            int campoint=campo(teclado);
            switch (campoint) {
                case 1:
                    System.out.println("Dame el nombre del medicamento");
                    String nombre = teclado.nextLine();
                    URL+="nombre="+nombre;
                    break;
                case 2:
                    System.out.println("Dame el laboratorio del medicamento");
                    String laboratorio = teclado.nextLine();
                    URL+="laboratorio="+laboratorio;
                default:
                    System.out.println("Opción no contemplada");
                    break;
            }

            //Crear petición
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).GET().build();
        
            try{
                //Enviar petición y guardar respuesta
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String respuestaAPI = response.body();
                JsonObject root = JsonParser.parseString(respuestaAPI).getAsJsonObject();
                JsonArray lista = root.getAsJsonArray("resultados");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println(gson.toJson(lista.get(1).getAsJsonObject()));

                System.out.println(URL);
                System.out.println("Quieres seguir? [s/n]");
                respuesta = teclado.nextLine();
                if(respuesta=="s"){
                    URL+="&&";
                }
                else if (respuesta=="n"){
                    seguir = false;
                }
                else{
                    URL+="&&";
                }
            }
            catch(Exception e){
                System.err.println(e);
                seguir=false;
            }
        }while (seguir);
        
    }
}