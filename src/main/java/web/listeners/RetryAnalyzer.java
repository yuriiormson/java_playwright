package web.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import org.testng.Reporter;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {

        // default retry count
        int maxRetryCount = 2;

        if (retryCount < maxRetryCount) {
            try {
                Thread.sleep(5000); // add a delay between retries
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            retryCount++;
            Reporter.log("Retrying test method " + result.getMethod().getMethodName()
                    + " for the " + retryCount + " time.", true);
            return true;
        }
        return false;
    }
}
