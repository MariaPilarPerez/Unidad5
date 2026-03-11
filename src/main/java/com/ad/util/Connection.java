package com.ad.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * Clase de conexión a ObjectDB
 * Gestiona EntityManagerFactory y EntityManager
 */
public class Connection {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    private String nombreBD;
    
    public Connection(String nombre) {
        this.emf = null;
        this.em = null;
        this.nombreBD = nombre;
    }
    
    private void conectar() {  
        emf = Persistence.createEntityManagerFactory(nombreBD);
        try {
            em = emf.createEntityManager();
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void desconectar() {  
        if (em != null) em.close();
        if (emf != null) emf.close();
        em = null;
        emf = null;
    }
    
    public EntityManager getEM() {
        if (em == null) conectar();
        return em;
    }
}