/**
 * Created by Quinten on 3/12/2015.
 */

var canvas = document.getElementById("FittsPreviewCanvas");
var context = canvas.getContext("2d");
var selector = document.getElementById("stagesSelector");

//Start test initialization
FittsTestStart(testAttr.testStages);

function FittsTestStart(testStages)
{
    this.testStages = testStages;
    initializeEventSystem();
}

function initializeEventSystem()
{
    //Set draw interval (10 ms)     // For performance, it is possible to use a trigger event instead of time interval
    setInterval(draw, 10);

    //Resize window
    window.addEventListener("resize", resizeEvent.bind(this), false);
}

function draw()
{
    //Clear frame
    context.clearRect(0, 0, canvas.width, canvas.height);

    //set current stage
    if(selector.selectedIndex >= 0) {

        console.log(selector.selectedIndex);

        //Set current stage
        this.currentStage = this.testStages[selector.selectedIndex];
        this.currentStage.initializeDots(canvas);

        //Draw
        this.currentStage.drawDots(context);
    }
}


function resizeEvent(event)
{
    if(selector.selectedIndex >= 0) {

        //Set current stage
        this.currentStage = this.testStages[selector.selectedIndex];
        this.currentStage.initializeDots(canvas);

        //Reposition
        this.currentStage.repositionTest(canvas);
    }
}
