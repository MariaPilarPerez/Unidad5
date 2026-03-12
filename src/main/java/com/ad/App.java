package com.ad;

import com.ad.model.*;
import com.ad.util.Connection;
import com.ad.util.Leer;

import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
import javax.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

//import java.util.List;

/**
 * Aplicación principal - Gestión de Empleados, Departamentos y Proyectos
 * 
 * EJERCICIO: Completar las consultas JPQL indicadas
 */
public class App {

    private static Connection con;

    public static void main(String[] args) {
        con = new Connection("EmpleadosDepartamentoProyecto.odb");
        int opcion=10;
        while (opcion !=0 ) {
                System.out.println("1-Guardar objetos");
                System.out.println("2-Consulta proyectos sin empleados");
                System.out.println("3-Empleados sin departamento asignado");
                System.out.println("4-Empleados contratados en un año concreto");
                System.out.println("5-Empleado con mayor antigüedad");
                System.out.println("6-Empleados con número de proyectos en los que trabajan");
                //System.out.println("7-");
                //System.out.println("8-");
                opcion=Leer.leerEntero("Selecciona una opcion: ");
        
        switch (opcion) {
            case 1: fase1_GuardarObjetos(); break;
            case 2: consulta1_ProyectosSinEmpleados(); break;
            case 3: consulta2_EmpleadosSinDepartamento(); break;
            case 4: consulta3_EmpleadosDelAnyo(Leer.leerEntero("Introduzca el año: ")); break;
            case 5: consulta4_EmpleadoMasAntiguo(); break;
            case 6: consulta5_EmpleadosYNumeroProyectos(); break;
            default: 
        }
    }
        //fase1_GuardarObjetos();
        // Descomentar para probar cada fase:
        // fase1_GuardarObjetos();
        // fase2_MostrarElementos();
        
        // CONSULTAS A IMPLEMENTAR:
        // consulta1_ProyectosSinEmpleados();
        // consulta2_EmpleadosSinDepartamento();
        // consulta3_EmpleadosDelAnyo(1995);
        // consulta4_EmpleadoMasAntiguo();
        // consulta5_EmpleadosYNumeroProyectos();
    }

    // =====================================================
    // FASE 1: Guardar objetos (ya implementado)
    // =====================================================
    public static void fase1_GuardarObjetos() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();

        // Crear Empleados
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int i;
        for (i = 0; i < 10; i++) {
            Empleado e = new Empleado(
                Leer.leerTexto("Nombre del empleado: "),
                LocalDate.parse(Leer.leerTexto("Fecha de contratación (ej: 01/15/1995): "),formatter),
                new Direccion(
                    Leer.leerTexto("Calle: "),
                    Leer.leerEntero("Número: ")
                )
            );
            em.persist(e);
        }
        // Crear Departamentos
        for ( i=0; i<3; i++) {
            Departamento d = new Departamento(
                Leer.leerTexto("Nombre del Departamento: "),
                Leer.leerTexto("Sede: ")
            );
            em.persist(d);
            
        }
        // Crear Proyectos
        for (i = 0; i < 3; i++) {
            Proyecto p = new Proyecto(Leer.leerTexto("Descripción del Proyecto: "));
            em.persist(p);
        }
        em.getTransaction().commit();
    }

    // =====================================================
    // CONSULTAS A IMPLEMENTAR POR EL ALUMNO
    // =====================================================

    /**
     * CONSULTA 1: Proyectos sin empleados asignados
     * TODO: Implementar query JPQL que devuelva proyectos donde size(losEmpleados)=0
     */
    public static void consulta1_ProyectosSinEmpleados() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        /*TypedQuery<Empleado> tq = em.createQuery(
                    "SELECT e FROM Empleado e " +
                    "WHERE YEAR(e.fechaContrato) = :anyo", Empleado.class);
          tq.setParameter("anyo", 2025);
          List<Empleado> losEmpleados = tq.getResultList();  */
          em.getTransaction().commit();
    }

    /**
     * CONSULTA 2: Empleados sin departamento asignado
     * TODO: Implementar query JPQL que devuelva empleados donde departamento is null
     */
    public static void consulta2_EmpleadosSinDepartamento() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        
        // TODO: Crear TypedQuery<Empleado> con JPQL
        
        // TODO: Obtener resultados y mostrarlos
        
        em.getTransaction().commit();
    }

    /**
     * CONSULTA 3: Empleados contratados en un año concreto
     * TODO: Implementar query JPQL filtrando por año de fechaContrato
     */
    public static void consulta3_EmpleadosDelAnyo(int anyo) {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        
        // TODO: Crear TypedQuery<Empleado> con JPQL y parámetro :anyo
        
        // TODO: Obtener resultados y mostrarlos
        
        em.getTransaction().commit();
    }

    /**
     * CONSULTA 4: Empleado con mayor antigüedad
     * TODO: Implementar query JPQL ordenando por fechaContrato ASC y limitando a 1
     */
    public static void consulta4_EmpleadoMasAntiguo() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        
        // TODO: Crear TypedQuery<Empleado> ordenado por fecha
        // TODO: Usar setMaxResults(1) para obtener solo el primero
        
        em.getTransaction().commit();
    }

    /**
     * CONSULTA 5: Empleados con número de proyectos en los que trabajan
     * TODO: Obtener todos los empleados y mostrar cuántos proyectos tiene cada uno
     */
    public static void consulta5_EmpleadosYNumeroProyectos() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        
        // TODO: Obtener todos los empleados
        // TODO: Para cada empleado, mostrar nombre y losProyectos.size()
        
        em.getTransaction().commit();
    }
    public static void anadirEmpleadosaDepartamentos(){
        EntityManager em = con.getEM();
        em.getTransaction().begin();
    Departamento d = em.find(Departamento.class, 1);
        TypedQuery<Empleado> tq = em.createQuery(
                   "Select e FROM Empleado e WHERE YEAR(e.fechaContrato) = :anyo", Empleado.class);
                    tq.setParameter("anyo", 2026);
                    List<Empleado> lista = tq.getResultList();
                    for (Empleado e: lista) {
                    d.addEmpleado(e);
                    }

        d = em.find(Departamento.class, 2);
        tq = em.createQuery(
                   "Select e FROM Empleado e WHERE YEAR(e.fechaContrato) = :anyo", Empleado.class);
                    tq.setParameter("anyo", 2025);
                    lista = tq.getResultList();
                    for (Empleado e: lista) {
                    d.addEmpleado(e);
                    }
         tq = em.createQuery(
                "Select e FROM Empleado e WHERE YEAR(e.fechaContrato) < :anyo", Empleado.class);
                tq.setParameter("anyo", 2025);
             lista = tq.getResultList();
             for (Empleado e: lista) {
                 d.addEmpleado(e);
             }
             em.getTransaction().commit();
            }
}
/*TypedQuery<Object[]> tq = em.createQuery(
                    "SELECT e.nombre, e.fechaContrato, e.direccion.calle, e.direccion.numero " +
                    "FROM Empleado e " +
                    "WHERE YEAR(e.fechaContrato) = :anyo", Object[].class);
                    tq.setParameter("anyo", 2025)
                    Set<Empleado> losEmpleados =tq.getResultList();
                    */