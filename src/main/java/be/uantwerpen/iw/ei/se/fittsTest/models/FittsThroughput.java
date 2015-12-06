package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.repositories.FittsStageResultRepository;
import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import be.uantwerpen.iw.ei.se.repositories.FittsTestStageRepository;
import be.uantwerpen.iw.ei.se.services.FittsResultService;
import be.uantwerpen.iw.ei.se.services.FittsService;
import be.uantwerpen.iw.ei.se.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dries on 5/12/2015.
 */
public class FittsThroughput {

    @Autowired
    private FittsTestStageRepository fittsTestStageRepository;

    @Autowired
    private FittsTestRepository fittsTestRepository;

    @Autowired
    private FittsStageResultRepository fittsStageResultRepository;

    @Autowired
    private FittsService fittsService;

    @Autowired
    private UserService userService;

    private List<Integer> StageThroughput;
    private int TotalThroughput;
    private FittsTest fittsTest;
    private List<FittsTestStage> testStages;
    private int dotNumber;
    private int dotRadius;
    private int dotDistance;
    private double angle;
    private List<List<Double>> coords = new ArrayList<List<Double>>();
    //coords.add(new ArrayList<Double>());
    //coords.add(new ArrayList<Double>());
    private List<List<Double>> lines = new ArrayList<List<Double>>();
    private List<List<Double>> clicks = new ArrayList<List<Double>>();
    private List<FittsStageResult> stageResults;
    private FittsStageResult stageResult;
    private List<FittsTrackPath> trackpaths;
    private FittsTrackPath trackpath;
    private List<FittsTrackEvent> trackEvents;
    private FittsTrackEvent trackEvent;


    public List<Integer> getStageThroughput()
    {
        return StageThroughput
    }

    public void calculateStageThroughput(FittsTest test)
    {
        testStages = test.getTestStages();
        for(int i=0; i<test.getTestStages().size(); i++)
        {
            calculateCoord(testStages.get(i));
            calculateLines();
            getAllClicks(i);
        }
    }

    public int getTotalThroughput()
    {
        return TotalThroughput
    }

    public void calculateThroughput(FittsTest test)
    {
        calculateStageThroughput(test);
        int sum = 0;
        for(int i=0; i<StageThroughput.size(); i++)
            sum = sum+StageThroughput.get(i);
        TotalThroughput = sum/StageThroughput.size();
    }

    private void calculateCoord(FittsTestStage stage)
    {
        dotNumber = stage.getNumberOfDots();
        dotRadius = stage.getDotRadius();
        dotDistance = stage.getDotDistance();
        angle = 2*Math.PI/dotNumber;

        for(int j=0; j<dotNumber; j++)
        {
            coords.get(0).add(-dotDistance * Math.sin(-angle * j));
            coords.get(1).add(-dotDistance * Math.cos(-angle * j));
        }
    }

    private void calculateLines()
    {
        for(int j=0; j<dotNumber; j++)
        {
            if(j==0)
            {
                //eerste punt is het middelpunt
                lines.get(0).add(coords.get(1).get(j)/coords.get(0).get(j));
                lines.get(1).add(coords.get(0).get(j));
            }
            else
            {
                lines.get(0).add((coords.get(1).get(j) - coords.get(1).get(j - 1) / (coords.get(0).get(j) - coords.get(0).get(j - 1)));
                lines.get(1).add(coords.get(0).get(j));
            }
        }
    }

    private void getAllClicks(int i)
    {
        Iterable<FittsResult> results = fittsService.findResultsByTestIdForUser(fittsTest.getTestID(), userService.getPrincipalUser());
        while(results.iterator().hasNext()) {
            for (FittsResult result : results) {
                stageResults = result.getStageResults();
                for(int l=0; l<stageResults.size(); l++)
                {
                    stageResult = stageResults.get(l);
                    for(int k=0; k<stageResult.getFittsTrackPaths().size(); k++)
                    {
                        trackpaths = stageResult.getFittsTrackPaths();
                        for(int m=0; m<trackpaths.size();m++)
                        {
                            trackpath = trackpaths.get(m);
                            for(int n=0; n<trackpath.getPath().size(); n++)
                            {
                                trackEvents = trackpath.getPath(n);

                            }
                        }
                    }
                }
            }
        }
    }
}
