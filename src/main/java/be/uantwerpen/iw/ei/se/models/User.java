package be.uantwerpen.iw.ei.se.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Thomas on 19/10/2015.
 */
@Entity
public class User
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

//    @ManyToMany
//    @JoinTable(
//           name="USER_ROLE",
//            joinColumns={
 //               @JoinColumns(name="USER_ID", referencedColumnName="ID")},
 //           inverseJoinColumns= {
//                @JoinColumns(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

    public User()
    {
        this.firstName = "";
        this.lastName = "";
        this.userName = "";
        this.password = "";
    }

    public User(String firstName, String lastName, String userName, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public User(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = "";
        this.password = "";
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
}
