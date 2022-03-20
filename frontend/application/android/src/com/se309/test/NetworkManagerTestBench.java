package com.se309.test;

import com.se309.net.NetworkManager;

public class NetworkManagerTestBench {

    /**
     * A simple test bench for the network manager
     * @param nw Incoming NetworkManager object
     */
    public static void testNetworkFunctions(NetworkManager nw) {

        System.out.println("Testing serialization...");

        String serial = nw.serialize(new UserDummy("foo", "bar"));

        System.out.println(serial);

        System.out.println("Testing deserialization...");

        UserDummy original = (UserDummy) nw.deserialize(serial, UserDummy.class);

        System.out.println("Username: " + original.getUsername() + " Password: " + original.getPassword());
    }

}
