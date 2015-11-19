/**
 * Created by Thomas on 19/11/2015.
 */
function FittsTestSeries(fittsTests)
{
    this.fittsTests = fittsTests;
    this.currentTest = {};
    this.currentStage = 0;
    this.canvas = {};
    this.testSeriesFinished = false;

    this.initialize = function(canvas)
    {
        this.canvas = canvas;
        this.currentStage = 0;
        this.currentTest = this.fittsTests[this.currentStage];
        this.testSeriesFinished = false;

        this.currentTest.initialize(this.canvas);
    }

    this.getCurrentStageNumber = function()
    {
        return this.currentStage;
    }

    this.getCurrentStage = function()
    {
        return this.currentTest
    }

    this.nextStage = function()
    {
        if(this.currentStage < this.fittsTests.length)
        {
            this.currentStage++;
            this.currentTest = this.fittsTests[this.currentStage];

            this.currentTest.initialize(this.canvas);

            return this.currentTest;
        }
        else
        {
            this.testSeriesFinished = true;
            return null;
        }
    }

    this.getFittsTestSeries = function()
    {
        return this.fittsTests;
    }

    this.getNumberOfStages = function()
    {
        return this.fittsTests.length;
    }

    this.getTestSeriesFinished = function()
    {
        return this.testSeriesFinished;
    }
}