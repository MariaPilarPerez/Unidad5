package com.ad.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Clase Direccion - Componente embebido
 * Representa la dirección de un empleado (calle, número, bloque)
 * 
 * Hecho: Anotar esta clase como @Embeddable
**/
@Embeddable
public class Direccion implements Serializable {
    
    private String calle;
    private int numero;
    private char bloque;

    public Direccion() {}

    public Direccion(String calle, int numero) {
        this.calle = calle;
        this.numero = numero;
        this.bloque = '-';
    }

    public Direccion(String calle, int numero, char bloque) {
        this.calle = calle;
        this.numero = numero;
        this.bloque = bloque;
    }

    // Getters y Setters
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public char getBloque() { return bloque; }
    public void setBloque(char bloque) { this.bloque = bloque; }

    @Override
    public String toString() {
        return "Direccion{calle=" + calle + ", numero=" + numero + ", bloque=" + bloque + "}";
    }
}