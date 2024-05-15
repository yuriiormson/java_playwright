package web.configutils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public final class EnvironmentUrlConfigReader {
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    public static String getBaseUrl() {
        Properties props = new Properties();
        try {
            FileInputStream input = new FileInputStream(CONFIG_FILE_PATH);
            props.load(input);
        } catch (IOException e) {
            log.error("No config.properties file found in src/test/resources/ directory", e);
        }
        String baseUrl = props.getProperty("BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("ERROR: No matching BASE_URL found in config.properties file");
        }

        return baseUrl;
    }

}
