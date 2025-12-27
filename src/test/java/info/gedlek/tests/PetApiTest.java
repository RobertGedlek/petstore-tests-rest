package info.gedlek.tests;

import info.gedlek.api.PetStoreApiClient;
import info.gedlek.model.Category;
import info.gedlek.utils.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static info.gedlek.asserters.PetAsserter.assertThat;

class PetApiTest {

    private final PetStoreApiClient petStoreApiClient = new PetStoreApiClient();

    @Test
    @DisplayName("POST /pet - should create a new pet with random data")
    void shouldCreateAndGetPet() {
        // given
        var category = new Category();
        category.setId((long) TestDataGenerator.getRandomCategoryId());
        category.setName(TestDataGenerator.getFaker().animal().name());

        var pet = TestDataGenerator.generateDefaultPet();
        pet.setCategory(category);

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
                .toHaveCategory(category)
                .toHavePhotoUrls(pet.getPhotoUrls())
                .toHaveStatus(pet.getStatus());
    }
}