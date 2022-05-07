import static com.se309.tower.CreateLoginPage.isValidEmail;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;


import com.se309.config.NetworkConfig;
import com.se309.net.NetworkHandle;
import com.se309.net.NetworkManager;
import com.se309.test.UserDummy;
import com.se309.tower.*;

public class JTest {

    private NetworkManager networkManager;

    @Before
    public void network_initNetwork() {
        // Set up initial network manager
        networkManager = new NetworkManager(null, NetworkConfig.BACKEND_URL);
    }

    @Test
    public void network_testGsonSerialize() {
        UserDummy dummy = new UserDummy("foo", "bar");

        String serial = networkManager.serialize(dummy);

        // Ensure that the expected Gson output is made
        assertEquals("{\n" +
                "  \"username\": \"foo\",\n" +
                "  \"password\": \"bar\"\n" +
                "}", serial);
    }

    @Test
    public void network_testGsonDeserialize() {
        String serial = "{\n" +
                "  \"username\": \"foo\",\n" +
                "  \"password\": \"bar\"\n" +
                "}";

        UserDummy dummyDeserialized = (UserDummy) networkManager.deserialize(serial, UserDummy.class);
        UserDummy dummyExpected = new UserDummy("foo", "bar");

        // Ensure that the two deserialized classes are the same
        assertEquals(dummyExpected, dummyDeserialized);
    }

    @Test
    public void network_testHandleSpawn() {
        NetworkHandle testHandle = networkManager.spawnHandler("endpoint");

        // Ensure that the manager does not spit out a null handler
        assertNotNull(testHandle);
    }

    @Test
    public void network_testHandleEndpoint() {
        NetworkHandle testHandle = networkManager.spawnHandler("endpoint");

        // Ensure that the manager does not spit out a null handler
        assertEquals(testHandle.getDefaultEndpoint(), "endpoint");
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }



    @Test
    public void emailCorrect(){
        assertTrue(isValidEmail("jalewis1@iastate.edu"));
    }

    @Test
    public void emailFalse(){
        assertFalse(isValidEmail("jal"));
    }

    @Test
    public void emailpartial(){
        assertFalse(isValidEmail("jal@"));
    }

    @Test
    public void emailpartial2(){
        assertFalse(isValidEmail("@gdgd"));
    }

    @Test
    public void gameConfigTest() {
        GameConfiguration config = new GameConfiguration("foo", 69);

        assertEquals(config.getSocketServerAddress(), "foo");
        assertEquals(config.getSocketServerPort(), 69);
    }

    @Test
    public void gameConfigDefaultTest() {
        GameConfiguration config = new GameConfiguration("foo", 69);

        assertEquals(config.getUserLoginToken(), "1cb8af81-92d6-4abc-baf6-8348529577ca");
    }

 



}
