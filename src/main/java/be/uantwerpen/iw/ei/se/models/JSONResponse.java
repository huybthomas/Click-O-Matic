package be.uantwerpen.iw.ei.se.models;

/**
 * Created by Thomas on 22/11/2015.
 */
public class JSONResponse
{
    private String status;
    private String message;
    private String redirect;
    private boolean nextTest;

    public JSONResponse()
    {
        this.status = "";
        this.message = "";
        this.redirect = "";
        this.nextTest = false;
    }

    public JSONResponse(String status, String message, String redirect, boolean nextTest)
    {
        this.status = status;
        this.message = message;
        this.redirect = redirect;
        this.nextTest = nextTest;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setRedirect(String redirect)
    {
        this.redirect = redirect;
    }

    public String getRedirect()
    {
        return this.redirect;
    }

    public void setNextTest(boolean nextTest) { this.nextTest = nextTest; }

    public boolean getNextTest() { return this.nextTest; }
}
