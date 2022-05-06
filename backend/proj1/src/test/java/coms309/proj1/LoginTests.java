package coms309.proj1;

import coms309.proj1.login.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.authentication.PreemptiveAuthProvider;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers.*;

import static org.hamcrest.Matchers.equalTo;

public class LoginTests
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

	/**
	 * Test that upon successful login, the user is redirected
	 */
	@Test
	public void validLoginTest() {
		io.restassured.RestAssured.with().body(new LoginRequest("james", "1234")).when().
				request("POST", "/login").then().assertThat().statusCode(302);
	}

	@Test
	public void loginSuccessTest() {
		io.restassured.RestAssured.get("/login/success").then().assertThat().statusCode(202);
		io.restassured.RestAssured.get("/login/success").then().assertThat().body("data.username", equalTo("james"));
	}

	@Test
	public void logoutSuccessTest() {
		io.restassured.RestAssured.get("/logout").then().assertThat().statusCode(204);
	}

	@Test
	public void userDoesNotExistFailedLoginTest() {
		io.restassured.RestAssured.with().body(new LoginRequest("alskdjfhak", "1234")).when().request("POST", "/login").then()
				.assertThat().statusCode(401);
	}

	@Test
	public void invalidPasswordFailedLoginTest() {
		io.restassured.RestAssured.with().body(new LoginRequest("james", "akjsdhfg")).when().request("POST", "/login").then().statusCode(401);
	}


}
