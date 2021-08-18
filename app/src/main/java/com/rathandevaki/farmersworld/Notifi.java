package com.rathandevaki.farmersworld;

public class Notifi {
    private String Note,Date;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public Notifi() {}


    // Getter and setter method
    public String getNote ()
    {
        return Note;
    }
    public void setNote(String note)
    {
        this.Note=note;
    }

    public String getDate(){
        return Date;
    }
    public void setDate(String Date)
    {
        this.Date=Date;
    }
}
