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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.se309.config.NetworkConfig;

import java.lang.reflect.Type;

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

    // Gson stuff
    private GsonBuilder builder;


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

        //requestQueue = new RequestQueue(cache, network);

        // Quickly set up Gson stuff
        builder = new GsonBuilder();

        builder.setPrettyPrinting();

        // Start it up
        //requestQueue.start();

        requestQueue = Volley.newRequestQueue(context);

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
     */
    public void SendStringGET(final Type ty, final String endpoint, Context context, final NetworkResponse responseHandle) {

        System.out.println("SendStringGET to " + host + endpoint);


        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        // After this function is called, the caller thread should .wait() until volley has finished it's request
        // When that happens, the caller will be notified after it's response has been posted
        StringRequest request = new StringRequest(Request.Method.GET, host + endpoint, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println("Getting response...");

                ResponseContainer container = new ResponseContainer(deserialize(response.toString(), ty), false, null);

                responseHandle.onResponse(container);

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Fail!");

                ResponseContainer container = new ResponseContainer(error.getMessage(), true, error);

                responseHandle.onResponse(container);
            }

        });

        mRequestQueue.add(request);

        System.out.println("Placed in queue");
    }

    public String serialize(Object src) {
        Gson gson = builder.create();

        return gson.toJson(src);
    }

    public Object deserialize(String src, Type ty) {
        Gson gson = builder.create();

        return gson.fromJson(src, ty);
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
