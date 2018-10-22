package hello;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestValidation {

    static final String KEY = "image";
    static final String VALUE = "test";

    @Test
    public void responseCodeIs200BodyEmptyTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY, VALUE);
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK).expectBody(equalTo(""));
        ResponseSpecification responseSpec = resBuilder.build();
        given().header("Content-Type", "application/json").body(jsonObject.toJSONString()).when().post("http://localhost:8081/api/image").then().spec(responseSpec);
    }

}
