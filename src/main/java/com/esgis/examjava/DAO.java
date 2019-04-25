/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;

import static com.esgis.examjava.JpaMain.fh;
import static com.esgis.examjava.JpaMain.logger;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author esperancia
 */
public class DAO {

    EntityManager em;
    DAO dao;
    DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static final Logger logger = JpaMain.logger;

    String tousLesVols = "select v from Vol v ";
    String unVol = "select v from Vol v where v.numVol = ?1"; //parametres positionnels

    public DAO(EntityManager em) {
        this.em = em;
    }

    public void ajouterVol(Date dateDep, Date dateArr, String codeAeroDep, String codeAeroArr, int numVol, String refAvion, String refEquipage, int nbrePassagers, int poidsCharge) {
        Vol vol = new Vol(dateDep, dateArr, codeAeroDep, codeAeroArr, numVol, refAvion, refEquipage, nbrePassagers, poidsCharge);
        EntityTransaction trx = em.getTransaction();
        trx.begin();
        em.persist(vol);
        trx.commit();
        System.out.println("Enregistrement effectué avec succès.\n");
    }

    public List<Vol> listerVol() {
        TypedQuery<Vol> query = em.createQuery(tousLesVols, Vol.class);
        return query.getResultList();
    }

    public Vol afficherVol(int numVol) {
        return em.createQuery(unVol, Vol.class)
                .setParameter(1, numVol) // ou .setParameter("design", numVol) //paramètre nominé
                .getSingleResult();
    }

    // methode generique
    public <T> List<T> listerGeneric(Class<T> c) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(c);
        Root<T> table = query.from(c);
        query.select(table);
        return em.createQuery(query).getResultList();
    }

    public <T> List<T> listerGenericSurUnChamps(Class<T> c, String field, String value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(c);
        Root<T> table = query.from(c);
        query.select(table).where(cb.equal(table.get(field), value));
        return em.createQuery(query).getResultList();
    }

    public <T> List<T> listerGenericSur3Champs(Class<T> c, String field1, String field2, String field3,
            String value1, String value2, String value3) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(c);
        Root<T> table = query.from(c);
        Predicate cond = cb.and(cb.equal(table.get(field1), value1), cb.equal(table.get(field2), value2), cb.equal(table.get(field3), value3));
        query.select(table).where(cond);
        return em.createQuery(query).getResultList();
    }

    public void procedureAjoutVolAvecControle(String[] info) throws ParseException {
        if (info.length < 9) {
            System.out.println("Tous les paramètres n'y sont pas. veuillez reprendre");
        } else {
            List<Vol> volsNum = listerGenericSurUnChamps(Vol.class, "numVol", info[4]);
            List<Vol> volsCriteres = listerGenericSur3Champs(Vol.class, "dateDep", "dateArr", "refAvion", info[0], info[1], info[5]);
            if (volsNum.isEmpty() && volsCriteres.isEmpty()) {
                try {
                    Date dateDep = sdf.parse(info[0]);
                    Date dateArr = sdf.parse(info[1]);
                    String codeAeroDep = info[2];
                    String codeAeroArr = info[3];
                    int numVol = Integer.parseInt(info[4]);
                    String refAvion = info[5];
                    String refEquipage = info[6];
                    int nbrePassagers = Integer.parseInt(info[7]);
                    int poidsCharge = Integer.parseInt(info[8]);
                    dao.ajouterVol(dateDep, dateArr, codeAeroDep, codeAeroArr, numVol, refAvion, refEquipage, nbrePassagers, poidsCharge);
                    System.out.println("Ajout effectué");
                } catch (ParseException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                logger.setUseParentHandlers(false);
                logger.info("Un enregistrement de vol à ce numéro existe ! Vol Numero " + info[4]
                        + " ou Référence Avion " + info[5] + "dates départ " + info[0] + " et arrivée " + info[1]);
                logger.info("============================================================ \n ");
                System.out.println("==========Erreur: consulter log file==========");
            }
        }
    }

    public void procedureAjoutVolAvecControle(String file, Vol vol) throws ParseException {
        List<Vol> volsNum = listerGenericSurUnChamps(Vol.class, "numVol", Integer.toString(vol.getNumVol()));
        List<Vol> volsCriteres = listerGenericSur3Champs(Vol.class, "dateDep", "dateArr", "refAvion", dateformat.format(vol.getDateDep()), dateformat.format(vol.getDateArr()), vol.getRefAvion());
        if (volsNum.isEmpty() && volsCriteres.isEmpty()) {
            Date dateDep = vol.getDateDep();
            Date dateArr = vol.getDateArr();
            String codeAeroDep = vol.getCodeAeroDep();
            String codeAeroArr = vol.getCodeAeroArr();
            int numVol = vol.getNumVol();
            String refAvion = vol.getRefAvion();
            String refEquipage = vol.getRefEquipage();
            int nbrePassagers = vol.getNbrePassagers();
            int poidsCharge = vol.getPoidsCharge();
            ajouterVol(dateDep, dateArr, codeAeroDep, codeAeroArr, numVol, refAvion, refEquipage, nbrePassagers, poidsCharge);
            System.out.println("Ajout effectué");
        } else {
            logger.setUseParentHandlers(false);
            logger.info("Ficher " + file + " \n");
            logger.info("Un enregistrement de vol à ce numéro existe ! Vol Numero " + vol.getNumVol()
                    + " ou Référence Avion " + vol.getRefAvion() + " dates départ " + vol.getDateDep() + " et arrivée " + vol.getDateArr());
            logger.info("============================================================ \n ");
            System.out.println("==========Erreur: consulter log file==========");
        }
    }

}
