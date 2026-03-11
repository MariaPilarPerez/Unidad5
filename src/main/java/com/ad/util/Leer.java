package com.ad.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utilidad para lectura de datos por consola
 */
public class Leer {
    private static final BufferedReader entradaConsola = 
        new BufferedReader(new InputStreamReader(System.in));

    public static String leerTexto(String mensaje) {
        String respuesta = null;
        do {
            try {
                System.out.print(mensaje);
                respuesta = entradaConsola.readLine();
            } catch (IOException ex) {
                return "";
            }
        } while (respuesta == null);
        return respuesta;
    }

    public static int leerEntero(String mensaje) {
        int n = 0;
        boolean conseguido = false;
        while (!conseguido) {
            try {
                n = Integer.parseInt(leerTexto(mensaje));
                conseguido = true;
            } catch (NumberFormatException ex) {
                System.out.println("Debes introducir un número correcto");
            }
        }
        return n;
    }
}