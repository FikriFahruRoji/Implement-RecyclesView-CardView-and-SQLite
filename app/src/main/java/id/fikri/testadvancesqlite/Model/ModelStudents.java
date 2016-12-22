package id.fikri.testadvancesqlite.Model;

/**
 * Created by fikri on 21/12/16.
 */

public class ModelStudents {
    private String id, name, surename;

    public ModelStudents() {
    }

    public ModelStudents(String id, String name, String surename) {
        this.id = id;
        this.name = name;
        this.surename = surename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }
}
