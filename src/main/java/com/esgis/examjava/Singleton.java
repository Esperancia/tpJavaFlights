/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author esperancia
 */


public class Singleton {
    
    private static Singleton instance = null;
   
    private static Date dateTraitement = new Date();
    private static int nbreTraites = 0;
    private static int nbreEnCoursDeTraitement = 0;

    protected Singleton() {
      
    }
   
    public static Singleton getInstance() {
        if(instance == null) {
           instance = new Singleton();
        }
        return instance;
    }
      
    public static Date getDateTraitement() {
        return dateTraitement;
    }

    public static void setDateTraitement(Date dateTraitement) {
        Singleton.dateTraitement = dateTraitement;
    }

    public static int getNbreTraites() {
        return nbreTraites;
    }

    public static void setNbreTraites() {
        // if (nbreEnCoursDeTraitement > 0)
        //    nbreEnCoursDeTraitement--;
        nbreTraites ++;
    }

    public static int getNbreEnCoursDeTraitement() {
        return nbreEnCoursDeTraitement;
    }

    public static void setNbreEnCoursDeTraitement() {
        nbreEnCoursDeTraitement++;
    }
     
    public static void resetNbreEnCoursDeTraitement() {
        nbreEnCoursDeTraitement = 0;
    }
    
    public static void resetNbreTraites() {
        nbreTraites = 0;
    }
    
    AtomicInteger nbreFichiersTraites = new AtomicInteger();
    AtomicInteger nbreFichiersEnCoursDeTraitement = new AtomicInteger();

    public int getNbreFichiersTraites() {
        return nbreFichiersTraites.get();
    }

    public void setNbreFichiersTraites() {
        nbreFichiersEnCoursDeTraitement.decrementAndGet();
        nbreFichiersTraites.incrementAndGet();
    }

    public int getNbreFichiersEnCoursDeTraitement() {
        System.out.println("================================================== \n");
        System.out.println("nbreFichiersEnCoursDeTraitement \n" + nbreFichiersEnCoursDeTraitement);
        System.out.println("================================================== \n");
                
        return nbreFichiersEnCoursDeTraitement.get();
    }

    public void setNbreFichiersEnCoursDeTraitement() {
        nbreFichiersEnCoursDeTraitement.incrementAndGet();
    }
      
    public void initNbreFichiersEnCoursDeTraitement() {
        this.nbreFichiersEnCoursDeTraitement.set(0);
    }

   
}