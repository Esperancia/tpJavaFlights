/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.persistence.criteria.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;

/**
 *
 * @author esperancia
 */
public class JpaMain {
    
    Console cons;
    BufferedReader bf;
    DAO dao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static final Logger logger = Logger.getLogger("MyLog");  
    static FileHandler fh;
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws ParseException {
        // TODO code application logic here
        try {  
            fh = new FileHandler("/Applications/FichiersNonSynchro/master_irt/m2irt/Examens/examjava/src/main/java/com/esgis/utils/MyLog.log", true);  
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);
        } catch (SecurityException|IOException e) {  
            e.printStackTrace();  
        }
        
        JpaMain jpaMain = new JpaMain();
        jpaMain.cons = System.console();
        jpaMain.bf = new BufferedReader(new InputStreamReader(System.in));
        jpaMain.sdf = new SimpleDateFormat("yyyy-MM-dd");
        jpaMain.dao = new DAO(DB.getEntityManager());
        do {
            //jpaMain.afficherMenu();
            System.out.println("Tapez 0 pour quitter le programme!");
            try {
                jpaMain.scannerDossier();
                UpdateData updateCallable = new UpdateData();
                ExecutorService updateDataExecutor = Executors.newSingleThreadExecutor(
                        new ThreadFactoryBuilder()
                                .setDaemon(true)
                                .build()
                );

                FutureTask<String> taskUpdateData = new FutureTask<>(updateCallable);
                updateDataExecutor.execute(taskUpdateData);
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(JpaMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            jpaMain.traiterSaisie();
        } while(true);   
    }
      
    public void afficherMenu () {
        StringWriter menu = new StringWriter();
        menu.append("0. Quitter \n");
        menu.append("1. Ajouter vol manuellement \n");
        menu.append("2. Scanner les dossiers pour enregistrer les vols \n");
        System.out.println(menu.toString());
    }
    
    public void traiterSaisie() throws ParseException {
        try {
            String entree = bf.readLine();
            int k = 0;
            
            try{
                k = Integer.parseInt(entree);
                switch(k){
                case 0:
                    System.out.println("Aurevoir!");
                    System.exit(0);
                case 1:
                    System.out.println("Entrez les informations du vol: datedep, dateArrivee, "
                            + "codeAeroDep, codeAeroArr, numVol, référenceAvion, référenceEquipage, "
                            + "nbrePassagers, et poids chargement");
                    traiterAjoutVol();
                    break;
                case 2:
                    //System.out.println("Patientez");
                    scannerDossier();
                    break;
                default:
                    System.out.printf("entrée invalide [%d]%n", k);
                }
            } catch(IOException | NumberFormatException e ){
                System.out.printf("une erreur s'est produite. \n" + e.toString());
            }  
            catch(InterruptedException e ){
                System.exit(0);
            } 
        } catch (IOException ex) {
            System.out.printf("une erreur s'est glissée dans votre choix. veuillez reprendre \n");
        }
    }
          
    public void traiterAjoutVol() throws ParseException{
        String entree;
        try {
            entree = bf.readLine();
            String[] info = analyserSaisie(entree);
            dao.procedureAjoutVolAvecControle(info);  // j'ai finalement fait en méthode polymorphe
            //Thread.sleep(1000);
            afficherMenu();
            traiterSaisie();
        } catch (IOException ex) {
            System.out.println("Erreur. Vérifiez les valeurs que vous entrez !" + ex.toString());
        }
    }
    
    public String [] analyserSaisie (String entree) {
        return entree.trim().split("\\|");
    }

    public void scannerDossier () throws InterruptedException, IOException {
        File dossier;
        String nommage;
        int frequence;
        int lot;
        try {
           //get file content in the form of string
            File configXml = new File("/Applications/FichiersNonSynchro/master_irt/m2irt/Examens/examjava/src/main/java/com/esgis/utils/config.xml");
            //initialize jaxb classes
            JAXBContext context = JAXBContext.newInstance(Traitements.class);
            Unmarshaller un = context.createUnmarshaller();
            //convert to desired object
            Traitements traitementData = (Traitements)un.unmarshal(configXml);
            List<Flux> fluxTraitement = traitementData.getFlux();
            //iterate over object
            for(Flux f: fluxTraitement){
                dossier = new File(f.getChemin());
                nommage = f.getNommage();
                frequence = Integer.parseInt(f.getFrequence());
                lot = Integer.parseInt(f.getLot());
                
                Scanner scanDossier = new Scanner(dossier, nommage, frequence, lot);
                Thread thrdScanDossier = new Thread(scanDossier);
                thrdScanDossier.start();
            }
        } catch (JAXBException ex) {
            Logger.getLogger(JpaMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 
    /**
    * Reads file content
    * @param filePath
    * @return
    */
    public String getConfigFileContent(String filePath) {
        FileReader fr = null;
        char[] buffer = new char[1024];
        StringBuffer fileContent = new StringBuffer();
        try {
            fr = new FileReader(filePath);
            int i = 0;
            while ((i = fr.read(buffer)) != -1) {
                fileContent.append(new String(buffer));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent.toString();
    }
    
    public List<File> getFluxFiles(String dossier, String substring) {
        List<File> listeFichiers = new ArrayList<>();
        return listeFichiers;
    }

    public void raffraichirDonnees () {
        
    }

}