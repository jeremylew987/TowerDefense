package com.se309.net;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

/**
 * NetworkManager.java
 *
 * Does what it says on the tin. This class is responsible for maintaining the RequestQueue.
 * In addition, handles can be created (NetworkHandle.java) that allow for organized communication with RequestQueue.
 * These handles can be passed around and independently configured for their respective purpose. These are also thread safe.
 */

public class NetworkManager {

    // Incoming objects
    private Context context;
    private String host;


    // Queue specific objects
    private Cache cache;
    private RequestQueue requestQueue;
    private Network network;


    /**
     * The constructor for NetworkManager, should be started at the beginning of the application only once
     * @param context The app context
     * @param host The address to the server, will be used for NetworkHandles
     */
    public NetworkManager(Context context, String host) {

        // Save local instances
        this.context = context;
        this.host = host;

        // Set up RequestQueue
        cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);

        network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        // Start it up
        requestQueue.start();

    }

    /**
     * Spawns in a new network handle, and returns it to the caller
     * @param defaultResource The end section of the url that all requests will be sent to.
     * @return Newly generated
     */
    public NetworkHandle spawnHandler(String defaultResource) {
        NetworkHandle handle = new NetworkHandle(defaultResource, this);

        return handle;
    }

    /**
     * Will perform a GET from a resource, send that response to the caller, and then wake up the caller
     * @param caller The thread calling this function
     * @param resource
     */
    public void SendStringGET(final NetworkHandle caller, final String resource) {

        // After this function is called, the caller thread should .wait() until volley has finished it's request
        // When that happens, the caller will be notified after it's response has been posted
        StringRequest request = new StringRequest(Request.Method.GET, host + resource, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                ResponseContainer container = new ResponseContainer(resource, false, null);

                caller.response = container;

                synchronized (caller) {
                    caller.notify();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                ResponseContainer container = new ResponseContainer(error.getMessage(), true, error);

                synchronized (caller) {
                    caller.notify();
                }
            }

        });
    }


    /**
     * Getter-ville
     */

    public Context getContext() {
        return context;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Cache getCache() {
        return cache;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public Network getNetwork() {
        return network;
    }
}
