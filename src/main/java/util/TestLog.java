package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {
    private static final Logger logger = LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args) {
        logger.debug("DEBUG de prueba");
        logger.info("INFO de prueba");
        logger.error("ERROR de prueba");
    }
}
