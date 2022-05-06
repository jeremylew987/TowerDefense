package coms309.proj1.usertests;

import coms309.proj1.login.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.authentication.PreemptiveAuthProvider;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTests
{

	@BeforeAll
	static void setup() {
		RestAssured.baseURI = "http://coms-309-027.class.las.iastate.edu";
		RestAssured.port = 8080;
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("james");
		authScheme.setPassword("1234");
		RestAssured.authentication = authScheme;
	}

	@Test
	public void adminTest() {
		io.restassured.RestAssured.get("/admin").then().assertThat().statusCode(202);
	}

	@Test
	public void homeTest() {
		io.restassured.RestAssured.get("/home").then().assertThat().statusCode(202);
	}

	@Test
	public void rootTest() {
		io.restassured.RestAssured.get("/").then().assertThat().statusCode(202);
	}

	@Test
	public void userTest() {
		io.restassured.RestAssured.get("/user").then().assertThat().statusCode(202);
		io.restassured.RestAssured.get("/user").then().assertThat().body("data.user.username", equalTo("james"));
	}

	@Test
	public void usersTest() {
		io.restassured.RestAssured.get("/users").then().assertThat().statusCode(202);
		io.restassured.RestAssured.get("/user").then().assertThat().body("data.user.username", equalTo("james"));

		List<String> values = RestAssured.when().get("/users")
				.then().extract().jsonPath()
				.getList("data.username");

		assertFalse(values.isEmpty());
		assertTrue(values.contains("james"));

	}

	@Test
	public void getFriendsTest() {
		io.restassured.RestAssured.get("/user/friends").then().assertThat().statusCode(202);
	}

	@Test
	public void fullFriendTest() {
		io.restassured.RestAssured.get("/user/friends/remove?user=jeremy").then().assertThat().statusCode(202);
		io.restassured.RestAssured.get("/user/friends/decline?user=jeremy").then().assertThat().statusCode(202);

		io.restassured.RestAssured.get("/user/friends/add?user=jeremy").then().assertThat().statusCode(202);
		io.restassured.RestAssured.get("/user/friends/sent").then().assertThat().statusCode(202);
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("jeremy");
		authScheme.setPassword("pass");
		RestAssured.authentication = authScheme;

		io.restassured.RestAssured.get("/user/friends/received").then().assertThat().statusCode(202);
		io.restassured.RestAssured.get("/user/friends/accept?user=jeremy").then().assertThat().statusCode(202);

	}


}
