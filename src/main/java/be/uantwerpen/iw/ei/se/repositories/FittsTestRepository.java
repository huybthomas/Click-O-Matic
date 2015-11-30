package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Verstraete on 3/11/2015.
 */
@Repository
public interface FittsTestRepository extends CrudRepository<FittsTest,Long>
{
    //@Query(value="select t from User u left join u.tests t where u=:usr")
    //Iterable<FittsTest> findAllForUser(@Param("usr") User user);

    @Query(value="select t from FittsTest t where t.completed=:complete")
    Iterable<FittsTest> findByCompleteState(@Param("complete") boolean completed);

    //@Query(value="select t from FittsTest t left join u.roles r where u.userName=:userName")
    //Iterable<FittsTest> findAllForUser(@Param("userName") String userName);

    FittsTest findByTestID(String testID);

    List<FittsTest> findAll();
}
