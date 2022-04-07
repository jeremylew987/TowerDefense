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
import com.se309.tower.R;

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
    public void friendTest(){//main code style for both friendlist and friend Request list
        JSONObject temp = new JSONObject();
        try {
            temp.put("username", "jeremy");
            temp.put("email", "jalewis1@iastate.edu");
            temp.put("role", "USER");
            temp.put("id", 47);
        }catch (Exception e){
            e.printStackTrace();
        }
        assertTrue(friend(temp));
    }
    public boolean friend(JSONObject friend){
        try {
            if((friend.getString("username")).equals("jeremy"))
                return true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
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

 



}
