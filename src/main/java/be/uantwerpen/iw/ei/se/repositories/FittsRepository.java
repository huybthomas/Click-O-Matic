package be.uantwerpen.iw.ei.se.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by Verstraete on 3/11/2015.
 *
 */
@Repository
public interface FittsRepository extends CrudRepository<FittsTest,Long> {

    List<FittsTest> fittstests = new ArrayList<>();
    FittsTest difficultyOne = new FittsTest("One");
    FittsTest difficultyTwo = new FittsTest("two");
    FittsTest difficultyThree = new FittsTest("three");
    FittsTest difficultyFour = new FittsTest("four");

    fittstests.add(difficultyOne);
    fittstests.add(difficultyTwo);
    fittstests.add(difficultyThree);
    fittstests.add(difficultyFour);


    List<FittsTest> findAll()
    {
        return new ArrayList<FittsTest> (this.fittstests);
    }

    FittsTest findOne(String name)
    {
        for(int i=0 ; i < fittstests.size();i++) {
            if (fittstests.get(i).getName()==name)
            {
                return fittstests.get(i);
            }
            return null;
        }
    }
    Long count()
    {
        return fittstest.size();
    }
    List<FittsTest> removeByFirstName ( String name);

    FittsTest save(FitsTest fitstest);

    boolean exists(String name);


}
