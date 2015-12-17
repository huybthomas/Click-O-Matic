package be.uantwerpen.iw.ei.se.models;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
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
            name="USER_ROLE",
            joinColumns={
                @JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                @JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

    @ManyToMany
            @JoinTable(
            name="USER_TEST",
            joinColumns={
                    @JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                    @JoinColumn(name="FITTS_TEST_ID", referencedColumnName="ID")})
    private List<FittsTest> tests;

    @OneToMany
    @JoinTable(
            name="USER_RESULT",
            joinColumns={
                    @JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={
                    @JoinColumn(name="FITTS_RESULT_ID", referencedColumnName="ID")})
    private List<FittsResult> results;

    public User()
    {
        this.firstName = "";
        this.lastName = "";
        this.userName = "";
        this.password = "";

        this.roles = new ArrayList<Role>();
        this.tests = new ArrayList<FittsTest>();
        this.results = new ArrayList<FittsResult>();
    }

    public User(String firstName, String lastName, String userName, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;

        this.roles = new ArrayList<Role>();
        this.tests = new ArrayList<FittsTest>();
        this.results = new ArrayList<FittsResult>();
    }

    public User(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = "";
        this.password = "";

        this.roles = new ArrayList<Role>();
        this.tests = new ArrayList<FittsTest>();
        this.results = new ArrayList<FittsResult>();
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

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public List<FittsTest> getTests()
    {
        return this.tests;
    }

    public void setTests(List<FittsTest> tests)
    {
        this.tests = tests;
    }

    public List<FittsResult> getResults()
    {
        return this.results;
    }

    public void setResults(List<FittsResult> results)
    {
        this.results = results;
    }

    public FittsTest getTest(String ID)
    {
        while(tests.iterator().hasNext())
        {
            FittsTest test = tests.iterator().next();
            if(test.getTestID().equals(ID))
            {
                return test;
            }
        }
        return null;
    }

    public void addResult(FittsResult result)
    {
        this.results.add(result);
    }

    public boolean hasPermission(String permission)
    {
        for(Role r : roles)
        {
            for(Permission p : r.getPermissions())
            {
                if(p.getName().equals(permission))
                {
                    return true;
                }
            }
        }
        return false;
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
