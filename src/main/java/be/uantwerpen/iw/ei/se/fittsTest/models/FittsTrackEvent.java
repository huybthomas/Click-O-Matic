package be.uantwerpen.iw.ei.se.fittsTest.models;

import be.uantwerpen.iw.ei.se.models.MyAbstractPersistable;

import javax.persistence.Entity;

/**
 * Created by Quinten on 15/11/2015.
 */
@Entity
public class FittsTrackEvent extends MyAbstractPersistable<Long>
{
    private long timestamp;
    private int cursorPosX;
    private int cursorPosY;
    private boolean cursorState;     // true if left mouse button is pressed

    public FittsTrackEvent()
    {
        this.timestamp = 0L;
        this.cursorPosX = 0;
        this.cursorPosY = 0;
        this.cursorState = false;
    }

    public FittsTrackEvent(long timestamp, int cursorPosX, int cursorPosY, boolean cursorState)
    {
        this.timestamp = timestamp;
        this.cursorPosX = cursorPosX;
        this.cursorPosY = cursorPosY;
        this.cursorState = cursorState;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public long getTimestamp()
    {
        return this.timestamp;
    }

    public void setCursorPosX(int cursorPosX)
    {
        this.cursorPosX = cursorPosX;
    }

    public int getCursorPosX()
    {
        return this.cursorPosX;
    }

    public void setCursorPosY(int cursorPosY)
    {
        this.cursorPosY = cursorPosY;
    }

    public int getCursorPosY()
    {
        return this.cursorPosY;
    }

    public void setCursorState(boolean cursorState)
    {
        this.cursorState = cursorState;
    }

    public boolean getCursorState()
    {
        return this.cursorState;
    }
}
