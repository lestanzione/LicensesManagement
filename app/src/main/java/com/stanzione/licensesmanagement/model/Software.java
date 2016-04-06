package com.stanzione.licensesmanagement.model;

import java.io.Serializable;

/**
 * Created by Leandro Stanzione on 28/12/2015.
 */
public class Software implements Serializable{

    private int id;
    private String code;
    private String name;
    private String type;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
