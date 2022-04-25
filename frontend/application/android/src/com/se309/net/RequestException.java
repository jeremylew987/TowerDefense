package com.se309.net;

import com.android.volley.VolleyError;

/**
 * Exception for NetworkManager-related errors
 *
 * @author Gavin Tersteeg
 */
public class RequestException extends Exception{

    private VolleyError volleyError;
    private boolean isVolleyError;

    /**
     * Default constructor
     * @param errorMessage Message
     */
    public RequestException(String errorMessage) {
        super(errorMessage);

        isVolleyError = false;

        volleyError = null;
    }

    /**
     * Constructor with VolleyError
     * @param errorMessage Message
     * @param error Volley error
     */
    public RequestException(String errorMessage, VolleyError error) {
        super(errorMessage);

        isVolleyError = true;

        volleyError = error;
    }

}
