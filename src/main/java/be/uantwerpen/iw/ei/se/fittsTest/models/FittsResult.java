package be.uantwerpen.iw.ei.se.fittsTest.models;

/**
 * Created by dries on 3/11/2015.
 */
public class FittsResult
{
    private double movementTime;
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

    public void FittsTest()
    {
        //empty FittsTest
        this.movementTime = -1;
        this.Sx = -1;
        this.distance = -1;
        this.throughput = -1;
    }

    public void FittsTest(double time, double Sx, double d, double throughput)
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
//    }
}
