package info.gedlek.utils.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TestResultLoggerExtension implements TestWatcher {

    private static final Logger logger = LoggerFactory.getLogger(TestResultLoggerExtension.class);

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        logger.info("🚫 Test Disabled: {}", context.getDisplayName());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("✅ Test Passed: {}", context.getDisplayName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("⚠️ Test Aborted: {}", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("❌ Test Failed: {} | Error: {}", context.getDisplayName(), cause.getMessage());
    }
}