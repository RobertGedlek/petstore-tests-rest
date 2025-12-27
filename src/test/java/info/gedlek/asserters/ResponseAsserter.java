package info.gedlek.asserters;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseAsserter extends AbstractAssert<ResponseAsserter, Response> {

    private ResponseAsserter(Response response) {
        super(response, ResponseAsserter.class);
    }

    public static ResponseAsserter assertThatResponse(Response response) {
        return new ResponseAsserter(response);
    }

    @Step("Verify response status code is {expectedStatusCode}")
    public ResponseAsserter toHaveStatusCode(int expectedStatusCode) {
        if (actual.statusCode() != expectedStatusCode) {
            failWithMessage("Expected status code to be <%d> but was <%d>. \nResponse body: \n%s",
                    expectedStatusCode, actual.statusCode(), actual.getBody().asPrettyString());
        }
        return this;
    }

    @Step("Verify error type is '{expectedType}'")
    public ResponseAsserter toHaveErrorType(String expectedType) {
        var actualType = actual.jsonPath().getString("type");

        assertThat(actualType)
                .withFailMessage("Expected error type to be <%s> but was <%s>", expectedType, actualType)
                .isEqualTo(expectedType);

        return this;
    }

    @Step("Verify error message is '{expectedMessage}'")
    public ResponseAsserter toHaveErrorMessage(String expectedMessage) {
        var actualMessage = actual.jsonPath().getString("message");

        assertThat(actualMessage)
                .withFailMessage("Expected error message to be <%s> but was <%s>", expectedMessage, actualMessage)
                .isEqualTo(expectedMessage);

        return this;
    }
}