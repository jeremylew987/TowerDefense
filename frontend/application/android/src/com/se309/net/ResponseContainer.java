package com.se309.net;

import com.android.volley.VolleyError;

/**
 * ResponseContainer.java
 *
 * This class is passed between the NetworkHandler and NetworkResponse during an exchange.
 */

public class ResponseContainer {

    private Object body;
    private boolean isError;
    private VolleyError error;

    public ResponseContainer(Object body, boolean isError, VolleyError error) {
        this.body = body;
        this.isError = isError;
        this.error = error;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public VolleyError getError() {
        return error;
    }

    public void setError(VolleyError error) {
        this.error = error;
    }
}
