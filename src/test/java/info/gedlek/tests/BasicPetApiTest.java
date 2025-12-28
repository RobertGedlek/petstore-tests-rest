package info.gedlek.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Disabled("Basic example test - not part of any suite")
class BasicPetApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @DisplayName("POST /pet - should create a new pet")
    void shouldCreateNewPet() {
        String petJson = """
            {
              "id": 123456,
              "category": {
                "id": 1,
                "name": "dogs"
              },
              "name": "Burek",
              "photoUrls": [
                "string"
              ],
              "tags": [
                {
                  "id": 0,
                  "name": "string"
                }
              ],
              "status": "available"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Burek"))
                .body("id", equalTo(123456));
    }

}