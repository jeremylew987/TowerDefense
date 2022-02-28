package com.se309.net;

import com.android.volley.VolleyError;

/**
 * ResponseContainer.java
 *
 * This class is passed between the NetworkHandler and NetworkManager during an exchange.
 */

public class ResponseContainer {

    private String body;
    private boolean isError;
    private VolleyError error;

    public ResponseContainer(String body, boolean isError, VolleyError error) {
        this.body = body;
        this.isError = isError;
        this.error = error;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
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
