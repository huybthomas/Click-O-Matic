package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.repositories.FittsStageResultRepository;
import be.uantwerpen.iw.ei.se.repositories.FittsTestRepository;
import be.uantwerpen.iw.ei.se.repositories.FittsTestStageRepository;
import be.uantwerpen.iw.ei.se.services.FittsResultService;
import be.uantwerpen.iw.ei.se.services.FittsService;
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
        Iterable<FittsResult> results = fittsService.findResultsByTestId(fittsTest.getTestID());
        
    }
}
