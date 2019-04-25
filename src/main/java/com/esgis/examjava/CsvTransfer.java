/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esgis.examjava;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esperancia
 */
public class CsvTransfer {

    private List<String[]> csvStringList;

    private List<VolCsv> csvList;

    public CsvTransfer() {}

    public List<String[]> getCsvStringList() {
        if (csvStringList != null) return csvStringList;
        return new ArrayList<String[]>();
    }

    public void addLine(String[] line) {
        if (this.csvList == null) this.csvStringList = new ArrayList<>();
        this.csvStringList.add(line);
    }

    public void setCsvStringList(List<String[]> csvStringList) {
        this.csvStringList = csvStringList;
    }

    public void setCsvList(List<VolCsv> csvList) {
        this.csvList = csvList;
    }

    public List<VolCsv> getCsvList() {
        if (csvList != null) return csvList;
        return new ArrayList<VolCsv>();
    }
}