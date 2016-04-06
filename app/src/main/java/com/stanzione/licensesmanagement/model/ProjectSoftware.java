package com.stanzione.licensesmanagement.model;

/**
 * Created by Leandro Stanzione on 30/12/2015.
 */
public class ProjectSoftware {

    private int id;
    private String name;
    private String creationDate;
    private String modificationDate;
    private boolean activate;
    private int creationUserId;
    private int modificationUserId;
    private int projectId;
    private int softwareId;
    private String projectName;
    private String softwareName;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
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
