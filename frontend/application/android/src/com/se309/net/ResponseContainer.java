package com.se309.net;

import com.android.volley.VolleyError;

/**
 * This class is passed between the NetworkHandler and NetworkResponse during an exchange.
 *
 * @author Gavin Tersteeg
 */
public class ResponseContainer {

    private Object body;
    private boolean isError;
    private VolleyError error;

    /**
     * Default constructor for response container
     * @param body Object body
     * @param isError Defines an error
     * @param error Volley error
     */
    public ResponseContainer(Object body, boolean isError, VolleyError error) {
        this.body = body;
        this.isError = isError;
        this.error = error;
    }

    /**
     * Returns the object body
     * @return Object body
     */
    public Object getBody() {
        return body;
    }

    /**
     * Changes out the object body
     * @param body New object body
     */
    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * States if the response container is an error
     * @return Is error
     */
    public boolean isError() {
        return isError;
    }

    /**
     * Sets if the response container is an error
     * @param error Is error
     */
    public void setError(boolean error) {
        isError = error;
    }

    /**
     * Returns the volley error
     * @return Volley error
     */
    public VolleyError getError() {
        return error;
    }

    /**
     * Changes the volley error
     * @param error New volley error
     */
    public void setError(VolleyError error) {
        this.error = error;
    }
}
