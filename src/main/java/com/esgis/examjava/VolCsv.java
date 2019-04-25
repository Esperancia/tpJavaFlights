/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author esperancia
 */ 
public class VolCsv {
 
    @CsvBindByName(column = "datedep")
    private Date dateDep;
    
    @CsvBindByName(column = "datearr") 
    private Date dateArr;
    
    @CsvBindByName(column = "codeaerodep")
    private String codeAeroDep;
    
    @CsvBindByName(column = "codeaeroarr")
    private String codeAeroArr;
    
    @CsvBindByName(column = "numvol")
    private int numVol;
    
    @CsvBindByName(column = "refavion")
    private String refAvion;
    
    @CsvBindByName(column = "refequipage")
    private String refEquipage;
    
    @CsvBindByName(column = "nbrepassagers")
    private int nbrePassagers;
    
    @CsvBindByName(column = "poidscharge")
    private int poidsCharge;
    
    

    public VolCsv() {
        
    }

    public VolCsv(Date dateDep, Date dateArr, String codeAeroDep, String codeAeroArr, int numVol, String refAvion, String refEquipage, int nbrePassagers, int poidsCharge) {
        this.dateDep = dateDep;
        this.dateArr = dateArr;
        this.codeAeroDep = codeAeroDep;
        this.codeAeroArr = codeAeroArr;
        this.numVol = numVol;
        this.refAvion = refAvion;
        this.refEquipage = refEquipage;
        this.nbrePassagers = nbrePassagers;
        this.poidsCharge = poidsCharge;
    }

    
 
    public Date getDateDep() {
        return dateDep;
    }

    public void setDateDep(Date dateDep) {
        this.dateDep = dateDep;
    }

    public Date getDateArr() {
        return dateArr;
    }

    public void setDateArr(Date dateArr) {
        this.dateArr = dateArr;
    }

    public String getCodeAeroDep() {
        return codeAeroDep;
    }

    public void setCodeAeroDep(String codeAeroDep) {
        this.codeAeroDep = codeAeroDep;
    }

    public String getCodeAeroArr() {
        return codeAeroArr;
    }

    public void setCodeAeroArr(String codeAeroArr) {
        this.codeAeroArr = codeAeroArr;
    }

    public int getNumVol() {
        return numVol;
    }

    public void setNumVol(int numVol) {
        this.numVol = numVol;
    }

    public String getRefAvion() {
        return refAvion;
    }

    public void setRefAvion(String refAvion) {
        this.refAvion = refAvion;
    }

    public String getRefEquipage() {
        return refEquipage;
    }

    public void setRefEquipage(String refEquipage) {
        this.refEquipage = refEquipage;
    }

    public int getNbrePassagers() {
        return nbrePassagers;
    }

    public void setNbrePassagers(int nbrePassagers) {
        this.nbrePassagers = nbrePassagers;
    }

    public int getPoidsCharge() {
        return poidsCharge;
    }

    public void setPoidsCharge(int poidsCharge) {
        this.poidsCharge = poidsCharge;
    }
    
    
    

    @Override
    public String toString() {
        return "examenjava.Vol[ refavion=" + refAvion + " ]";
    }

    
}
