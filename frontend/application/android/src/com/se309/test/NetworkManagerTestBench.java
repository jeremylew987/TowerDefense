package com.se309.test;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.se309.config.NetworkConfig;
import com.se309.net.NetworkHandle;
import com.se309.net.NetworkManager;
import com.se309.net.NetworkResponse;
import com.se309.net.RequestException;
import com.se309.net.ResponseContainer;

public class NetworkManagerTestBench {

    /**
     * A simple test bench for the network manager
     * @param nw Incoming NetworkManager object
     */
    public static void testNetworkFunctions(NetworkManager nw, Context context) {

        // Test the basic Gson serialization routines

        nw.setHost(NetworkConfig.MOCK_URL);

        System.out.println("Testing serialization...");

        String serial = nw.serialize(new UserDummy("foo", "bar"));

        System.out.println(serial);

        System.out.println("Testing deserialization...");

        UserDummy original = (UserDummy) nw.deserialize(serial, UserDummy.class);

        System.out.println("Username: " + original.getUsername() + " Password: " + original.getPassword());

        // Test the handler GET functionality

        System.out.println("Testing GET from mock server...");

        NetworkHandle handle = nw.spawnHandler("/test");


        handle.get(UserDummy.class, new NetworkResponse() {
            @Override
            public void onResponse(ResponseContainer container) {

                UserDummy back = (UserDummy) container.getBody();

                System.out.println("Username: " + back.getUsername() + " Password: " + back.getPassword());
            }
        });


        System.out.println("Tests finished!");
    }

    /**
     * I am going fucking bonkers
     */
    public static void SimpleVolleyGETRequest(Context context) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, NetworkConfig.MOCK_URL + "/test", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("!!!!!!!!!!!!!!!!!");
            }
        });

        mRequestQueue.add(mStringRequest);
    }

}
