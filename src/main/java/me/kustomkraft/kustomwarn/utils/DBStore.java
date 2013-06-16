package me.kustomkraft.kustomwarn.utils;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "warnings")
public class DBStore {

    @Id
    private int id;

    @NotEmpty
    private Date date;
    @NotNull
    private String offenderName;
    @Length(max = 30)
    @NotEmpty
    private String name;

    @NotEmpty

    private String test;

    private String warningReason;

    public void setOffenderName(String offenderName){
        this.offenderName = offenderName;
    }

    public String getOffenderName(){
        return offenderName;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getWarningReason() {
        return warningReason;
    }

    public void setWarningReason(String warningReason) {
        this.warningReason = warningReason;
    }

    public String getDate() {
        return String.valueOf(date);
    }

    public void setDate(String date) {
        this.date = new Date();
    }
}
