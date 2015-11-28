package be.uantwerpen.iw.ei.se.repositories;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 28/11/2015.
 */
@Repository
public interface FittsTrackEventRepository extends CrudRepository<FittsTrackEvent,Long>
{
    List<FittsTrackEvent> findAll();
}
