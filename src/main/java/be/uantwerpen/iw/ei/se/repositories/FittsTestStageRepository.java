package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 19/11/2015.
 */
@Repository
public interface FittsTestStageRepository extends CrudRepository<FittsTestStage,Long>
{
    FittsTestStage findByTestStageID(String testStageID);

    List<FittsTestStage> findAll();
}