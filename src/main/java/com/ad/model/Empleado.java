package com.ad.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Transient;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase Empleado - Entidad principal
 * 
 * Requisitos:
 * - idEmpleado: identificador autogenerado
 * - nombre: String
 * - fechaContrato: Date
 * - malaActuacion: boolean (NO debe persistirse, usar @Transient)
 * - direccion: Direccion embebida (@Embedded)
 * - departamento: relación ManyToOne con Departamento
 * - losProyectos: relación ManyToMany con Proyecto
 * 
 * Hecho: 
 * 1. Anotar la clase como @Entity
 * 2. Anotar idEmpleado con @Id y @GeneratedValue
 * 3. Anotar malaActuacion con @Transient
 * 4. Anotar direccion con @Embedded
 * 5. Anotar departamento con @ManyToOne (cascade=CascadeType.ALL)
 * 6. Anotar losProyectos con @ManyToMany (cascade, fetch)
 */
@Entity
@Table (name="Empleado")
public class Empleado implements Serializable {
    
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
 private Long idEmpleado;
    
@Column(nullable = false, length = 100) // No nulo, máx. 100 caracteres
private String nombre;

@Column(nullable =false)  //no nulo
private LocalDate fechaContrato;
    
@Transient
private boolean malaActuacion;
    
@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinColumn(name = "idDepartamento", nullable = false)
private Departamento departamento;
    
@ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}, // Propaga persist y merge
        fetch = FetchType.LAZY // Carga diferida (recomendado)
    )
    @JoinTable(
        name = "Empleado_Proyecto",
        joinColumns = @JoinColumn(name = "idEmpleado"),
        inverseJoinColumns = @JoinColumn(name = "idProyecto")
    )
        private Set<Proyecto> losProyectos = new HashSet<>();
    
    @Embedded
    private Direccion direccion;

    public Empleado() {}

    public Empleado(String nombre, LocalDate fechaContrato, Direccion direccion) {
        this.nombre = nombre;
        this.fechaContrato = fechaContrato;
        this.direccion = direccion;
    }
                //hecho: los getters
    public long getIdEmpleado(){
        return idEmpleado;
    }
    public String getNombre(){
        return nombre;
    }
    public LocalDate getFechaContrato(){
        return fechaContrato;
    }
    public Direccion getDireccion(){
        return direccion;
    }
    public boolean isMalaActuacion(){
        return malaActuacion;
    }
    public Departamento getDepartamento(){
        return departamento;
    }
    public Set<Proyecto> getLosProyectos(){
        return losProyectos;
    }
    //Hecho: los setters
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setFechaContrato(LocalDate fechaContrato) {
        this.fechaContrato = fechaContrato;
    }
    public void setMalaActuacion(boolean malaActuacion) {
        this.malaActuacion = malaActuacion;
    }
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    public void setLosProyectos(Set<Proyecto> losProyectos) {
        this.losProyectos = losProyectos;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }


    @Override
    public String toString() {
        return "{" +
            " idEmpleado='" + getIdEmpleado() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", fechaContrato='" + getFechaContrato() + "'" +
            ", malaActuacion='" + isMalaActuacion() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", losProyectos='" + getLosProyectos() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
    public void addProyecto(Proyecto p){
            this.losProyectos.add(p);
            p.getLosEmpleados().add(this); // si la relación es bidireccional

    }

    // Hecho: Implementar getters y setters
    // Implementar addProyecto(Proyecto p) - añadir proyecto evitando duplicados
    // Hecho: Implementar toString()
}