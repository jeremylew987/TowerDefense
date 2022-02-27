package com.se309.net;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

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
     * @param defaultResource
     * @return
     */
    public NetworkHandle spawnHandler(String defaultResource) {
        NetworkHandle handle = new NetworkHandle(defaultResource, this);

        return handle;
    }
}
