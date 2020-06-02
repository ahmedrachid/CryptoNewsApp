package com.digger.app.model;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class DataPlot {

    private ArrayList<Entry> entries;
    private ArrayList<String> dates;

    public DataPlot(ArrayList<Entry> entries, ArrayList<String> dates) {
        this.entries = entries;
        this.dates = dates;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }
}
