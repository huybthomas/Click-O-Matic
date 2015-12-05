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

function setStage() {
    this.currentStage = this.testStages[selector.selectedIndex];
    this.currentStage.initializeDots(canvas);
}

function initDots() {
    this.currentStage.initializeDots(canvas);
}

function draw()
{
    //Clear frame
    context.clearRect(0, 0, canvas.width, canvas.height);

    //set current stage
    if(selector.selectedIndex >= 0) {

        //Draw
        this.currentStage.drawDots(context);
    }
}


function resizeEvent()
{
    if(selector.selectedIndex >= 0) {
        //Reposition
        this.currentStage.repositionTest(canvas);
    }
}
