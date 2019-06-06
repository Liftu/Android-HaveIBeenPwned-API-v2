package com.example.td1.Model;

import java.util.List;

public class Breaches {
    private String Name;
    private String Title;
    private String Domain;
    private String BreachDate;
    private String AddedDate;
    private int PwnCount;
    private String Description;
    private String LogoPath;
    private List<String> DataClasses;
    private boolean IsVerified;
    private boolean IsFabricated;
    private boolean IsSensitive;
    private boolean IsRetired;
    private boolean IsSpamList;

    public void setName(String name) {
        Name = name;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public void setBreachDate(String breachDate) {
        BreachDate = breachDate;
    }

    public void setAddedDate(String addedDate) {
        AddedDate = addedDate;
    }

    public void setPwnCount(int pwnCount) {
        PwnCount = pwnCount;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setLogoPath(String logoPath) {
        LogoPath = logoPath;
    }

    public void setDataClasses(List<String> dataClasses) {
        DataClasses = dataClasses;
    }

    public void setVerified(boolean verified) {
        IsVerified = verified;
    }

    public void setFabricated(boolean fabricated) {
        IsFabricated = fabricated;
    }

    public void setSensitive(boolean sensitive) {
        IsSensitive = sensitive;
    }

    public void setRetired(boolean retired) {
        IsRetired = retired;
    }

    public void setSpamList(boolean spamList) {
        IsSpamList = spamList;
    }

    public String getName() {
        return Name;
    }

    public String getTitle() {
        return Title;
    }

    public String getDomain() {
        return Domain;
    }

    public String getBreachDate() {
        return BreachDate;
    }

    public String getAddedDate() {
        return AddedDate;
    }

    public int getPwnCount() {
        return PwnCount;
    }

    public String getDescription() {
        return Description;
    }

    public String getLogoPath() {
        return LogoPath;
    }

    public List<String> getDataClasses() {
        return DataClasses;
    }

    public boolean isVerified() {
        return IsVerified;
    }

    public boolean isFabricated() {
        return IsFabricated;
    }

    public boolean isSensitive() {
        return IsSensitive;
    }

    public boolean isRetired() {
        return IsRetired;
    }

    public boolean isSpamList() {
        return IsSpamList;
    }
}