package web.base;

import lombok.extern.slf4j.Slf4j;
import web.common.PlaywrightWrapper;
import web.configutils.EnvironmentUrlConfigReader;
import web.utils.FileOperations;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static web.common.PlaywrightWrapper.*;

@Slf4j
public class BaseTest {
    public static final String BASE_URL = EnvironmentUrlConfigReader.getBaseUrl();

    @BeforeSuite
    public void beforeSuite() {
        //We delete build/pw/ folder to reduce amount of files that stores on local env.
        FileOperations.deleteFolder("build/pw/");

    }

    public void checkValueAndSkipTestIfEmpty(String value) {
        if (!StringUtils.isNotBlank(value)) {
            Reporter.getCurrentTestResult().setStatus(ITestResult.SKIP);
            Reporter.log("Skipping test because the value is null or empty", true);
            throw new SkipException("Skipping test due to null or empty value");
        }
    }

    public static String getRandomValueFromDropdown(String selector) {
        String locatorTexts = findAll(selector).getTextAsString();

        // Split the text by the newline or any other character that separates the values
        String[] values = locatorTexts.split("\n");

        // Prepare a new list, excluding any "Select" value
        List<String> possibleValues = new ArrayList<>();
        for (String value : values) {
            if (!value.equals("Select") && !value.isEmpty()) {
                possibleValues.add(value);
            }
        }
        log.info("List from Dropdown" + possibleValues);

        // If list is empty (i.e., all values were "Select"), handle appropriately
        if (possibleValues.isEmpty()) {
            log.error("No valid options in dropdown!");
            return null;  // or throw an exception as per your requirement
        }

        // Choose a random value from the possibleValues list
        Random random = new Random();
        return possibleValues.get(random.nextInt(possibleValues.size()));
    }


    public String getUrlAsSting() {
        return PlaywrightWrapper.pw().getPage().url();
    }


    public String getTitleAsSting() {
        return PlaywrightWrapper.pw().getPage().title();
    }

}
