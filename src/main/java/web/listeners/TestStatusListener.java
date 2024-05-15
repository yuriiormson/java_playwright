package web.listeners;

import web.common.Configuration;
import web.common.PlaywrightWrapper;
import web.configutils.EnvironmentUrlConfigReader;
import web.utils.ConsoleColors;
import web.utils.DateTimeUtils;
import web.utils.FileOperations;
import org.testng.*;

public class TestStatusListener implements ITestListener {
    public static String getClassName(ITestResult result) {
        String className = result.getTestClass().toString();

        return className.substring(22, className.length() - 1);
    }

    private static String getTestRunTime(ITestResult result) {
        final long time = result.getEndMillis() - result.getStartMillis();

        return DateTimeUtils.getTimeInMinSecFormat(time);
    }

    private String getTestName(ITestResult result) {
        return result.getMethod().getMethodName();
    }

    public static String getReportHeader() {
        String header = "\t" + "Test Report\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";
        String currentDate = "\t" + "Date: "
                + DateTimeUtils.getCurrentDateTime()
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";
        String projectName = "\tProject: Test Test" + "\n";
        String baseURL = "\tBASE_URL: " + EnvironmentUrlConfigReader.getBaseUrl() + "\n";

        return header + currentDate + projectName + baseURL;
    }

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log(ConsoleColors.YELLOW
                + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + ConsoleColors.RESET, true);
        Reporter.log("Full Class Path : " + getClassName(result), true);
        Reporter.log("Test: " + result.getName() + " ---- Status: " + "\u001B[35m" + "STARTED" + "\u001B[0m",
                true);
        Reporter.log("\n",
                true);
        Reporter.log("[Main Operation] ", true);
        PlaywrightWrapper.initTestContext(getTestName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String tracesDirPath = Configuration.tracesPath;
        String traceFileNamePrefix = getTestName(result);
        Reporter.log("\n",
                true);
        Reporter.log("Test " + result.getName() + " ---- Status: " + "\u001B[32m" + "PASS" + "\u001B[0m"
                        + "\nRun Time: " + getTestRunTime(result),
                true);
        Reporter.log(ConsoleColors.YELLOW
                + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + ConsoleColors.RESET, true);
        PlaywrightWrapper.closeContext(getTestName(result));
        PlaywrightWrapper.close();
        FileOperations.deleteFilesWithPrefixInDirectory(tracesDirPath, traceFileNamePrefix);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("Test " + result.getName() + " ---- Status: " + "\u001B[31m" + "FAIL" + "\u001B[0m"
                        + "\nRun Time: " + getTestRunTime(result),
                true);
        Reporter.log(ConsoleColors.YELLOW
                + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + ConsoleColors.RESET, true);

        String className = result.getTestClass().getName();
        int totalTests = result.getTestContext().getAllTestMethods().length;
        int failedTests = result.getTestContext().getFailedTests().size();
        if (failedTests > totalTests / 2) {
            throw new SkipException("More than 50% of tests in class "
                    + className + " have failed. Skipping remaining tests.");
        }

        PlaywrightWrapper.closeContext(getTestName(result));
        PlaywrightWrapper.close();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String tracesDirPath = Configuration.tracesPath;
        String traceFileNamePrefix = getTestName(result);
        Reporter.log("Test " + result.getName() + " ---- Status: " + "\u001B[34m" + "SKIPPED" + "\u001B[0m"
                        + "\nRun Time: " + getTestRunTime(result),
                true);
        Reporter.log(ConsoleColors.YELLOW
                + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + ConsoleColors.RESET, true);
        PlaywrightWrapper.closeContext(getTestName(result));
        PlaywrightWrapper.close();
        FileOperations.deleteFilesWithPrefixInDirectory(tracesDirPath, traceFileNamePrefix);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String tracesDirPath = Configuration.tracesPath;
        String traceFileNamePrefix = getTestName(result);
        Reporter.log("Test " + result.getName() + " ---- Status: "
                        + "\u001B[33m" + " FAILED BUT WITHIN SUCCESS PERCENTAGE" + "\u001B[0m"
                        + "\nRun Time: " + getTestRunTime(result),
                true);
        Reporter.log(ConsoleColors.YELLOW
                + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + ConsoleColors.RESET, true);
        PlaywrightWrapper.closeContext(getTestName(result));
        PlaywrightWrapper.close();
        FileOperations.deleteFilesWithPrefixInDirectory(tracesDirPath, traceFileNamePrefix);
    }

    @Override
    public void onStart(ITestContext context) {
        Reporter.log(ConsoleColors.BLUE
                + "*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*/*/*/*/*/*/*/*/*"
                + ConsoleColors.RESET, true);
        Reporter.log("Test Suite " + context.getName() + "   ----   "
                        + ConsoleColors.PURPLE_BOLD
                        + " STARTED"
                        + ConsoleColors.RESET,
                true);
        Reporter.log(getReportHeader(), true);
        Reporter.log(ConsoleColors.BLUE
                + "*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*/*/*/*/*/*/*/*/*"
                + ConsoleColors.RESET, true);
    }

    @Override
    public void onFinish(ITestContext context) {
        Reporter.log("\n", true);
        Reporter.log(ConsoleColors.BLUE
                + "*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*/*/*/*/*/*/*/*/*"
                + ConsoleColors.RESET, true);
        Reporter.log("Test Suite " + context.getName() + "   ----   "
                        + ConsoleColors.PURPLE_BOLD
                        + " FINISHED"
                        + ConsoleColors.PURPLE_BOLD,
                true);
        Reporter.log(ConsoleColors.BLUE
                + "*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*/*/*/*/*/*/*/*/*"
                + ConsoleColors.RESET, true);
    }
}
