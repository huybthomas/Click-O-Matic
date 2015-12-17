package be.uantwerpen.iw.ei.se.fittsTest.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dries on 5/12/2015.
 */
public class FittsThroughput
{
    private List<Double> stageThroughput;
    private Double totalThroughput;

    public FittsThroughput()
    {
        this.stageThroughput = new ArrayList<Double>();
        this.totalThroughput = 0.0;
    }

    public FittsThroughput(List<Double> stageThroughput, Double totalThroughput)
    {
        this.stageThroughput = stageThroughput;
        this.totalThroughput = totalThroughput;
    }

    public void setStageThroughput(List<Double> stageThroughput)
    {
        this.stageThroughput = stageThroughput;
    }

    public List<Double> getStageThroughput()
    {
        return this.stageThroughput;
    }

    public void setTotalThroughput(Double totalThroughput)
    {
        this.totalThroughput = totalThroughput;
    }

    public Double getTotalThroughput()
    {
        return this.totalThroughput;
    }
}
