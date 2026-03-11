package com.ad.model;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Clase Proyecto - Entidad
 * 
 * Requisitos:
 * - idProyecto: identificador autogenerado
 * - descripcion: String
 * - directorProyecto: relación OneToOne con Empleado (un empleado solo puede ser jefe de un proyecto)
 * - losEmpleados: relación ManyToMany con Empleado (empleados que participan)
 * 
 * TODO:
 * 1. Anotar la clase como @Entity
 * 2. Anotar idProyecto con @Id y @GeneratedValue
 * 3. Anotar directorProyecto con @OneToOne (cascade)
 * 4. Anotar losEmpleados con @ManyToMany (cascade, fetch)
 */
@Entity
@Table (name="Proyecto")

public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long idProyecto;
    
    @Column(nullable=false)
    private String descripcion;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idDirectorProyecto", nullable = false)
    private Empleado directorProyecto;
    
    
    @ManyToMany(
    cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    fetch = FetchType.LAZY)
    @JoinTable(
        name = "Empleado_Proyecto",
        joinColumns = @JoinColumn(name = "idProyecto"),
        inverseJoinColumns = @JoinColumn(name = "idEmpleado")
    )
    private Set<Empleado> losEmpleados = new HashSet<>();

    public Proyecto() {}

    public Proyecto(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdProyecto() {
        return this.idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empleado getDirectorProyecto() {
        return this.directorProyecto;
    }

    public void setDirectorProyecto(Empleado directorProyecto) {
        this.directorProyecto = directorProyecto;
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
            " idProyecto='" + getIdProyecto() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", directorProyecto='" + getDirectorProyecto() + "'" +
            ", losEmpleados='" + getLosEmpleados() + "'" +
            "}";
    }
    public void addEmpleadoProyecto(Empleado e){
        this.losEmpleados.add(e);
        e.getLosProyectos().add(this);
    }

    // Hecho: Implementar getters y setters
    
    // TODO: Implementar addEmpleadoProyecto(Empleado e) - añadir empleado evitando duplicados
    
    // Hecho: Implementar toString()
}