package base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseAPI {
	
	public static String bearerToken=null;	
	
	public static void getToken()
	{
		RestAssured.baseURI = "http://jars.tekstac.com:9098/app/signin";
        String requestBody = """
            {
                "username":"admin",
                "password":"admin123"
            }
        """;

        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON) 
            .body(requestBody) 
            .post(); 
        bearerToken = response.jsonPath().getString("accessToken");
	}

    public static Response updateBacklog(String rollNo, String backlogCount) {
    	
    	RestAssured.baseURI = "http://jars.tekstac.com:9091";

        Response response = RestAssured
            .given()
            .pathParam("rollNo", rollNo)
            .pathParam("backlogCount", backlogCount)
            .auth()
            .oauth2(bearerToken)
            .contentType("application/json")
            .when()
            .put("/student/update-backlog/{rollNo}/{backlogCount}");

        System.out.println("API Response: " + response.asString());
        return response;
    }

    public static Response deleteRecord(String rollNo) {
    	
    	RestAssured.baseURI = "http://jars.tekstac.com:9091";
    	Response response=RestAssured.given()
                .header("Content-Type", "application/json")
                .pathParam("rollNo", rollNo)
                .auth()
                .oauth2(bearerToken)
                .when()
                .delete("/student/delete/{rollNo}")
                .then()
                .extract()
                .response();
    	System.out.println(response.asPrettyString());
        return response;
    }
    
 public static Response verifyRecord() {
    	
    	RestAssured.baseURI = "http://jars.tekstac.com:9091";
    	Response response=RestAssured.given()
                .header("Content-Type", "application/json")
                .auth()
                .oauth2(bearerToken)
                .when()
                .get("/student/view-all")
                .then()
                .extract()
                .response();
    	System.out.println(response.asString());
        return response;
    }
}

