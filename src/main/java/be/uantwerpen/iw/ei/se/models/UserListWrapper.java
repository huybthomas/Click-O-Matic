package be.uantwerpen.iw.ei.se.models;

import java.util.ArrayList;

/**
 * Created by Quinten on 17/12/2015.
 */
public class UserListWrapper {

    private ArrayList<User> users;

    public UserListWrapper() {
        this.users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
