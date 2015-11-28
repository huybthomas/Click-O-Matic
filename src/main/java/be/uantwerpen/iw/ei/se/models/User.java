package be.uantwerpen.iw.ei.se.models;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Entity
public class User extends MyAbstractPersistable<Long>
{
    @Size(min=2, max=30)
    @NotNull
    private String firstName;

    @Size(min=2, max=30)
    @NotNull
    private String lastName;

    @Size(min=2, max=30)
    @NotNull
    private String userName;

    @Size(min=4, max=12)
    @NotNull
    private String password;

    @ManyToMany
    @JoinTable(
            name="USER_TEST",
            joinColumns={
                    @JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                    @JoinColumn(name="FITTS_TEST_ID", referencedColumnName="ID")})
    private List<FittsTest> tests;

    @ManyToMany
    @JoinTable(
            name="USER_ROLE",
            joinColumns={
                @JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                @JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

    public User()
    {
        this.firstName = "";
        this.lastName = "";
        this.userName = "";
        this.password = "";

        this.roles = new ArrayList<>();
    }

    public User(String firstName, String lastName, String userName, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;

        this.roles = new ArrayList<>();
    }

    public User(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = "";
        this.password = "";

        this.roles = new ArrayList<>();
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<Role> getRoles()
    {
        return this.roles;
    }

    public void addTest(FittsTest test) {
        tests.add(test);
    }

    public FittsTest getTest(int number) {
        return this.tests.get(number);
    }

    public List<FittsTest> getTests() {
        return this.tests;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
        {
            return true;
        }

        if(object == null || this.getClass() != object.getClass())
        {
            return false;
        }

        User user = (User) object;

        return this.userName.equals(user.getUserName());
    }
}
