/**
 * Created by Thomas on 03/11/2015.
 */
var canvas = document.getElementById("FittsCanvas");
var context = canvas.getContext("2d");
var cursorState = {x: 0, y: 0, leftPressed: false, leftReleased : false};
var postRequestSend = false;
var startTime = new Date();
var AmountOfClicks = 0;

//Start test initialization
FittsTestStart(testAttr.testStages);

function FittsTestStart(testStages)
{
    this.test = new FittsTest(testStages);

    this.test.initialize(canvas);

    initializeEventSystem();
}

function initializeEventSystem()
{
    //Set draw interval (10 ms)
    setInterval(draw, 10);

    //Set event listeners
    //Cursor movement
    canvas.addEventListener("mousemove", cursorEvent.bind(this), false);

    //Cursor click
    canvas.addEventListener("mousedown", cursorEvent.bind(this), false);
    canvas.addEventListener("mouseup", cursorEvent.bind(this), false);

    //Resize window
    window.addEventListener("resize", resizeEvent.bind(this), false);
}

function draw()
{
    //Clear frame
    context.clearRect(0, 0, canvas.width, canvas.height);

    //Draw test
    this.test.draw(context);

    //Draw status
    drawStatus();
}

function drawStatus()
{
    clickAmount = "Clicks: " + addZero(AmountOfClicks, 2);
    testRound = "Round: " + addZero(this.test.getCurrentStageNumber() + 1, 2) + "/" + addZero(this.test.getTestStages().length, 2);
    now = new Date();
    ElapsedTime = Math.floor((now - startTime)/1000);

    seconds = addZero((ElapsedTime%60), 2);
    minutes = addZero((Math.floor(ElapsedTime/60)), 2);
    time = "Time: " + minutes + ":" + seconds;

    context.font = "16px Arial";
    context.fillStyle = "black";
    context.fillText(time, 10, 25);
    context.fillText(clickAmount, 10, 45);
    context.fillText(testRound, 10, 65);
}

function cursorEvent(event)
{
    //Get mouse position relative to the canvas
    cursorState.x = event.clientX - canvas.offsetLeft;
    cursorState.y = event.clientY - canvas.offsetTop;

    //Get mouse button state
    if(!event.which && event.button)        //Cross-browser approach: <IE9 fix
    {
        if(event.button & 1)
            event.which = 1;    //Left mouse button
        else if(event.button & 4)
            event.which = 2;    //Middle mouse button
        else if(event.button & 2)
            event.which = 3;    //Right mouse button
    }

    if(event.which == 1)    //Left mouse button
    {
        cursorState.leftReleased = false;

        if(event.type == "mousedown")
        {
            cursorState.leftPressed = true;
        }
        else if(event.type == "mouseup")
        {
            cursorState.leftPressed = false;
            cursorState.leftReleased = true;
        }
    }
    else if(event.which == 0)               //Cross-browser approach: Chrome + Opera fix
    {
        cursorState.leftReleased = false;
    }

    //Evaluate cursor event only if test not finished
    if(!this.test.getCurrentStage().getFinished())
    {
        if(cursorState.leftReleased)
        {
            AmountOfClicks++;
        }

        this.test.getCurrentStage().triggeredCursorEvent(cursorState);
    }

    checkState();
}

function checkState()
{
    if(this.test.getCurrentStage().getFinished())
    {
        this.test.nextStage();
    }

    if(this.test.getTestFinished())
    {
        if(!postRequestSend)
        {
           paths = this.test.getTestStages();

           sendResult(paths);
        
           postRequestSend = true;
        }
    }
}

function testFinished()
{
    return this.test.getTestFinished();
}

function sendResult(result)
{
    $.ajax({
        type: "POST",
        url: "/PostFittsResult/" + testAttr.testID + "/",
        data: JSON.stringify(result),           //"trackPaths" will be value for @RequestParam
        contentType: "application/json",
        success: function(response) {
            if(response.status = "OK")
            {
                receiveSuccess(response);
            }
            else
            {
                receiveError(response.message);
            }
        },
        error: function(response) {
            receiveError("Connection lost with the server! An error occurred when trying to contact the server.\nError-message: " + response.responseJSON.message);
        }
    });
}

function receiveSuccess(response)
{
    //Response redirect is set on server side, depending on response.responseObject boolean value
    if(response.responseObject == false)
    {
        window.location.replace(response.redirect);
    }
    else
    {
        $('#nextTestLink').attr("href", response.redirect);
        $('#continueModal').modal('show');
    }
}

function receiveError(response)
{
    alert('We are sorry, but an error has occurred: ' + response);       //Reroute to error page
}

function resizeEvent(event)
{
    this.test.repositionTest(canvas);
}

function addZero(x,n)
{
    while(x.toString().length < n)
    {
        x = "0" + x;
    }
    return x;
}