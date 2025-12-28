package info.gedlek.utils.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger logger = LoggerFactory.getLogger(TimingExtension.class);
    private static final String START_TIME = "start time";

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        getStore(context).put(START_TIME, System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        long startTime = getStore(context).remove(START_TIME, long.class);
        long duration = System.currentTimeMillis() - startTime;

        logger.info("⏱️ Test [{}] took {} ms", context.getDisplayName(), duration);

        if (duration > 2000) {
            logger.warn("⚠️ SLOW TEST DETECTED! (> 2000ms)");
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }
}