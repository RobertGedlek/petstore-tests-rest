package info.gedlek.tests;

import info.gedlek.api.PetStoreApiClient;
import info.gedlek.utils.Configuration;
import info.gedlek.utils.extensions.TestResultLoggerExtension;
import info.gedlek.utils.extensions.TimingExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        TimingExtension.class,
        TestResultLoggerExtension.class,
})
public abstract class BaseTest {
    protected PetStoreApiClient petStoreApiClient;

    @BeforeAll
    void setup() {
        petStoreApiClient = new PetStoreApiClient(Configuration.BASE_URI.get());
    }
}
