
package com.esgis.examjava;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author esperancia
 */
public class UpdateData implements Callable<String> {
    DAO dao = new DAO(DB.getEntityManager());
    private static final AtomicBoolean runMAJ = new AtomicBoolean(true);
    private long delay = 3;         //3s temps quasi-reel
    @Override
    public String call() throws Exception {
        try {
            while (runMAJ.get()) {
                // rafraichir donnees BD chaque 3s
                dao = new DAO(DB.getEntityManager());
                List<Vol> listeVols = dao.listerGeneric(Vol.class);
                listeVols.forEach((Vol vol)->{
                    System.out.println(vol.toString());
                });
                Thread.sleep(delay * 1000);  // Pause
            }
        }
        finally
        {

        }
        return "done";
    }
}