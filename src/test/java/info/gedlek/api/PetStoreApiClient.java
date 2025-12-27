package info.gedlek.api;

import info.gedlek.model.Pet;
import info.gedlek.utils.Configuration;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetStoreApiClient {

    private final RequestSpecification requestSpec;

    public PetStoreApiClient() {
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(Configuration.getBaseUri())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    @Step("Create a new pet id: {pet.id}")
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

    @Step("Get pet by ID: {petId}")
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

    @Step("Delete pet by ID: {petId}")
    public void deletePet(Long petId) {
        given()
                .spec(requestSpec)
                .when()
                .delete("/pet/" + petId)
                .then()
                .statusCode(200);
    }
}