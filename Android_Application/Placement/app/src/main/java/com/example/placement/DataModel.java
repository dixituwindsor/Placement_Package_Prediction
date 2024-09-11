package com.example.placement;

public class DataModel {
    private String cname;
    private String gender;
    private String sscp;
    private String hscp;
    private String degreep;
    private String degreet;
    private String workex;
    private String etestp;
    private String specialization;
    private String mbap;
    private String salary;
    private String location;
    private String bond;
    private String id;
    private String title;

    public DataModel() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public DataModel(String id, String title, String cname, String gender, String sscp, String hscp, String degreep, String degreet, String workex, String etestp, String specialization, String mbap, String salary, String location, String bond) {
        this.id = id;
        this.title = title;
        this.cname = cname;
        this.gender = gender;
        this.sscp = sscp;
        this.hscp = hscp;
        this.degreep = degreep;
        this.degreet = degreet;
        this.workex = workex;
        this.etestp = etestp;
        this.specialization = specialization;
        this.mbap = mbap;
        this.salary = salary;
        this.location = location;
        this.bond = bond;
    }

    // getter and setter methods

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }
    public void setCname(String name) {
        this.cname = name;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSscp() {
        return sscp;
    }
    public void setSscp(String sscp) {
        this.sscp = sscp;
    }

    public String getHscp() {
        return hscp;
    }
    public void setHscp(String hscp) {
        this.hscp = hscp;
    }

    public String getDegreep() {
        return degreep;
    }
    public void setDegreep(String degreep) {
        this. degreep= degreep;
    }

    public String getDegreet() {
        return degreet;
    }
    public void setDegreet(String degreet) {
        this.degreet = degreet;
    }

    public String getWorkex() {
        return workex;
    }
    public void setWorkex(String workex) {
        this.workex = workex;
    }

    public String getEtestp() {
        return etestp;
    }
    public void setEtestp(String etestp) {
        this.etestp = etestp;
    }

    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getMbap() {
        return mbap;
    }
    public void setMbap(String mbap) {
        this.mbap = mbap;
    }

    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getBond() {
        return bond;
    }
    public void setBond(String bond) {
        this.bond = bond;
    }
}
