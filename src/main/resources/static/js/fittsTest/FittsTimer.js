/**
 * Created by Thomas on 09/11/2015.
 */
function FittsTimer()
{
    this.startTime = new Date();
    this.endTime = new Date();
    this.running = false;

    this.startTimer = function()
    {
        if(!this.running)
        {
            this.startTime = new Date();
            this.running = true;
        }
    }

    this.stopTimer = function()
    {
        if(this.running)
        {
            this.endTime = new Date();
            this.running = false;
        }
    }

    this.getStartTime = function()
    {
        return this.startTime;
    }

    this.getEndTime = function()
    {
        return this.endTime;
    }

    this.getElapsedTime = function()
    {
        if(this.running)
        {
            var now = new Date();

            return now - this.startTime;
        }
        else
        {
            return this.endTime - this.startTime;
        }
    }
}