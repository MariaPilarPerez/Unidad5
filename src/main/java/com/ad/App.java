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
            //case 7: anadirEmpleadosaDepartamentos(); break;   //ya los he añadido
            //case 8: nuevosdatos(); break;
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
    public static void fase1_GuardarObjetos() {   //ok, esto funciona
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
     * Hecho: Implementar query JPQL que devuelva proyectos donde size(losEmpleados)=0
     */
    public static void consulta1_ProyectosSinEmpleados() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        TypedQuery<Proyecto> tq = em.createQuery(
                    "SELECT p FROM Proyecto p WHERE SIZE(p.losEmpleados) = 0", Proyecto.class);
          List<Proyecto> losProyectos = tq.getResultList();  
          System.out.println("-------Proyectos sin empleados asignados--------");
          for(Proyecto p: losProyectos){
            System.out.println("idProyecto: " + p.getIdProyecto()+
            "\nNombre Proyecto: "+p.getDescripcion() +"\nDirector Proyecto: ");
            try {
                System.out.println(p.getDirectorProyecto().getNombre());
            } 
            catch (NullPointerException exc)
            { 
                System.out.print("No hay Director de Proyecto");
            }
                System.out.println(":  No tiene Empleados asignados");
            }
            em.getTransaction().commit();
        
    }

    /**
     * CONSULTA 2: Empleados sin departamento asignado
     * Hecho: Implementar query JPQL que devuelva empleados donde departamento is null
     */
    public static void consulta2_EmpleadosSinDepartamento() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        TypedQuery<Empleado> tq=em.createQuery("select e from Empleado e where e.departamento is null",Empleado.class);
        List<Empleado> empleados = tq.getResultList();
        if (empleados.size()>0){
            System.out.println("---Empleados sin departamento asignado---");
            for(Empleado e: empleados){
            System.out.println(e.toString());
            }
        } else {
                System.out.println("Todos los empleados tienen departamento asignado");
            }
        //Hecho: Crear TypedQuery<Empleado> con JPQL
        // Hecho: Obtener resultados y mostrarlos
        em.getTransaction().commit();
    }

    /**
     * CONSULTA 3: Empleados contratados en un año concreto
     * Hecho: Implementar query JPQL filtrando por año de fechaContrato
     */
    public static void consulta3_EmpleadosDelAnyo(int anyo) {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        TypedQuery<Empleado> tq = em.createQuery("Select e from Empleado e where year(fechaContrato) = :aniocont", Empleado.class);
        tq.setParameter("aniocont", anyo);
        List<Empleado> empl= tq.getResultList();
        System.out.println("------Lista de Empleados contratados en "+anyo);
        if (empl.size() > 0) {
            for (Empleado e: empl){
            System.out.println(e.toString());
            }
        }else System.out.println("No hay empleados");
            
        
        // Hecho: Crear TypedQuery<Empleado> con JPQL y parámetro :anyo
        // Hecho: Obtener resultados y mostrarlos
        
        em.getTransaction().commit();
    }

    /**
     * CONSULTA 4: Empleado con mayor antigüedad
     * Hecho: Implementar query JPQL ordenando por fechaContrato ASC y limitando a 1
     */
    public static void consulta4_EmpleadoMasAntiguo() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        TypedQuery<Empleado> tq=em.createQuery("select e from Empleado e order by e.fechaContrato asc", Empleado.class);
        tq.setMaxResults(1);
        Empleado e=tq.getSingleResult();
        System.out.println("El Empleado más antiguo es: "+e.getNombre()+", contratado el día: "+e.getFechaContrato());

        // Hecho: Crear TypedQuery<Empleado> ordenado por fecha
        // Hecho: Usar setMaxResults(1) para obtener solo el primero
        
        em.getTransaction().commit();
    }

    /**
     * CONSULTA 5: Empleados con número de proyectos en los que trabajan
     * Hecho: Obtener todos los empleados y mostrar cuántos proyectos tiene cada uno
     */
    public static void consulta5_EmpleadosYNumeroProyectos() {
        EntityManager em = con.getEM();
        em.getTransaction().begin();
        TypedQuery<Empleado> tq=em.createQuery("Select e from Empleado e", Empleado.class);
        List<Empleado> listaempleados=tq.getResultList();
        System.out.println("-----Empleados y num. de proyectos en los que trabaja-----");
        for (Empleado e: listaempleados){
            System.out.println("Empleado: "+e.getNombre()+", proyectos en los que trabaja: "+e.getLosProyectos().size());
        }
        em.getTransaction().commit();
        // Hecho: Obtener todos los empleados
        // Hecho: Para cada empleado, mostrar nombre y losProyectos.size()
        
        
    }
    public static void anadirEmpleadosaDepartamentos(){
        EntityManager em = con.getEM();
        em.getTransaction().begin();
    Departamento d = em.find(Departamento.class, 1);
        TypedQuery<Empleado> tq = em.createQuery(
                   "Select e FROM Empleado e WHERE YEAR(e.fechaContrato) < :anyo", Empleado.class);
                    tq.setParameter("anyo", 2024);
                    List<Empleado> lista = tq.getResultList();
                    for (Empleado e: lista) {
                    d.addEmpleado(e);
                    }
                    System.out.println(d.toString());
                    System.out.println("Asignados Empleados del año <2024");
                 d = em.find(Departamento.class, 2);
                tq = em.createQuery(
                   "Select e FROM Empleado e WHERE YEAR(e.fechaContrato) = :anyo", Empleado.class);
                    tq.setParameter("anyo", 2025);
                    lista = tq.getResultList();
                    for (Empleado e: lista) {
                    d.addEmpleado(e);
                    }
                  System.out.println(d.toString());
                  System.out.println("Asignados Empleados del año 2025");
                 d = em.find(Departamento.class, 3);
                 tq = em.createQuery(
                 "Select e FROM Empleado e WHERE YEAR(e.fechaContrato) < :anyo", Empleado.class);
                 tq.setParameter("anyo", 2025);
                    lista = tq.getResultList();
                    for (Empleado e: lista) {
                         d.addEmpleado(e);
                     }
                     System.out.println(d.toString());
             System.out.println("Asignados Empleados anteriores a 2025");
             em.getTransaction().commit();
            }
            
            public static void anadeDatosaProyecto(){
                EntityManager em = con.getEM();
                em.getTransaction().begin();
                
                Proyecto p = em.find(Proyecto.class, 3);
                TypedQuery<Empleado> tq = em.createQuery(
                "Select e FROM Empleado e WHERE YEAR(e.fechaContrato) = :anyo", Empleado.class);
                tq.setParameter("anyo", 2024);
                List<Empleado> lista = tq.getResultList();
                for (Empleado x: lista){
                    p.addEmpleadoProyecto(x);
                }
                em.getTransaction().commit();
            }

                  
                   
           
            public static void nuevosdatos(){  //nuevos datos
                EntityManager em = con.getEM();
                em.getTransaction().begin();
                //Empleados y direcciones
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Empleado e1=new Empleado("Naira Lopez",
                LocalDate.parse("01/01/2021",formatter),
                 new Direccion("La Sierra", 2, 'A'));
                em.persist(e1);
                 Empleado e2=new Empleado("Pilar Perez",
                LocalDate.parse("01/02/2022",formatter),
                 new Direccion("Valle de Broto", 4, '5')); 
                 em.persist(e2);
                 Empleado e3=new Empleado("Lucas Gonzalez",
                LocalDate.parse("01/03/2022",formatter),
                 new Direccion("La Gasca", 7, 'A'));
                 em.persist(e3);
                Empleado e4=new Empleado("Javier Herrera",
                LocalDate.parse("01/04/2023",formatter),
                 new Direccion("Valle de Gistain", 4, '5')); 
                 em.persist(e4);
                Empleado e5=new Empleado("Ana María Benedi",
                LocalDate.parse("01/05/2023",formatter),
                 new Direccion("Sierra de Guara", 2, 'A'));
                 em.persist(e5);
                Empleado e6=new Empleado("Carlos Arguilas",
                LocalDate.parse("01/06/2024",formatter),
                 new Direccion("Los Danzantes", 4, '5')); 
                 em.persist(e6);
                Empleado e7=new Empleado("Belen Abad",
                LocalDate.parse("01/07/2024",formatter),
                 new Direccion("Paseo la mina", 2, 'A'));
                 em.persist(e7);
                Empleado e8=new Empleado("Francisco Lujan",
                LocalDate.parse("01/08/2024",formatter),
                 new Direccion("Huerta Alta", 4, '5')); 
                 em.persist(e8);
                 Empleado e9=new Empleado("Marta Brusca",
                LocalDate.parse("01/09/2025",formatter),
                 new Direccion("La Saca", 2, 'A'));
                 em.persist(e9);
                Empleado e10=new Empleado("Antonio Pitarque",
                LocalDate.parse("01/10/2025",formatter),
                 new Direccion("Valle de Hecho", 4, '5')); 
                 em.persist(e10);
                 
                // nuevos Departamentos
                
                Departamento d1=new Departamento("Fabricacion-AF", "Zaragoza");
                Departamento d2=new Departamento("Calidad-AF", "Zaragoza");
                Departamento d3=new Departamento("Almacen-AF", "Zaragoza-A1");
                em.persist(d1);
                em.persist(d2);
                em.persist(d3);
                
                //nuevos proyectos
                Proyecto p1=new Proyecto("Electrico-AF");
                Proyecto p2=new Proyecto("Calidad-3F");
                Proyecto p3=new Proyecto("Mejoras-Tech");
                Proyecto p4=new Proyecto("Mejoras-AF");
                em.persist(p1);
                em.persist(p2);
                em.persist(p3);
                em.persist(p4);
                    // asignar empleados a departamentos
                d1.addEmpleado(e1);
                d1.addEmpleado(e2);
                d1.addEmpleado(e6);
                d1.addEmpleado(e7);
                d2.addEmpleado(e3);
                d2.addEmpleado(e4);
                d2.addEmpleado(e10);
                d3.addEmpleado(e8);
                d3.addEmpleado(e9); 
                
                //asignar empleados como directores de proyecto
                p1.setDirectorProyecto(e10);
                p2.setDirectorProyecto(e7);
                p3.setDirectorProyecto(e3);
                
                //asignar empleados a proyectos
                
                p1.addEmpleadoProyecto(e1);
                p1.addEmpleadoProyecto(e3);
                p1.addEmpleadoProyecto(e7);
                p1.addEmpleadoProyecto(e8);
                p1.addEmpleadoProyecto(e10);
                p2.addEmpleadoProyecto(e1);
                p2.addEmpleadoProyecto(e2);
                p2.addEmpleadoProyecto(e3);
                p2.addEmpleadoProyecto(e4);
                p2.addEmpleadoProyecto(e5);
                p2.addEmpleadoProyecto(e7);
                p2.addEmpleadoProyecto(e10);
                p3.addEmpleadoProyecto(e10);
                p3.addEmpleadoProyecto(e9);
                p3.addEmpleadoProyecto(e6);
                em.getTransaction().commit();

            }


}
