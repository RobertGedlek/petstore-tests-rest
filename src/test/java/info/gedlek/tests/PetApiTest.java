package info.gedlek.tests;

import info.gedlek.model.Category;
import info.gedlek.model.Pet;
import info.gedlek.utils.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static info.gedlek.asserters.PetAsserter.assertThat;

@DisplayName("Pet API tests")
class PetApiTest extends  BaseTest {

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
        var createdPet = petApiClient.createPet(pet);

        // then
        assertThat(createdPet)
                .toHaveName(pet.getName())
                .toHaveStatus(pet.getStatus());

        var fetchedPet = petApiClient.getPetById(pet.getId());

        assertThat(fetchedPet)
                .toHaveId(pet.getId())
                .toHaveName(pet.getName())
                .toHaveCategory(category)
                .toHavePhotoUrls(pet.getPhotoUrls())
                .toHaveStatus(pet.getStatus());
    }


    @ParameterizedTest(name = "Run #{index}: Create pet with status ''{0}''")
    @EnumSource(Pet.StatusEnum.class)
    void shouldCreatePetWithDifferentStatuses(Pet.StatusEnum status) {
        // given
        var pet = TestDataGenerator.generateDefaultPet();
        pet.setStatus(status);

        // when
        var createdPet = petApiClient.createPet(pet);

        // then
        assertThat(createdPet)
                .toHaveStatus(status)
                .toHaveName(pet.getName());
    }
}