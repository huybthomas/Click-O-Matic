/**
 * Created by Thomas on 05/11/2015.
 */
function FittsCircle(size, posX, posY)
{
    this.size = size;
    this.posX = 0;
    this.posY = 0;
    this.color = "blue";
    this.dashLength = 1;

    this.draw = function(context)
    {
        context.beginPath();

        //Draw circle
        context.arc(this.posX, this.posY, this.size, 0, 2 * Math.PI);

        context.stroke();
    };

    this.calculateCirclePoints = function()
    {
        n = this.size/this.dashLength;
        alpha = Math.PI * 2 / dots;
        pointObject = {};
        points = [];
        i = -1;
    };
}