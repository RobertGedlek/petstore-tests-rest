package info.gedlek.api;

import info.gedlek.model.Pet;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetApiClient {

    private final RequestSpecification requestSpec;

    public PetApiClient() {
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public Pet createPet(Pet pet) {
        return given()
                .spec(requestSpec)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .as(Pet.class);
    }

    public Pet getPetById(Long petId) {
        return given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + petId)
                .then()
                .statusCode(200)
                .extract()
                .as(Pet.class);
    }

    public void deletePet(Long petId) {
        given()
                .spec(requestSpec)
                .when()
                .delete("/pet/" + petId)
                .then()
                .statusCode(200);
    }
}