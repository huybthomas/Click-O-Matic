/**
 * Created by Thomas on 09/11/2015.
 */
function FittsTracking()
{
    this.trackPaths = [];
    this.currentTrackPath;

    this.initialize = function()
    {
        this.currentTrackPath = new FittsTrackPath();
    }

    this.addCursorEvent = function(cursorEvent)
    {
        this.currentTrackPath.addCursorEvent(cursorEvent);
    }

    this.continueWithNextTrackPath = function()
    {
        this.trackPaths.push(this.currentTrackPath);
        this.currentTrackPath = new FittsTrackPath();
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