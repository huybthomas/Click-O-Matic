/**
 * Created by Thomas on 09/11/2015.
 */
function FittsTrackPath()
{
    this.path = [];

    this.addCursorEvent = function(cursorEvent)
    {
        this.path.push(cursorEvent);
    }

    this.getPath = function()
    {
        return this.path;
    }

    this.getPathTime = function() {
        var firstEvent = this.getFirstEvent();
        var lastEvent = this.getLastEvent();

        if((lastEvent != null) && (firstEvent != null)) {
            return lastEvent.getTimestamp() - firstEvent.getTimestamp();
        } else {
            return 0;
        }

    }

    this.getFirstEvent = function()
    {
        return this.path[0];
    }

    this.getLastEvent = function()
    {
        if(this.path.length > 0) {
            return this.path[this.path.length-1];
        } else {
            return 0;
        }
    }
}