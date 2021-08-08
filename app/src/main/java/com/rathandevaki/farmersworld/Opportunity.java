package com.rathandevaki.farmersworld;


public class Opportunity {

    private String JobDescription;

    private String ProfilePhoto, UserID, UserName, JobDuration,Location,Salary,Vacancies;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public Opportunity() {
    }


    // Getter and setter method
    //userid
    public String getUserID() {
        return UserID;
    }
    public void setUserID(String UserID) {
        this.UserID = UserID;
    }
    //profle Photo
    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String ProfilePhoto) {
        this.ProfilePhoto = ProfilePhoto;
    }


    //duration
    public String getJobDuration() {
        return JobDuration;
    }

    public void setJobDuration(String JobDuration) {
        this.JobDuration = JobDuration;
    }
//usrname
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
//description
    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String JobDescription) {
        this.JobDescription = JobDescription;
    }

    public String getLocation()
    {
        return Location;
    }
    public void setLocation(String Location){
        this.Location=Location;
    }
    public String getSalary(){
        return Salary;
    }
    public void setSalary(String Salary){
        this.Salary=Salary;
    }
    public String getVacancies(){
        return Vacancies;
    }
    public void setVacancies(String Vacancies){
        this.Vacancies=Vacancies;
    }
}
