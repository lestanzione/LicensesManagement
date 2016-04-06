package com.stanzione.licensesmanagement.model;

import java.io.Serializable;

/**
 * Created by Leandro Stanzione on 28/12/2015.
 */
public class Contact implements Serializable{

    private int id;
    private String firstName;
    private String lastName;
    private String title;
    private String email;
    private String telNumber;
    private String creationDate;
    private String modificationDate;
    private boolean activate;
    private int companyId;
    private int creationUserId;
    private int modificationUserId;
    private String companyName;
    private String creationUserName;
    private String modificationUserName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
