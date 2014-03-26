package com.stronghearttarot.kustomwarn.utils;

import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "kustom_warn")
@SuppressWarnings("unused")
public class Warnings
{

    @Id
    private int id;

    @NotNull
    private String warningNumber;

    @NotNull
    private String playerName;

    @NotNull
    private String adminName;

    private String warningReason;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getWarningNumber()
    {
        return warningNumber;
    }

    public void setWarningNumber(String warningNumber)
    {
        this.warningNumber = warningNumber;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public String getAdminName()
    {
        return adminName;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }

    public String getWarningReason()
    {
        return warningReason;
    }

    public void setWarningReason(String warningReason)
    {
        this.warningReason = warningReason;
    }
}