package info.gedlek.tests;

import info.gedlek.api.PetStoreApiClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected PetStoreApiClient petApiClient;

    @BeforeAll
    void setup() {
        petApiClient = new PetStoreApiClient();
    }
}
