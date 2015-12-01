package be.uantwerpen.iw.ei.se.models;

import java.util.Objects;

/**
 * Created by Thomas on 22/11/2015.
 */
public class JSONResponse
{
    private String status;
    private String message;
    private String redirect;
    private Object responseObject;

    public JSONResponse()
    {
        this.status = "";
        this.message = "";
        this.redirect = "";
        this.responseObject = null;
    }

    public JSONResponse(String status, String message, String redirect, Object responseObject)
    {
        this.status = status;
        this.message = message;
        this.redirect = redirect;
        this.responseObject = responseObject;
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

    public void setResponseObject(Object responseObject)
    {
        this.responseObject = responseObject;
    }

    public Object getResponseObject()
    {
        return this.responseObject;
    }
}
