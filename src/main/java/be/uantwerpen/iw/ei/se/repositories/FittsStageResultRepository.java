package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 28/11/2015.
 */
@Repository
public interface FittsStageResultRepository extends CrudRepository<FittsStageResult,Long>
{
    @Query(value="select s from FittsResult r left join r.stages s where r=:res")
    Iterable<FittsStageResult> findAllForTestResult(@Param("res")FittsResult testResult);

    List<FittsStageResult> findAll();
}
