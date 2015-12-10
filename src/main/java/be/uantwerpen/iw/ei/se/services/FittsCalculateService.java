package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dries on 10/12/2015.
 */
@Service
public class FittsCalculateService
{
    @Autowired
    private FittsService fittsService;

    @Autowired
    private UserService userService;

    private List<List<Double>> coords = new ArrayList<List<Double>>();
    private Double constant=4.133;

    //calculates the total throughput using the throughput per stage
    public FittsThroughput calculateThroughput(FittsResult result)
    {
        FittsThroughput throughputResults = new FittsThroughput();

        List<Double> stageThroughputs = calculateStageThroughputs(result);

        Double sum = 0.0;
        for(int i=0; i<stageThroughputs.size(); i++)
        {
            sum = sum + stageThroughputs.get(i);
        }

        throughputResults.setTotalThroughput(sum/stageThroughputs.size());
        throughputResults.setStageThroughput(stageThroughputs);

        return throughputResults;
    }

    //calculates the throughput per stage
    private List<Double> calculateStageThroughputs(FittsResult result)
    {
        FittsTest test = fittsService.findTestById(result.getTestID());
        List<Double> stageThroughputs = new ArrayList<Double>();

        for(int i=0; i<test.getTestStages().size(); i++)
        {
            List<List<Double>> coordinates = new ArrayList<List<Double>>();
            List<List<Double>> lines = new ArrayList<List<Double>>();
            List<List<Double>> projectedClicks = new ArrayList<List<Double>>();
            List<FittsTrackEvent> clickEvents = new ArrayList<FittsTrackEvent>();
            int dotDistance = test.getTestStages().get(i).getDotDistance();
            int dotNumber = test.getTestStages().get(i).getNumberOfDots();
            Double meanDeviation;

            coordinates = calculateCoord(test.getTestStages().get(i), dotNumber, dotDistance);
            lines = calculateLines(dotNumber, coordinates);
            clickEvents = getAllClickEvents(result);
            projectedClicks = calculateProjectedPoints(clickEvents, lines);
            meanDeviation = calculateDeviations(projectedClicks);

            stageThroughputs.add(Math.log(((dotDistance*2)+(meanDeviation*this.constant))/(meanDeviation*this.constant))/
                    (clickEvents.get(clickEvents.size() - 1).getTimestamp() - clickEvents.get(0).getTimestamp()));
        }

        return stageThroughputs;
    }

    //Calculates the coordinates of where should of been clicked
    private List<List<Double>> calculateCoord(FittsTestStage stage, int dotNumber, int dotDistance)
    {
        List<List<Double>> coords = new ArrayList<List<Double>>();
        Double angle = 2*Math.PI/dotNumber;

        for(int j=0; j<dotNumber; j++)
        {
            coords.get(0).add(-dotDistance * Math.sin(-angle * j));
            coords.get(1).add(-dotDistance * Math.cos(-angle * j));
        }

        return coords;
    }

    //Calculates the lines between previous target and current target
    //for the first target the previous target is the middle of the cirkle, coordinates (0,0)
    private List<List<Double>> calculateLines(int dotNumber, List<List<Double>> coords)
    {
        List<List<Double>> lines = new ArrayList<List<Double>>();

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
                if(coords.get(0).get(j) - coords.get(0).get(j - 1) == 0)
                {
                    lines.get(0).add(0.0);
                }
                else
                {
                    lines.get(0).add(this.coords.get(1).get(j) - coords.get(1).get(j - 1) / (coords.get(0).get(j) - coords.get(0).get(j - 1)));
                }

                lines.get(1).add(coords.get(0).get(j));
            }
        }

        return lines;
    }

    //Get the positions where a click occurred
    //clickEvents.get(0) is X-coordinates
    //clickEvents.get(1) is Y-coordinates
    private List<FittsTrackEvent> getAllClickEvents(FittsResult result)
    {
        List<FittsTrackEvent> clickEvents = new ArrayList<FittsTrackEvent>();

        for(int i=0; i < result.getStageResults().size(); i++)
        {
            FittsStageResult stageResult = result.getStageResults().get(i);

            for(int k=0; k < stageResult.getFittsTrackPaths().size(); k++)
            {
                FittsTrackPath trackpath = stageResult.getFittsTrackPaths().get(k);

                //First cursor position is the first element
                if(k == 0)
                {
                    clickEvents.add(trackpath.getPath().get(0));
                }

                clickEvents.add(trackpath.getPath().get(trackpath.getPath().size() - 1));
            }
        }

        return clickEvents;
    }

    private List<List<Double>> calculateProjectedPoints(List<FittsTrackEvent> clickEvents, List<List<Double>> lines)
    {
        List<List<Double>> projectedClicks = new ArrayList<List<Double>>();

        for(int i=0; i < clickEvents.size(); i++)
        {
            double clickX = clickEvents.get(i).getCursorPosX();
            double clickY = clickEvents.get(i).getCursorPosY();
            double slope = lines.get(0).get(i);
            double offset = lines.get(1).get(i);
            double projectedSlope = 0;
            double projectedOffset = clickY - projectedSlope * clickX;

            if(slope != 0)
            {
                projectedSlope = -1 / slope;
            }

            if(slope == 0)
            {
                projectedClicks.get(0).add(offset-projectedOffset);
                projectedClicks.get(1).add(-projectedOffset);
            }
            else
            {
                projectedClicks.get(0).add((offset - projectedOffset) / (slope - projectedSlope));
                projectedClicks.get(1).add(slope * ((offset - projectedOffset) / (slope - projectedSlope)) - offset);
            }
        }

        return projectedClicks;
    }

    private Double calculateDeviations(List<List<Double>> projectedClicks)
    {
        Double meanDeviation = 0.0;
        int i = 0;

        for(i = 0; i < projectedClicks.get(0).size(); i++)
        {
            Double deviation = (Math.sqrt(Math.pow((projectedClicks.get(0).get(i)-coords.get(0).get(i)),2.0))
                    +Math.pow((projectedClicks.get(1).get(i)-coords.get(1).get(i)),2.0));

            meanDeviation = meanDeviation + deviation;
        }

        if(i != 0)
        {
            return meanDeviation / i;
        }
        else
        {
            return 0.0;
        }
    }
}
