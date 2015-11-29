/**
 * Created by Thomas on 09/11/2015.
 */
function FittsTrackEvent(cursorPosX, cursorPosY, cursorState)
{
    this.timestamp = new Date().getTime();
    this.cursorPosX = cursorPosX;
    this.cursorPosY = cursorPosY;
    this.cursorState = cursorState;

    this.getCursorPosX = function()
    {
        return this.cursorPosX;
    };

    this.getCursorPosY = function()
    {
        return this.cursorPosY;
    };

    this.getCursorState = function()
    {
        return this.cursorState;
    };

    this.getTimestamp = function()
    {
        return this.timestamp;
    };
}