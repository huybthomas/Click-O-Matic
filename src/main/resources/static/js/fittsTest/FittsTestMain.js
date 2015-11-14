/**
 * Created by Thomas on 03/11/2015.
 */
var canvas = document.getElementById("FittsCanvas");
var context = canvas.getContext("2d");
var cursorState = {x: 0, y: 0, leftPressed: false, leftReleased : false};

//Start test initialization
FittsTestStart(testAttr);

function FittsTestStart(testAttr)
{
    this.test = new FittsTest(testAttr.numberOfDots, testAttr.dotsSize, testAttr.dotDistance); // FittsTest(numberOfDots (enkel oneven),dotSize,dotDistance)

    this.test.initialize(canvas);

    initializeTest();
}

function initializeTest()
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

    //Draw border


    //Draw position circle

    //Draw target circles
    this.test.drawDots(context);

    //Draw tracking path

    //Draw test status
    this.test.drawStatus(context);
}

function cursorEvent(event)
{
    var boundRect = canvas.getBoundingClientRect();

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
            event.which = 3     //Right mouse button
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

    this.test.triggeredCursorEvent(cursorState);
}

function resizeEvent(event)
{
    console.log("RESIZE EVENT TRIGGER");
    this.test.repositionTest(canvas);
}