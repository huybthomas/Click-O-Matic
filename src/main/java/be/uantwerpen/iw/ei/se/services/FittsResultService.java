package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsStageResult;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTrackPath;
import be.uantwerpen.iw.ei.se.models.User;
import be.uantwerpen.iw.ei.se.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Thomas on 28/11/2015.
 */
@Service
public class FittsResultService
{
    @Autowired
    private FittsResultRepository fittsResultRepository;

    @Autowired
    private FittsStageResultRepository fittsStageResultRepository;

    @Autowired
    private FittsTrackPathRepository fittsTrackPathRepository;

    @Autowired
    private FittsTrackEventRepository fittsTrackEventRepository;

    @Autowired
    private UserService userService;

    public Iterable<FittsResult> findAll()
    {
        return this.fittsResultRepository.findAll();
    }

    public void save(List<FittsResult> results)
    {
        for(FittsResult result : results)
        {
            for (FittsStageResult stage : result.getStageResults())
            {
                for (FittsTrackPath path : stage.getFittsTrackPaths())
                {
                    //Save the events of the path to the database
                    this.fittsTrackEventRepository.save(path.getPath());
                }

                //Save the paths of the stage to the database
                this.fittsTrackPathRepository.save(stage.getFittsTrackPaths());
            }

            //Save the stages of the test result to the database
            this.fittsStageResultRepository.save(result.getStageResults());
        }

        //Save the test result to the database
        this.fittsResultRepository.save(results);
    }

    public void save(FittsResult result)
    {
        this.save(new ArrayList<FittsResult>(Arrays.asList(result)));
    }

    public boolean delete(String resultID)
    {
        FittsResult result = this.findByResultID(resultID);

        if(result != null)
        {
            for(FittsStageResult stage : result.getStageResults())
            {
                for(FittsTrackPath path : stage.getFittsTrackPaths())
                {
                    //Delete all Track events
                    this.fittsTrackEventRepository.delete(path.getPath());
                }

                //Delete all Track Paths
                this.fittsTrackPathRepository.delete(stage.getFittsTrackPaths());
            }

            //Delete all Stage Results
            this.fittsStageResultRepository.delete(result.getStageResults());

            //Delete link with user
            User user = userService.findUserWithResult(result);

            if(user != null)
            {
                user.getResults().remove(result);
            }

            //Delete Result
            this.fittsResultRepository.delete(result);

            return true;
        }

        return false;
    }

    public Iterable<FittsResult> findByTestID(String testID)
    {
        return this.fittsResultRepository.findByTestID(testID);
    }

    public FittsResult findByResultID(String resultID)
    {
        return this.fittsResultRepository.findByResultID(resultID);
    }

    public Iterable<FittsResult> findByTestIDForUser(String testID, User user)
    {
        return this.fittsResultRepository.findByTestIDForUser(testID, user);
    }

    public boolean resultIdAlreadyExists(final String resultID)
    {
        List<FittsResult> results = fittsResultRepository.findAll();

        for(FittsResult resultIt : results)
        {
            if(resultIt.getResultID().equals(resultID))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isDuplicatedResultId(final FittsResult result)
    {
        List<FittsResult> results = fittsResultRepository.findAll();

        for(FittsResult resultIt : results)
        {
            if(resultIt.getTestID().equals(result.getTestID()) && !resultIt.getId().equals(result.getId()))
            {
                //Two different test objects with the same resultID
                return true;
            }
        }

        return false;
    }
}
