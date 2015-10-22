package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.ClickOMaticApplication;
import be.uantwerpen.iw.ei.se.models.User;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Thomas on 22/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClickOMaticApplication.class)
@WebAppConfiguration
public class UserRepositoryTests
{
    @Autowired
    UserRepository userRepository;

    @Test
    public void testSaveProduct()
    {
        //Setup product
        User user = new User();
        user.setUserName("TestUserName");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPassword("test");

        //Save product, verify has ID value after save
        assertNull(user.getId());       //Null before save
        userRepository.save(user);
        assertNotNull(user.getId());    //Not null after save

        //Fetch from database
        User fetchedUser = userRepository.findOne(user.getId());

        //Should not be null
        assertNotNull(fetchedUser);

        //Should equals
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals(user.getUserName(), fetchedUser.getUserName());

        //Update description and save
        fetchedUser.setUserName("NewTestUserName");
        userRepository.save(fetchedUser);

        //Get from database, should be updated
        User fetchedUpdatedUser = userRepository.findOne(fetchedUser.getId());
        assertEquals(fetchedUser.getUserName(), fetchedUpdatedUser.getUserName());

        //Verify count of products in database
        long userCount = userRepository.count();
        assertEquals(userCount, 5);                 //There are originally 4 users declared in the database

        //Get all products, list should only have one more then initial value
        Iterable<User> users = userRepository.findAll();

        int count = 0;

        for(User p : users)
        {
            count++;
        }

        //There are originally 4 users declared in the database
        assertEquals(count, 5);
    }
}
