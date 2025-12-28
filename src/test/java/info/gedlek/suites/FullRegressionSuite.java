package info.gedlek.suites;

import info.gedlek.tests.PetApiTest;
import info.gedlek.tests.PetValidationTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        PetApiTest.class,
        PetValidationTest.class
})
public class FullRegressionSuite {
}