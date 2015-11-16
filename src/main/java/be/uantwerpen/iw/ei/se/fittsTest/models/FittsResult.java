package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;
import be.uantwerpen.iw.ei.se.models.User;

import javax.persistence.Entity;

/**
 * Created by dries on 3/11/2015.
 */
@Entity
public class FittsResult extends MyAbstractPersistable<Long>
{
    private String resultID;
    private String testID;
    private String fittsTrackPaths;
    //private List<FittsTrackPath> fittsTrackPaths;
    //private User user;

    public FittsResult()
    {
        this.resultID = "";
        this.testID = "";
        this.fittsTrackPaths = "";
    }

    public FittsResult(String resultID, String testID, String fittsTrackPaths)
    {
        this.resultID = resultID;
        this.testID = testID;
        this.fittsTrackPaths = fittsTrackPaths;
        //this.user = user;
    }

    public void setResultID(String resultID)
    {
        this.resultID = resultID;
    }

    public String getResultID()
    {
        return this.resultID;
    }

    public void setTestID(String testID)
    {
        this.testID = testID;
    }

    public String getTestID()
    {
        return this.testID;
    }

    public void setFittsTrackPaths(String fittsTrackPaths)
    {
        this.fittsTrackPaths = fittsTrackPaths;
    }

    public String getFittsTrackPaths()
    {
        return this.fittsTrackPaths;
    }

/*    private double movementTime;
    //standaard afwijking van selected coord to target
    private double Sx;
    //Breedte van doel
    private double We;
    //distance to target
    private double distance;
    private double throughput;

    //private double difficutlyIndex;

    //final static double LOG_TWO = 0.693147181;
    //final static double SQRT_2_PI_E = 4.132731354;

    public FittsResult()
    {
        //empty FittsResult
    }

    public FittsResult(double time, double Sx, double d, double throughput)
    {
        this.movementTime = time;
        this.Sx = Sx;
        this.distance = d;
        this.throughput = throughput;
    }

    public void setTime(double time) {this.movementTime = time;}
    public double getTime() {return movementTime;}

    public void setSx(double Sx) {this.Sx = Sx;}
    public double getSx() {return Sx;}

    public void setWe(double We) {this.We = We;}
    public double getWe() {return We;}

    public void setDistance(double d) {this.distance = d;}
    public double getDistance() {return distance;}

    public void setThroughput(double throughput) {this.throughput = throughput;}
    public double getThroughput() {return throughput;}

//    private int Throughput(double Sx, double movementTime, double d)
//    {
//        We = Sx*SQRT_2_PI_E;
//        difficutlyIndex = LOG_TWO*((d+We)/We);
//        throughput = difficutlyIndex/movementTime;
//    } */
}
