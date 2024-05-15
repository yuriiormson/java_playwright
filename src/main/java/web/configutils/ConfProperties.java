package web.configutils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ConfProperties {
    protected FileInputStream fileInputStream;
    protected Properties properties;

    public ConfProperties() {
        try {
            fileInputStream = new FileInputStream("src/test/resources/conf.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.error("Unable to load properties", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("Unable to close FileInputStream", e);
                }
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
