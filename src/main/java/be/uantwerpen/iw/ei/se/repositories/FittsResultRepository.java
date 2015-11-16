package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 16/11/2015.
 */
@Repository
public interface FittsResultRepository extends CrudRepository<FittsResult,Long>
{
    FittsResult findByResultID(String resultID);

    List<FittsResult> findAll();
}
