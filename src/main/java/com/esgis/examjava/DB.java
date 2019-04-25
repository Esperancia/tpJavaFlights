/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author esperancia
 */
public class DB {
    
    static EntityManagerFactory fcx; // parce que static sa declaration est static.
    
    private DB() {
        
    }
            
    static {
        fcx = Persistence.createEntityManagerFactory("examenJavaPU");
    }
    
    public static EntityManagerFactory getDBFactory() {
        return fcx;
    }
    
    public static EntityManager getEntityManager() { //EntityManager est le persistenceContext
        return fcx.createEntityManager();
    }
    
}
