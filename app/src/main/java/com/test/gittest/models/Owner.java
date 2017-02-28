
package com.test.gittest.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "Owners")
public class Owner extends Model {

    @Column(name = "Login")
    @SerializedName("login")
    @Expose
    private String login;

    @Column(name = "Type")
    @SerializedName("type")
    @Expose
    private String type;

    @Column(name = "Repo", onDelete = Column.ForeignKeyAction.CASCADE)
    public Repo repo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Owner() {
        super();
    }

    /**
     * 
     * @param login
     * @param type
     */
    public Owner(String login, String type) {
        super();
        this.login = login;
        this.type = type;
    }

    String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
