/**
 * Created by Thomas on 03/11/2015.
 */
function FittsTest(numberOfDots, dotsSize, dotDistance)
{
    this.numberOfDots = numberOfDots;
    this.dotsSize = dotsSize;
    this.dotDistance = dotDistance; // Dit is de straal van de cirkel
    this.dotHColor = "red";
    this.dotLColor = "gray";
    this.backCircleColor = "blue";
    this.dotsList = [];
    this.backCircle = {};

    this.initializeTest = function(canvas)
    {
        this.initializeDots();

        this.repositionTest(canvas);
    }

    this.repositionTest = function(canvas)
    {

    }

    this.initializeDots = function()
    {
        var angle  = (2*Math.PI)/(this.numberOfDots);     // aan de hand van de hoek worden de cirkels in een cirkel gezet. Deze veranderd aan de hand van het aantal bolletjes
        var centreX = (canvas.width)/2;                   // middelpunt blijft centraal
        var centreY = (canvas.height)/2;

        for(var i = 0; i < this.numberOfDots; i++)
        {
            this.dotsList[i] = new FittsDot(i, this.dotsSize, this.dotHColor, this.dotLColor);
            this.dotsList[i].setPosition((this.dotDistance * Math.cos((angle*i)) + centreX), (this.dotDistance*Math.sin(angle*i)+ centreY));
           //document.getElementById("FittsDot").onClick = function(){alert('Click')};
            document.elementFromPoint(20,20).onclick=function(){alert('Click!')};

        }

        this.dotsList[5].setTarget(true);
    }

    this.setDotsSize = function(dotsSize)
    {
        this.dotsSize = dotsSize;
    }

    this.setDotColor = function(dotHColor, dotLColor)
    {
        this.dotHColor = dotHColor;
        this.dotLColor = dotLColor;
    }

    this.setBackCircleColor = function(backCircleColor)
    {
        this.backCircleColor = backCircleColor;
    }

    this.setDistance = function(dotDistance)
    {
        this.dotDistance = dotDistance;
    }

    this.drawBackCircle = function(context)
    {

    }

    this.drawDots = function(context)
    {
        var target = -1;

        for(var i = 0; i < this.numberOfDots; i++)
        {
            if(this.dotsList[i].isTarget()) {
                //Set target dot (will be drawn as last)
                target = i;
            }
            else
            {
                //Draw dot on screen
                this.dotsList[i].drawDot(context);
            }
        }

        //Draw target dot
        if(target != -1)
        {
            this.dotsList[target].drawDot(context);
        }
    }
}

