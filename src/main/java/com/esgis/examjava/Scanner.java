/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;

import static com.esgis.examjava.JpaMain.logger;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import static java.awt.SystemColor.info;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static javafx.scene.input.KeyCode.SEPARATOR;
import javax.persistence.EntityManager;

/**
 *
 * @author esperancia
 */
public class Scanner implements Runnable {
    File repository;
    String nommage;
    int frequence;
    int lot;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DAO dao = new DAO(DB.getEntityManager());
    private final static char SEPARATOR = '|';
    
    private static Date dateTraitement =  new Date();
    private static AtomicInteger nbreTraites = new AtomicInteger();
    private static AtomicInteger nbreEnCoursDeTraitement = new AtomicInteger();
    private static final ReentrantLock lock = new ReentrantLock();

    
    String[] lastFiles;
    boolean active = true;
    
    public Scanner(File repository, String nommage, int frequence, int lot) {
        this.repository = repository;
        this.nommage = nommage;
        this.frequence = frequence;
        this.lot = lot; 
    }
    
    public void snapshot(){
        lastFiles = repository.list();
        Arrays.sort(lastFiles);
    }
    
    public boolean check(String[] recent){
        Arrays.sort(recent);
        return Arrays.deepEquals(recent, lastFiles);
    }
    
    Predicate<String> p = new Predicate<String>(){

        @Override
        public boolean test(String t) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    };
    
    public List<String> detect(String[] list){
       //return Arrays.asList(list).stream().filter( s -> !Arrays.asList(lastFiles).contains(s)).collect(Collectors.toList());
        List<String> l = Arrays.asList(list);
        List<String> l2 = Arrays.asList(lastFiles);
        return l.stream().filter(s -> !l2.contains(s)).collect(Collectors.toList());
       // return l.stream().filter( t -> active == false).collect(Collectors.toList());
    }


    @Override
    public void run() {
        // début scann
        lastFiles = new String[0];
        
        while(lot != 0) {               //5 le lot le counter
            //System.out.println("Scan ...numero " + lot + " " + repository);
            String[] currentFiles;
            Pattern p = Pattern.compile(nommage);

            currentFiles = repository.list((File f, String name) -> p.matcher(name).matches());

            Arrays.toString(currentFiles);
            
            if(!check(currentFiles)){
                List<String> newFiles = detect(currentFiles);
                // Traiter csv
                newFiles.forEach((String filename) -> {
                    String file = repository + "/" + filename;    //file path repo + name
                    System.out.println("fichier en traitement : " + file);
                   
                    try {
                        traiterCsv(file);
                    } catch (IOException | ParseException ex) {
                        Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                lastFiles = currentFiles;
                
            }
            try{
                System.out.println("================================================== \n" + frequence);
                System.out.println("En attente... \n");
                Thread.sleep(frequence * 1000);        //ici avant delay de 3secondes. Mais frequence pr MAJ
            }catch(InterruptedException e){}
            
            --lot;
        }

        System.out.println("Scan terminé !");   // fin scann

    }
    
    public void stop(){
        active = false;
    }
  
    
    private void traiterCsv(String nomFichierCsv) throws FileNotFoundException, IOException, ParseException {
        File file = new File(nomFichierCsv);
        FileReader fr = new FileReader(file);

        CSVReader csvReader = new CSVReader(fr);    // je ne veux pas de saparateur

        List<String> data = new ArrayList<String>();
        List<Vol> vols = new ArrayList<Vol>();
        
        incrementNbreEnCours();

        String[] nextLine = null;
        while ((nextLine = csvReader.readNext()) != null) {
            int size = nextLine.length;

            // ligne vide
            if (size == 0) {
                continue;
            }
            String debut = nextLine[0].trim();
            if (debut.length() == 0 && size == 1) {
                continue;
            }

            // ligne de commentaire
            if (debut.startsWith("#")) {
                continue;
            }

            String dataLine = String.join(",", nextLine);
            //System.out.println("vol " + dataLine);
            data.add(dataLine);
        }

        for (String oneData : data) {
            String[] info = (new JpaMain()).analyserSaisie(oneData);
            String codeAeroDep = info[0];
            String codeAeroArr = info[1];
            int numVol = Integer.parseInt(info[5]);
            String refAvion = info[7];
            String refEquipage = info[8];
            int nbrePassagers = Integer.parseInt(info[4]);
            int poidsCharge = Integer.parseInt(info[6]);
            Date dateDep = sdf.parse(info[3]);
            Date dateArr = sdf.parse(info[2]);

            Vol vol = new Vol(dateDep, dateArr, codeAeroDep, codeAeroArr, numVol, refAvion, refEquipage, nbrePassagers, poidsCharge);
            //System.out.println("vol" + vol.toString());
            dao.procedureAjoutVolAvecControle(nomFichierCsv, vol);
        }
        
        System.out.println("================================================== \n");
        System.out.println("Date traitement: " + dateTraitement + " \n");
        System.out.println("Nombre Fichiers Traités: " + nbreTraites + " \n");
        System.out.println("Nombre Fichiers en cours: " + nbreEnCoursDeTraitement + " \n");
        System.out.println("================================================== \n");
                
        decrementNbreEnCours();
        incrementNbreTraites();

    }
    
       
    public static void incrementNbreTraites(){
        lock.lock();
        try{
            //System.out.println(Thread.currentThread().getName() + ": totoTraités :" + nbreTraites);
            nbreTraites.getAndIncrement();
        }finally{
            lock.unlock();
        }
    }
    
    
    public static void incrementNbreEnCours(){
        lock.lock();
        try{
            //System.out.println(Thread.currentThread().getName() + ": tataEncoursIncrement :" + nbreEnCoursDeTraitement);
            nbreEnCoursDeTraitement.getAndIncrement();
        }finally{
            lock.unlock();
        }
    }
    
    
    public static void decrementNbreEnCours(){
        lock.lock();
        try{
            //System.out.println(Thread.currentThread().getName() + ": totoEnCoursDecrement :" + nbreEnCoursDeTraitement);
            if (nbreEnCoursDeTraitement.get() > 0) {
                nbreEnCoursDeTraitement.getAndDecrement();
            }
        }finally{
            lock.unlock();
        }
    }
    
    
    /*
    private void traiterCsvMappingColumn(String nomFichierCsv) throws FileNotFoundException, IOException, ParseException {
        Path myPath = Paths.get(nomFichierCsv);
        CsvTransfer csvTransfer = new CsvTransfer();
        try {
            HeaderColumnNameMappingStrategy<Vol> ms = new HeaderColumnNameMappingStrategy<>();
            // ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
            ms.setType(Vol.class);

            Reader reader = Files.newBufferedReader(myPath);
            CsvToBean cb = new CsvToBeanBuilder(reader).withType(Vol.class)
                .withMappingStrategy(ms)
                .build();

            // System.out.println("toto "+cb.);

            csvTransfer.setCsvList(cb.parse()); 
            List<VolCsv> vols = csvTransfer.getCsvList();
            // List<Vol> vols = csvToBean.parse(strategy, csvReader);
            vols.forEach((VolCsv vol) -> {
                System.out.println("vol" + vol.toString());
                //dao.procedureAjoutVolAvecControle(vol);
            }); 
            reader.close();

        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    */
    
}