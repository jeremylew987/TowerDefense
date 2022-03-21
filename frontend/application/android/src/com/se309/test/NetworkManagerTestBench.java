package com.se309.test;

import com.se309.config.NetworkConfig;
import com.se309.net.NetworkHandle;
import com.se309.net.NetworkManager;
import com.se309.net.RequestException;

public class NetworkManagerTestBench {

    /**
     * A simple test bench for the network manager
     * @param nw Incoming NetworkManager object
     */
    public static void testNetworkFunctions(NetworkManager nw) {

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

        try {
            UserDummy getFromMock = (UserDummy) handle.get(UserDummy.class);

            System.out.println("Username: " + getFromMock.getUsername() + " Password: " + getFromMock.getPassword());
        } catch (RequestException e) {
            System.out.println("Could not perform GET");
        }

        System.out.println("Tests finished!");
    }

}
