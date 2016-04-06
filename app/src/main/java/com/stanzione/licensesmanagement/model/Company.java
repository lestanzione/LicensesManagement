package com.stanzione.licensesmanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class Company implements Serializable{

    private int id;
    private String name;
    private String address;
    private String creationDate;
    private String modificationDate;
    private boolean activate;
    private int creationUserId;
    private int modificationUserId;
    private String creationUserName;
    private String modificationUserName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public int getCreationUserId() {
        return creationUserId;
    }

    public void setCreationUserId(int creationUserId) {
        this.creationUserId = creationUserId;
    }

    public int getModificationUserId() {
        return modificationUserId;
    }

    public void setModificationUserId(int modificationUserId) {
        this.modificationUserId = modificationUserId;
    }

    public String getCreationUserName() {
        return creationUserName;
    }

    public void setCreationUserName(String creationUserName) {
        this.creationUserName = creationUserName;
    }

    public String getModificationUserName() {
        return modificationUserName;
    }

    public void setModificationUserName(String modificationUserName) {
        this.modificationUserName = modificationUserName;
    }

}
