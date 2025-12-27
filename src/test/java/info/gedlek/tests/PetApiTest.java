package info.gedlek.tests;

import info.gedlek.api.PetApiClient;
import info.gedlek.model.Category;
import info.gedlek.model.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static info.gedlek.asserters.PetAsserter.assertThat;

class PetApiTest {

    private final PetApiClient petApiClient = new PetApiClient();

    @Test
    @DisplayName("POST /pet - should create a new pet")
    void shouldCreateAndGetPet() {
        // given
        var category = new Category();
        category.setId(1L);
        category.setName("Dogs");

        var pet = new Pet();
        pet.setId(123999L);
        pet.setName("Burek");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        pet.setCategory(category);
        pet.setPhotoUrls(List.of("url1", "url2"));

        // when
        var createdPet = petApiClient.createPet(pet);

        // then
        assertThat(createdPet)
                .toHaveName("Burek")
                .toHaveStatus(Pet.StatusEnum.AVAILABLE);

        var fetchedPet = petApiClient.getPetById(123999L);

        assertThat(fetchedPet)
                .toHaveId(123999L)
                .toHaveName("Burek")
                .toHaveCategory(category)
                .toHavePhotoUrls(List.of("url1", "url2"))
                .toHaveStatus(Pet.StatusEnum.AVAILABLE);
    }
}