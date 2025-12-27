import info.gedlek.api.PetApiClient; // Importujemy naszego nowego klienta
import info.gedlek.model.Category;
import info.gedlek.model.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PetApiTest {

    private final PetApiClient petApiClient = new PetApiClient();

    @Test
    @DisplayName("POST /pet - should create a new pet")
    void shouldCreateAndGetPet() {
        //given
        var myPet = new Pet();
        myPet.setId(123999L);
        myPet.setName("Burek");
        myPet.setStatus(Pet.StatusEnum.AVAILABLE);

        var category = new Category();
        category.setId(1L);
        category.setName("Dogs");
        myPet.setCategory(category);

        myPet.setPhotoUrls(List.of("url1", "url2"));

        //when
        Pet createdPet = petApiClient.createPet(myPet);

        //then
        assertThat(createdPet.getName()).isEqualTo("Burek");

        Pet fetchedPet = petApiClient.getPetById(123999L);

        assertThat(fetchedPet)
                .usingRecursiveComparison()
                .ignoringFields("tags")
                .isEqualTo(myPet);
    }
}