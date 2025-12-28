package info.gedlek.api;

import info.gedlek.model.Pet;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetStoreApiClient {

    private final RequestSpecification requestSpec;

    public PetStoreApiClient(String baseUri) {
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    @Step("Send POST /pet request")
    public Response createPetResponse(Object body) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/pet");
    }

    @Step("Create a new pet id: {pet.id}")
    public Pet createPet(Pet pet) {
        return createPetResponse(pet)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .as(Pet.class);
    }

    @Step("Send GET /pet/{petId} request")
    public Response getPetByIdResponse(Long petId) {
        return given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + petId);
    }

    @Step("Get pet by ID: {petId}")
    public Pet getPetById(Long petId) {
        return getPetByIdResponse(petId)
                .then()
                .statusCode(200)
                .extract()
                .as(Pet.class);
    }

    @Step("Send DELETE /pet/{petId} request")
    public Response deletePetResponse(Long petId) {
        return given()
                .spec(requestSpec)
                .when()
                .delete("/pet/" + petId);
    }

    @Step("Delete pet by ID: {petId}")
    public void deletePet(Long petId) {
        deletePetResponse(petId)
                .then()
                .statusCode(200);
    }
}