package com.se309.net;

import com.android.volley.VolleyError;

public class RequestException extends Exception{

    private VolleyError volleyError;
    private boolean isVolleyError;

    public RequestException(String errorMessage) {
        super(errorMessage);

        isVolleyError = false;

        volleyError = null;
    }

    public RequestException(String errorMessage, VolleyError error) {
        super(errorMessage);

        isVolleyError = true;

        volleyError = error;
    }

}
