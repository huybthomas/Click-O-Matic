/**
 * Created by Thomas on 03/11/2015.
 */
function FittsDot(id, size, hColor, lColor)
{
    this.id = id;
    this.size = size;
    this.hColor = hColor;
    this.lColor = lColor;
    this.posX = 0;
    this.posY = 0;
    this.target = false;

    this.setTarget = function(flag)
    {
        this.target = flag;
    };

    this.isTarget = function()
    {
        return this.target;
    };

    this.setPosition = function(posX, posY)
    {
        this.posX = posX;
        this.posY = posY;
    };

    this.drawDot = function(context)
    {
        context.beginPath();

        //Draw circle
        context.arc(this.posX, this.posY, this.size, 0, 2 * Math.PI);

        //Set filling color
        if(this.target)
        {
            context.fillStyle = this.hColor;
        }
        else
        {
            context.fillStyle = this.lColor;
        }

        context.fill();
    };

    this.cursorOver = function(cursorX, cursorY)
    {
        if(Math.sqrt((this.posX - cursorX) * (this.posX - cursorX) + (this.posY - cursorY) * (this.posY - cursorY)) <= this.size)
        {
            return true;
        }
        else
        {
            return false;
        }
    };
}