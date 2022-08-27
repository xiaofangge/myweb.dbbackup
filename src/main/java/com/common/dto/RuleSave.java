package com.common.dto;




import java.util.ArrayList;

public class RuleSave {
    private Integer databaseId;
    private ArrayList<String> Sunday;
    private ArrayList<String> Monday;
    private ArrayList<String> Tuesday;
    private ArrayList<String> Wednesday;
    private ArrayList<String> Thursday;
    private ArrayList<String> Friday;
    private ArrayList<String> Saturday;
    private Integer backupCycle;

    public Integer getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Integer databaseId) {
        this.databaseId = databaseId;
    }

    public ArrayList<String> getSunday() {
        return Sunday;
    }

    public void setSunday(ArrayList<String> sunday) {
        Sunday = sunday;
    }

    public ArrayList<String> getMonday() {
        return Monday;
    }

    public void setMonday(ArrayList<String> monday) {
        Monday = monday;
    }

    public ArrayList<String> getTuesday() {
        return Tuesday;
    }

    public void setTuesday(ArrayList<String> tuesday) {
        Tuesday = tuesday;
    }

    public ArrayList<String> getWednesday() {
        return Wednesday;
    }

    public void setWednesday(ArrayList<String> wednesday) {
        Wednesday = wednesday;
    }

    public ArrayList<String> getThursday() {
        return Thursday;
    }

    public void setThursday(ArrayList<String> thursday) {
        Thursday = thursday;
    }

    public ArrayList<String> getFriday() {
        return Friday;
    }

    public void setFriday(ArrayList<String> friday) {
        Friday = friday;
    }

    public ArrayList<String> getSaturday() {
        return Saturday;
    }

    public void setSaturday(ArrayList<String> saturday) {
        Saturday = saturday;
    }

    public Integer getBackupCycle() {
        return backupCycle;
    }

    public void setBackupCycle(Integer backupCycle) {
        this.backupCycle = backupCycle;
    }
}
