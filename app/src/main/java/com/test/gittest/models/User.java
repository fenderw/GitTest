package com.test.gittest.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by F on 25/02/17.
 * Git user DB Model
 */

@Table(name = "Users")
public class User extends Model {

    @Column(name = "Username")
    private String username;

    @Column(name = "Repo_type")
    private String repo_type;

    private List<Repo> repoList;

    public void setRepoList(List<Repo> repoList) {
        this.repoList = repoList;
    }

    public List<Repo> getRepoList() {
        return getMany(Repo.class, "User");
    }

    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepo_type() {
        return repo_type;
    }

    public void setRepo_type(String repo_type) {
        this.repo_type = repo_type;
    }

    // some methods to deal with our requests

    public static List<User> getUserNames() {
        return new Select()
                .from(User.class)
                .orderBy("Id DESC")
                .execute();
    }

    /**
     * get user by id
     * */
    public static User getUserById(long id) {
        return new Select()
                .from(User.class)
                .where("id=?", id)
                .executeSingle();
    }

    /**
     * get number of rows
     * */
    public static int getNumberOfRows() {
        return new Select()
                .from(User.class)
                .count();
    }

    /**
     *  get first row Id
     * */
    public static long getFirstRowId() {
        return new Select()
                .from(User.class)
                .orderBy("Id ASC")
                .executeSingle().getId();
    }

    /**
     *  delete row by Id
     * */
    public static void deleteUserById(long id) {
        new Delete()
                .from(User.class)
                .where("Id=?",id)
                .execute();
    }
}

