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
    
    var startTime = new Date();

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
    }

    this.draw = function(context)
    {
        //Draw border


        //Draw position circle

        //Draw target circles
        this.currentStage.drawDots(context);

        //Draw tracking path

        //Draw test status
        this.currentStage.drawStatus(context);
    }

    this.getCurrentStageNumber = function()
    {
        return this.currentStageNumber;
    }

    this.getCurrentStage = function()
    {
        return this.currentStage
    }

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
    }

    this.getTestStages = function()
    {
        return this.testStages;
    }

    this.getNumberOfStages = function()
    {
        return this.testStages.length;
    }

    this.getTestFinished = function()
    {
        return this.testFinished;
    }

    this.repositionTest = function()
    {
        this.currentStage.repositionTest(this.canvas);
    }
    
    this.drawStatus = function()
    {
        //Draw mouse position coordinates
        var message = "Cursor x: " + this.cursorState.x + " y: " + this.cursorState.y + " - clicked: " + this.cursorState.leftPressed;
        var clickAmount = "Clicks: " + this.AmountOfClicks;
        var testRound = "Round: " ;
        //var timer = "Time: " ;
        var now = new Date();
        var ElapsedTime = Math.floor((now - startTime)/1000);
        console.log(ElapsedTime);
        var seconds = addZero((ElapsedTime%60), 2);
        var minutes = addZero((Math.floor(ElapsedTime/60)%60), 2);
        var hours = addZero(Math.floor(ElapsedTime/(60*60)), 2);
        var time = "Time: " + hours + ":" + minutes + ":" + seconds;

        context.font = "16px Arial";
        context.fillStyle = "black";
        context.fillText(message, 10, 25);
        context.fillText(time, 10, 45);
        context.fillText(clickAmount,10,65);
        context.fillText(testRound, 10, 85);
    }
}