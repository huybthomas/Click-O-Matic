/**
 * Created by Thomas on 09/11/2015.
 */
function FittsTracking()
{
    this.trackPaths = [];
    this.currentTrackPath = new FittsTrackPath();

    this.addCursorEvent = function(cursorEvent)
    {
        this.currentTrackPath.addCursorEvent(cursorEvent);
    }

    this.continueWithNextTrackPath = function()
    {
        this.currentTrackPath = new FittsTrackPath();
        this.trackPaths.push(this.currentTrackPath);
    }

    this.getTrackPaths = function()
    {
        return this.trackPaths;
    }

    this.getCurrentTrackPath = function()
    {
        return this.currentTrackPath;
    }
}