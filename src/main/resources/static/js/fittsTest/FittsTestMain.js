/**
 * Created by Thomas on 03/11/2015.
 */
var canvas = document.getElementById("FittsCanvas");
var context = canvas.getContext("2d");

//Start test initialization
FittsTestMain();

function FittsTestMain()
{
    this.test = new FittsTest(10, 10, 30); // FittsTest(numberOfDots,dotSize,dotDistance)
    initializeTest();
}

function initializeTest()
{
    this.test.initializeDots();

    //Set draw interval (10 ms)
    setInterval(draw, 10);
}

function draw()
{
    //Clear frame
    context.clearRect(0, 0, canvas.width, canvas.height);

    //Draw position circle

    //Draw target circles
    this.test.drawDots(context);

    //Draw tracking path

}