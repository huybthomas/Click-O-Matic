package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 16/11/2015.
 */
@Repository
public interface FittsResultRepository extends CrudRepository<FittsResult,Long>
{
    @Query(value="select r from User u left join u.results r where u=:usr and r.testID=:testid")
    Iterable<FittsResult> findByTestIDForUser(@Param("testid") String testID, @Param("usr") User user);

    FittsResult findByResultID(String resultID);

    Iterable<FittsResult> findByTestID(String testID);

    List<FittsResult> findAll();
}
