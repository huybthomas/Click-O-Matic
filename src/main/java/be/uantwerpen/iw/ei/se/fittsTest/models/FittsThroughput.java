package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.repositories.FittsStageResultRepository;
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
    private FittsService fittsService;

    @Autowired
    private UserService userService;

    private List<Double> stageThroughput;
    private Double TotalThroughput;
    private FittsTest fittsTest;
    private List<FittsTestStage> testStages;
    private int dotNumber;
    private int dotRadius;
    private int dotDistance;
    private double angle;
    private List<List<Double>> coords = new ArrayList<List<Double>>();
    private List<Double> deviations;
    private Double meanDeviation;
    private Double constante=4.133;
    //coords.add(new ArrayList<Double>());
    //coords.add(new ArrayList<Double>());
    private List<List<Double>> lines = new ArrayList<List<Double>>();
    private List<List<Integer>> clickEventes = new ArrayList<List<Integer>>();
    private List<List<Double>> projectedClicks = new ArrayList<List<Double>>();
    private List<FittsStageResult> stageResults;
    private FittsStageResult stageResult;
    private List<FittsTrackPath> trackpaths;
    private FittsTrackPath trackpath;
    private List<FittsTrackEvent> trackEvents;
    private FittsTrackEvent trackEvent;
    private Double difficultyIndex;
    private List<Long> timestamps;


    public List<Double> getStagesThroughput()
    {
        return stageThroughput;
    }

    //calculates the throughput per stage
    public void calculateStageThroughput(FittsTest test)
    {
        testStages = test.getTestStages();
        for(int i=0; i<test.getTestStages().size(); i++)
        {
            calculateCoord(testStages.get(i));
            calculateLines();
            getAllClickEventes(i);
            getAllClickEventes(i);
            calculateProjectedPoints();
            calculateDeveations();
            stageThroughput.add(Math.log(((dotDistance*2)+(meanDeviation*constante))/(meanDeviation*constante))/
                    (timestamps.get(timestamps.size()-1)-timestamps.get(0)));
        }
    }

    public Double getTotalThroughput()
    {
        return TotalThroughput;
    }

    //calculates the total throughput usinging the throughput per stage
    public void calculateThroughput(FittsTest test)
    {
        calculateStageThroughput(test);
        Double sum = 0.0;
        for(int i=0; i<stageThroughput.size(); i++)
            sum = sum+stageThroughput.get(i);
        TotalThroughput = sum/stageThroughput.size();
    }

    //Calculates the coordinates of where should of been clicked
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

    //Calculates the lines between previous target and current target
    //for the first target the previous target is the middle of the cirkle, coordinates (0,0)
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
                lines.get(0).add(coords.get(1).get(j) - coords.get(1).get(j - 1) / (coords.get(0).get(j) - coords.get(0).get(j - 1)));
                lines.get(1).add(coords.get(0).get(j));
            }
        }
    }

    //Get the positions where a click occured
    private void getAllClickEventes(int i)
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
                            trackEvents = trackpath.getPath();
                            for(int n=0; n<trackEvents.size(); n++)
                            {
                                if(trackEvents.get(n).getCursorState() == true)
                                {
                                    clickEventes.get(0).add(trackEvents.get(n).getCursorPosX());
                                    clickEventes.get(1).add(trackEvents.get(n).getCursorPosY());
                                    timestamps.add(trackEvents.get(n).getTimestamp());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void calculateProjectedPoints()
    {
        for(int i=0; i<clickEventes.get(0).size();i++)
        {
            double clickX = clickEventes.get(0).get(i);
            double clickY = clickEventes.get(1).get(i);
            double slope = lines.get(0).get(i);
            double offset = lines.get(1).get(i);
            double projectSlope = -1/slope;
            double projectOffset = clickY-projectSlope*clickX;
            projectedClicks.get(0).add((offset-projectOffset)/(slope-projectSlope));
            projectedClicks.get(1).add(slope*((offset-projectOffset)/(slope-projectSlope))-offset);
        }
    }

    private void calculateDeveations()
    {
        double power = 2;
        meanDeviation =0.0;
        for(int i=0; i<projectedClicks.get(0).size(); i++)
        {
            deviations.add(Math.sqrt(Math.pow((projectedClicks.get(0).get(i)-coords.get(0).get(i)),power))
                    +Math.pow((projectedClicks.get(1).get(i)-coords.get(1).get(i)),power));
            meanDeviation=meanDeviation+deviations.get(i);
        }
        meanDeviation = meanDeviation/deviations.size();
    }
}
