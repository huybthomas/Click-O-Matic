/**
 * Created by Thomas on 19/11/2015.
 */
function FittsTest(stages)
{
    this.testStages = [];
    this.currentStage = {};
    this.currentStageNumber = 0;
    this.canvas = {};
    this.testFinished = false;

    for(i = 0; i < stages.length; i++)
    {
        this.testStages[i] = new FittsTestStage(stages[i].numberOfDots, stages[i].dotRadius, stages[i].dotDistance);
    }

    this.initialize = function(canvas)
    {
        this.canvas = canvas;
        this.currentStageNumber = 0;
        this.currentStage = this.testStages[this.currentStageNumber];
        this.testFinished = false;

        this.currentStage.initialize(this.canvas);
    };

    this.draw = function(context)
    {
        //Draw border


        //Draw position circle

        //Draw target circles
        this.currentStage.drawDots(context);

        //Draw tracking path

    };

    this.getCurrentStageNumber = function()
    {
        return this.currentStageNumber;
    };

    this.getCurrentStage = function()
    {
        return this.currentStage
    };

    this.nextStage = function()
    {
        if(this.currentStageNumber < (this.testStages.length - 1))
        {
            this.currentStageNumber++;
            this.currentStage = this.testStages[this.currentStageNumber];

            this.currentStage.initialize(this.canvas);

            return this.currentStage;
        }
        else
        {
            this.testFinished = true;
            return null;
        }
    };

    this.getTestStages = function()
    {
        return this.testStages;
    };

    this.getNumberOfStages = function()
    {
        return this.testStages.length;
    };

    this.getTestFinished = function()
    {
        return this.testFinished;
    };

    this.repositionTest = function()
    {
        this.currentStage.repositionTest(this.canvas);
    };

    //Temporary function
    this.getThroughput = function()
    {
        var We = this.dotsSize*2;
        var d = this.dotDistance*2;
        var difficutlyIndex = Math.log((d/We)+1)/Math.log(2);
        console.log(this.pathTracker.getLastPath());
        console.log(this.pathTracker.getLastPath().getLastEvent());
        console.log(this.pathTracker.getLastPath().getLastEvent().getTimestamp());
        console.log(this.pathTracker.getFirstPath().getFirstEvent().getTimestamp());
        var movementTime = this.pathTracker.getLastPath().getLastEvent().getTimestamp() - this.pathTracker.getFirstPath().getFirstEvent().getTimestamp();
        var totalTime = (movementTime/1000)/numberOfDots;
        console.log(We);
        console.log(d);
        console.log(difficutlyIndex);
        console.log(totalTime);
        return difficutlyIndex/totalTime;
    };
}