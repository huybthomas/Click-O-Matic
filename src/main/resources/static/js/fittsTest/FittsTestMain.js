/**
 * Created by Thomas on 03/11/2015.
 */
var canvas = document.getElementById("FittsCanvas");
var context = canvas.getContext("2d");
var cursorPosition = {x: 0, y: 0};
var cursorState = {LeftPressed: false};

//Start test initialization
FittsTestMain();

function FittsTestMain()
{
    this.test = new FittsTest(9, 10, 100); // FittsTest(numberOfDots (enkel oneven),dotSize,dotDistance)

    this.test.initialize(canvas);

    initializeTest();
}

function initializeTest()
{
    //Set draw interval (10 ms)
    setInterval(draw, 10);

    //Set event listeners
    //Cursor movement
    canvas.addEventListener("mousemove", cursorEvent, false);

    //Cursor click
    canvas.addEventListener("mousedown", cursorEvent, false);
    canvas.addEventListener("mouseup", cursorEvent, false);
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

    //Draw mouse position coordinates
    var message = "Cursor x: " + mousePosition.x + " y: " + mousePosition.y + " - clicked: " + mouseState.LeftPressed;

    context.font = "12px Arial";
    context.fillStyle = "black";
    context.fillText(message, 10, 25);
}

function cursorEvent(event)
{
    var cursorState = {x: 0, y: 0, leftPressed: false};

    //Get mouse position
    cursorState.x = event.clientX;
    cursorState.y = event.clientY;

    //Get mouse button state
    if(!event.which && event.button)    //Cross-browser approach: IE fix
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
        if(event.type == "mousedown")
        {
            cursorState.LeftPressed = true;
        }
        else if(event.type == "mouseup")
        {
            cursorState.LeftPressed = false;
        }
    }

    this.test.triggeredCursorEvent(cursorState);
}



//"Cursor x:" + event.clientXcursorPosition.x + " y:" + cursorPosition.y