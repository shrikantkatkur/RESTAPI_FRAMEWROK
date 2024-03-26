package Day1;

import org.testng.annotations.Test;

import groovy.util.logging.Log;

import static io.restassured.RestAssured.*;//add manually
import static io.restassured.matcher.RestAssuredMatchers.*;//add manually
import static org.hamcrest.Matchers.*;//add manually

import java.util.HashMap;


public class HTTPRequests {
	int id;

	@Test(priority =1)
	public void getSingleUser() {
		given();
		when()
		.get("https://reqres.in/api/users?page=2")
		.then()
		.statusCode(200)
		.body("page",equalTo(2))
		.log().all();
		
	}
	/**@Test(priority =0)
	public void createUser() {
		HashMap data=new HashMap();
		data.put("Name","Shrikant");
		data.put("Job","Trainer");
		given()
		.contentType("application/json")
		.body(data)
		.when()
		.post("https://reqres.in/api/users")
		.then()
		.statusCode(201)
		.log().all();
		
	}
	**/
	
	@Test(priority =2)
	public void createUser() {
		HashMap data=new HashMap();
		data.put("Name","Shrikant");
		data.put("Job","Trainer");
		id=given()// we are storing the id from output
		.contentType("application/json")
		.body(data)
		.when()
		.post("https://reqres.in/api/users")
		.jsonPath().getInt("id");
		
		}
	@Test(priority =3,dependsOnMethods = {"createUser"})
	public void updateUser() {
		HashMap data=new HashMap();
		data.put("Name","Jyoti");
		data.put("Job","Billing Associate");
		given()// we are storing the id from output
		.contentType("application/json")
		.body(data)
		.when()
		.put("https://reqres.in/api/users/"+id)//id from createUser and Updating the same user
		.then()
		.statusCode(200)
		.log().all();
		
		}
	@Test(priority =4)
	public void deleteUser() {
		given()
		.when()
		.delete("https://reqres.in/api/users/"+id)
		.then()
		.statusCode(204)
		.log().all();

	}

}
