package info.gedlek.tests;

import info.gedlek.api.PetStoreApiClient;

public abstract class BaseTest {
    protected final PetStoreApiClient petApiClient = new PetStoreApiClient();

}
