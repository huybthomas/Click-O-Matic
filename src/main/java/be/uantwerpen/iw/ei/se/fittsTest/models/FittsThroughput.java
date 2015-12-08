package be.uantwerpen.iw.ei.se.fittsTest.models;

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

    private List<Double> stageThroughput = new ArrayList<Double>();
    private Double totalThroughput;
    private FittsTest fittsTest;
    private List<FittsTestStage> testStages;
    private int dotNumber;
    private int dotDistance;
    private double angle;
    private List<List<Double>> coords = new ArrayList<List<Double>>();
    private List<Double> deviations = new ArrayList<Double>();
    private Double meanDeviation;
    private Double constante=4.133;
    //coords.add(new ArrayList<Double>());
    //coords.add(new ArrayList<Double>());
    private List<List<Double>> lines = new ArrayList<List<Double>>();
    private List<List<Integer>> clickEvents = new ArrayList<List<Integer>>();
    private List<List<Double>> projectedClicks = new ArrayList<List<Double>>();
    private FittsStageResult stageResult;
    private FittsTrackPath trackpath;
    private List<FittsTrackEvent> trackEvents;
    private List<Long> timestamps= new ArrayList<Long>();


    public List<Double> getStagesThroughput()
    {
        return this.stageThroughput;
    }

    //calculates the throughput per stage
    public void calculateStageThroughput(FittsTest test)
    {
        this.fittsTest = test;
        this.testStages = test.getTestStages();
        for(int i=0; i<test.getTestStages().size(); i++)
        {
            calculateCoord(this.testStages.get(i));
            calculateLines();
            getAllClickEventes(i);
            calculateProjectedPoints();
            calculateDeveations();
            this.stageThroughput.add(Math.log(((this.dotDistance*2)+(this.meanDeviation*this.constante))/(this.meanDeviation*this.constante))/
                    (this.timestamps.get(this.timestamps.size()-1)-this.timestamps.get(0)));
        }
    }

    public Double getTotalThroughput()
    {
        return this.totalThroughput;
    }

    //calculates the total throughput usinging the throughput per stage
    public void calculateThroughput(FittsTest test)
    {
        calculateStageThroughput(test);
        Double sum = 0.0;
        for(int i=0; i<this.stageThroughput.size(); i++)
            sum = sum+this.stageThroughput.get(i);
        this.totalThroughput = sum/this.stageThroughput.size();
    }

    //Calculates the coordinates of where should of been clicked
    private void calculateCoord(FittsTestStage stage)
    {
        this.dotNumber = stage.getNumberOfDots();
        this.dotDistance = stage.getDotDistance();
        this.angle = 2*Math.PI/this.dotNumber;

        for(int j=0; j<this.dotNumber; j++)
        {
            this.coords.get(0).add(-this.dotDistance * Math.sin(-this.angle * j));
            this.coords.get(1).add(-this.dotDistance * Math.cos(-this.angle * j));
        }
    }

    //Calculates the lines between previous target and current target
    //for the first target the previous target is the middle of the cirkle, coordinates (0,0)
    private void calculateLines()
    {
        for(int j=0; j<this.dotNumber; j++)
        {
            if(j==0)
            {
                //eerste punt is het middelpunt
                this.lines.get(0).add(this.coords.get(1).get(j)/this.coords.get(0).get(j));
                this.lines.get(1).add(this.coords.get(0).get(j));
            }
            else
            {
                if(this.coords.get(0).get(j) - this.coords.get(0).get(j - 1) == 0)
                    this.lines.get(0).add(0.0);
                else
                    this.lines.get(0).add(this.coords.get(1).get(j) - this.coords.get(1).get(j - 1) / (this.coords.get(0).get(j) - this.coords.get(0).get(j - 1)));
                this.lines.get(1).add(this.coords.get(0).get(j));
            }
        }
    }

    //Get the positions where a click occured
    private void getAllClickEventes(int i)
    {
        Iterable<FittsResult> results = this.fittsService.findResultsByTestIdForUser(this.fittsTest.getTestID(), this.userService.getPrincipalUser());
        while(results.iterator().hasNext()) {
            for (FittsResult result : results) {
                for(int l=0; l<result.getStageResults().size(); l++)
                {
                    this.stageResult = result.getStageResults().get(l);
                    for(int k=0; k<this.stageResult.getFittsTrackPaths().size(); k++)
                    {
                        for(int m=0; m<this.stageResult.getFittsTrackPaths().size();m++) {
                            this.trackpath = this.stageResult.getFittsTrackPaths().get(m);
                            this.trackEvents = this.trackpath.getPath();
                            if (this.trackEvents.get(this.trackEvents.size()-1).getCursorState()) {
                                this.clickEvents.get(0).add(this.trackEvents.get(this.trackEvents.size()-1).getCursorPosX());
                                this.clickEvents.get(1).add(this.trackEvents.get(this.trackEvents.size()-1).getCursorPosY());
                                this.timestamps.add(this.trackEvents.get(this.trackEvents.size()-1).getTimestamp());
                            }
                        }
                    }
                }
            }
        }
    }

    private void calculateProjectedPoints()
    {
        for(int i=0; i<this.clickEvents.get(0).size();i++)
        {
            double clickX = this.clickEvents.get(0).get(i);
            double clickY = this.clickEvents.get(1).get(i);
            double slope = this.lines.get(0).get(i);
            double offset = this.lines.get(1).get(i);
            double projectSlope = 0;
            double projectOffset = clickY-projectSlope*clickX;
            if(slope!=0)
                projectSlope = -1/slope;

            if(slope == 0)
            {
                this.projectedClicks.get(0).add(offset-projectOffset);
                this.projectedClicks.get(1).add(-projectOffset);
            }
            else {
                this.projectedClicks.get(0).add((offset - projectOffset) / (slope - projectSlope));
                this.projectedClicks.get(1).add(slope * ((offset - projectOffset) / (slope - projectSlope)) - offset);
            }
        }
    }

    private void calculateDeveations()
    {
        this.meanDeviation =0.0;
        for(int i=0; i<this.projectedClicks.get(0).size(); i++)
        {
            deviations.add(Math.sqrt(Math.pow((this.projectedClicks.get(0).get(i)-coords.get(0).get(i)),2.0))
                    +Math.pow((this.projectedClicks.get(1).get(i)-coords.get(1).get(i)),2.0));
            this.meanDeviation=meanDeviation+deviations.get(i);
        }
        this.meanDeviation = this.meanDeviation/this.deviations.size();
    }
}
