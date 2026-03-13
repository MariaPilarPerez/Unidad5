package com.ad.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.CascadeType;
import javax.persistence.Column;



/**
 * Clase Departamento - Entidad
 * 
 * Requisitos:
 * - idDepartamento: identificador autogenerado
 * - nombre: String
 * - sede: String
 * - losEmpleados: relación OneToMany con Empleado
 * 
 * TODO:
 * 1. Anotar la clase como @Entity
 * 2. Anotar idDepartamento con @Id y @GeneratedValue
 * 3. Anotar losEmpleados con @OneToMany (cascade, fetch)
 */
@Entity
@Table (name = "Departamento")
public class Departamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private long idDepartamento;

    @Column(nullable=false, length=200)
    private String nombre;
    @Column(nullable=false, length=200)
    private String sede;
    
    @OneToMany(
        mappedBy = "departamento",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    
    private Set<Empleado> losEmpleados = new HashSet<>();

    public Departamento() {}

    public Departamento(String nombre, String sede) {
        this.nombre = nombre;
        this.sede = sede;
    }

    public long getIdDepartamento() {
        return this.idDepartamento;
    }

    public void setIdDepartamento(long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSede() {
        return this.sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Set<Empleado> getLosEmpleados() {
        return this.losEmpleados;
    }

    public void setLosEmpleados(Set<Empleado> losEmpleados) {
        this.losEmpleados = losEmpleados;
    }

    @Override
    public String toString() {
        return "{" +
            " idDepartamento='" + getIdDepartamento() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", sede='" + getSede() + "'" +
            ", losEmpleados='" + getLosEmpleados() + "'" +
            "}";
    }

    


    public void addEmpleado(Empleado e){
        losEmpleados.add(e);
        e.setDepartamento(this);

    }
    public void removeEmpleado(Empleado e){
        losEmpleados.remove(e);
        e.setDepartamento(null);
    }
    // Hecho: Implementar getters y setters
    
    // Implementar addEmpleado(Empleado e) - añadir empleado evitando duplicados
    
    //  Implementar removeEmpleado(Empleado e)
    
    // Hecho: Implementar toString()
}