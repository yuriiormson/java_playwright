package web.configutils;

import org.testng.Reporter;
import web.utils.ConsoleColors;

public class LoggingConfProperties extends ConfProperties {
    private final ConfProperties properties;

    public LoggingConfProperties(ConfProperties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        var value = properties.getProperty(key);
        Reporter.log(ConsoleColors.GREEN + "key: " + key + " value: " + value + ConsoleColors.RESET, true);
        return value;
    }
}
