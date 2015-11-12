/**
 * Created by Thomas on 03/11/2015.
 */
var canvas = document.getElementById("FittsCanvas");
var context = canvas.getContext("2d");

//Start test initialization
FittsTestMain();

function FittsTestMain()
{
    this.test = new FittsTest(10, 25, 150); // FittsTest(numberOfDots,dotSize,dotDistance)

    this.test.initialize(canvas);

    initializeTest();
}

function initializeTest()
{
    //Set draw interval (10 ms)
    setInterval(draw, 10);

    //Set event listeners
    //Cursor movement
    canvas.addEventListener("mousemove", cursorEvent(event), false);

    //Cursor click
    canvas.addEventListener("click", cursorEvent(event), false);
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

}

function cursorEvent(event)
{
    var cursorPosition = getCursorPosition(event);
    var message = "Cursor x:" + cursorPosition.x + " y:" + cursorPosition.y;

    context.font = "12px Arial";
    context.fillStyle = "black";
    context.fillText(message, 10, 25);
}

function getCursorPosition(event)
{
    var rect = canvas.getBoundingClientRect();

    return
    {
        x: event.clientX - rect.left;
        y: event.clientY - rect.top
    };
}