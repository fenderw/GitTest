
package com.test.gittest.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "Repos")
public class Repo extends Model {

    @Column(name = "Name")
    @SerializedName("name")
    @Expose
    private String name;

    @Column(name = "Full_name")
    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("owner")
    @Expose
    private Owner owner;

    @Column(name = "Description")
    @SerializedName("description")
    @Expose
    private String description;

    @Column(name = "Created_at")
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @Column(name = "Updated_at")
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @Column(name = "Stargazers_count")
    @SerializedName("stargazers_count")
    @Expose
    private Integer stargazersCount;

    @Column(name = "Language")
    @SerializedName("language")
    @Expose
    private String language;

    @Column(name = "User", onDelete = Column.ForeignKeyAction.CASCADE)
    public User user;

    /**
     * No args constructor for use in serialization
     */
    public Repo() {
        super();
    }

    /**
     * @param updatedAt
     * @param createdAt
     * @param description
     * @param name
     * @param owner
     * @param language
     * @param fullName
     * @param stargazersCount
     */
    public Repo(String name, String fullName, Owner owner, String description, String createdAt, String updatedAt, Integer stargazersCount, String language) {
        super();
        this.name = name;
        this.fullName = fullName;
        this.owner = owner;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stargazersCount = stargazersCount;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOwnerName() {
        return owner.getLogin();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
