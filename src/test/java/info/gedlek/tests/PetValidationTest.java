package info.gedlek.tests;

import info.gedlek.utils.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static info.gedlek.asserters.ResponseAsserter.assertThatResponse;

@DisplayName("Pet Endpoint - Validation & Error Handling")
class PetValidationTest extends BaseTest {

    @Test
    @DisplayName("GET /pet/{id} - should return 404 for non-existent pet")
    void shouldReturn404WhenPetNotFound() {
        // given
        long nonExistentId = -99999L;

        // when
        var response = petStoreApiClient.getPetByIdResponse(nonExistentId);

        // then
        assertThatResponse(response)
                .toHaveStatusCode(404)
                .toHaveErrorType("error")
                .toHaveErrorMessage("Pet not found");
    }

    @Test
    @DisplayName("DELETE /pet/{id} - should return 404 when deleting non-existent pet")
    void shouldReturn404WhenDeletingNonExistentPet() {
        // given
        long randomId = TestDataGenerator.getRandomPetId();

        // when
        var response = petStoreApiClient.deletePetResponse(randomId);

        // then
        assertThatResponse(response)
                .toHaveStatusCode(404);
    }

    @Test
    @DisplayName("POST /pet - should return 400 for invalid input")
    void shouldReturn400WhenInputIsInvalid() {
        // given
        var brokenJson = """
                { "id": 123, "name": 
                """;

        // when
        var response = petStoreApiClient.createPetResponse(brokenJson);

        // then
        assertThatResponse(response)
                .toHaveStatusCode(400);
    }
}