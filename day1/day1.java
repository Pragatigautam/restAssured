package day1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class day1 {
	@Test
	public void getUsers() {
		RestAssured.baseURI = "https://reqres.in/api";
		Response response = given().when().get("users/2");
		response.then().statusCode(200);
		// assertion
		response.then().assertThat().body("data.id", equalTo(2)).body("data.email", equalTo("janet.weaver@reqres.in"))
				.body("data.first_name", equalTo("Janet")).body("data.last_name", equalTo("Weaver"))
				.body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"));
		response.then().log().all();
	}

	@Test
	public void createUser() {
		RestAssured.baseURI = "https://reqres.in/api";

		// define payload
		String requestBody = "{\"name\": \"John Doe\", \"job\": \"Software Engineer\"}";
		Response response = given().contentType(ContentType.JSON).body(requestBody).when().post("/users");

		// validating status code
		response.then().statusCode(201);

		// Validate the response body (using Hamcrest matchers)
		response.then().assertThat().body("name", equalTo("John Doe")).body("job", equalTo("Software Engineer"));

	}

	@Test
	public void updateUser() {
		RestAssured.baseURI = "https://reqres.in/api";

		// Define the request payload
		String requestBody = "{\"name\": \"Pragati Gautam\", \"job\": \"Software Engineer\"}";
		Response response = given().contentType(ContentType.JSON).body(requestBody).when().put("/users/2");
		response.then().statusCode(200);

		response.then().assertThat().body("name", equalTo("Pragati Gautam")).body("job",
				equalTo("Software Engineer"));
	}

	@Test
	public void deleteUsers() {

		RestAssured.baseURI = "https://reqres.in/api";
		Response response = given().when().delete("users/2");

		response.then().statusCode(204);

	}
}
