package info.gedlek.tests;

import info.gedlek.model.Pet;
import info.gedlek.utils.TestDataGenerator;
import info.gedlek.utils.annotations.Positive;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static info.gedlek.asserters.PetAsserter.assertThat;

@DisplayName("Pet API tests")
class PetApiTest extends BaseTest {

    @Test
    @Positive
    @DisplayName("POST /pet - should create a new pet with random data")
    void shouldCreateAndGetPet() {
        // given
        var pet = TestDataGenerator.generateDefaultPet();

        // when
        var createdPet = petStoreApiClient.createPet(pet);

        // then
        assertThat(createdPet)
                .toHaveName(pet.getName())
                .toHaveStatus(pet.getStatus());

        var fetchedPet = petStoreApiClient.getPetById(pet.getId());

        assertThat(fetchedPet)
                .toHaveId(pet.getId())
                .toHaveName(pet.getName())
                .toHaveCategory(pet.getCategory())
                .toHavePhotoUrls(pet.getPhotoUrls())
                .toHaveStatus(pet.getStatus());
    }


    @ParameterizedTest(name = "Run #{index}: Create pet with status: {0}")
    @Positive
    @EnumSource(Pet.StatusEnum.class)
    void shouldCreatePetWithDifferentStatuses(Pet.StatusEnum status) {
        // given
        var pet = TestDataGenerator.generateDefaultPet();
        pet.setStatus(status);

        // when
        var createdPet = petStoreApiClient.createPet(pet);

        // then
        assertThat(createdPet)
                .toHaveStatus(status)
                .toHaveName(pet.getName());
    }
}