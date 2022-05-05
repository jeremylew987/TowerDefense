package coms309.proj1;

import coms309.proj1.login.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers.*;

public class LoginTests
{

	@BeforeAll
	static void setup() {
		RestAssured.baseURI = "http://coms-309-027.class.las.iastate.edu";
		RestAssured.port = 8080;
	}

//	@Test
//	public void validLoginTest() {
//		io.restassured.RestAssured.with().body(new LoginRequest("james", "1234")).when().
//				request("POST", "/login").then().assertThat().body("data.username", org.hamcrest.Matchers.equalTo("james"));
//	}

	@Test
	public void userDoesNotExistLoginTest() {
		io.restassured.RestAssured.with().body(new LoginRequest("alskdjfhak", "1234")).when().request("POST", "/login").then()
				.assertThat().statusCode(401);
	}

	@Test
	public void invalidPasswordLoginTest() {
		io.restassured.RestAssured.with().body(new LoginRequest("james", "akjsdhfg")).when().request("POST", "/login").then().statusCode(401);
	}


}
