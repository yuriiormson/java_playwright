package web.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import web.utils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class PlaywrightWrapper {
    private static final ConcurrentHashMap<Long, PWContainer> pw = new ConcurrentHashMap<>();

    public static PWContainer pw() {
        return pw.computeIfAbsent(Thread.currentThread().threadId(), k -> {
            var playwright = Playwright.create();
            var browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setChannel("chrome")
                            .setHeadless(Configuration.headless)
                            .setTimeout(Configuration.browserToStartTimeout)
                            .setDevtools(Configuration.devTools)
                            .setSlowMo(Configuration.poolingInterval)
                            .setTracesDir(Paths.get(Configuration.tracesPath))
            );
            return new PWContainer(null, null, playwright, browser);
        });
    }

    public static void close() {
        long threadId = Thread.currentThread().threadId();

        if (pw.containsKey(threadId)) {
            pw.get(threadId).getPage().close();
            pw.get(threadId).getContext().close();
            pw.get(threadId).getBrowser().close();
            pw.get(threadId).getPlaywright().close();
            pw.remove(threadId);
        }

    }

    public static void initTestContext(String testName) {
        var newContextOptions = new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setIgnoreHTTPSErrors(true);

        newContextOptions.baseURL = Configuration.baseUrl;

        var pw = pw();
        var browserContext = pw.getBrowser().newContext(newContextOptions);
            browserContext.setDefaultTimeout(60000);
        if (Configuration.saveTraces) {
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setTitle(testName)
                    .setName(testName + DateTimeUtils.getCurrentDateTimeForTrace() + ".zip")
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true)
            );
        }
        var targetPage = browserContext.newPage();

        pw.setContext(browserContext);
        pw.setPage(targetPage);
    }

    public static void closeContext(String testName) {
        var pw = pw();

        var targetContext = pw.getContext();
        if (Configuration.saveTraces) {
            targetContext.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(Configuration.tracesPath, testName
                            + DateTimeUtils.getCurrentDateTimeForTrace() + ".zip"))
            );
        }
        pw.getPage().close();
        targetContext.close();
    }

    public static void pausePage() {
        pw().getPage().pause();
    }
    public static void navigateToURL(String url) {
        try {
            pw().getPage().navigate(url, new Page.NavigateOptions()
                    .setWaitUntil(WaitUntilState.COMMIT)
                    .setTimeout(Configuration.navigateToURLTimeout));
        } catch (Exception e) {
            Reporter.log("Current URL: " + pw().getPage().url(),true);
            log.error("An error occurred when navigate to URL: " + e.getMessage());
        }
    }

    public static void openUrlWithDefaultBase(String baseClientUrl, String url) {
        Configuration.baseUrl = baseClientUrl;
        var targetUrl = StringUtils.isNotBlank(Configuration.baseUrl) ? Configuration.baseUrl + url : url;
        navigateToURL(targetUrl);
    }

    public static LocatorActions find(String selector) {
        pw().getPage().waitForLoadState(LoadState.LOAD);
        return new LocatorActions(pw().getPage().locator(selector).first());
    }

    public static LocatorActions find(String selector, String text) {
        pw().getPage().waitForLoadState(LoadState.LOAD);
        return new LocatorActions(pw().getPage().locator(selector).filter(
                new Locator.FilterOptions().setHasText(text)
        ));
    }

    public static LocatorActions find(String selector1, String text, String selector2) {
        pw().getPage().waitForLoadState(LoadState.LOAD);
        return new LocatorActions(pw().getPage().locator(selector1).filter(
                new Locator.FilterOptions().setHasText(text)).locator(selector2));
    }

    public static LocatorActions findAll(String selector) {
        pw().getPage().waitForLoadState(LoadState.LOAD);
        return new LocatorActions(pw().getPage().locator(selector));
    }

    @Data
    public static class PWContainer {

        private BrowserContext context;
        private Page page;
        private final Playwright playwright;
        private final Browser browser;

        public PWContainer(BrowserContext browserContext, Page page, Playwright playwright, Browser browser) {
            this.context = browserContext;
            this.page = page;
            this.playwright = playwright;
            this.browser = browser;
        }

    }

    public static PageActions getPageActions() {
        return new PageActions(pw().getPage());
    }

    public static void saveCookiesAsJsonToFile(String filePath) {
        try {
            BrowserContext context = pw().getContext();
            List<Cookie> cookies = context.cookies();
            String cookiesJson = new Gson().toJson(cookies);
            Files.write(Paths.get(filePath), cookiesJson.getBytes());
            log.info("Cookies saved");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save cookies to: " + filePath, e);
        }
    }

    public static void loadCookies(String filePath) {
        try {
            String cookiesJson = new String(Files.readAllBytes(Paths.get(filePath)));
            List<Cookie> cookies = new Gson().fromJson(cookiesJson, new TypeToken<List<Cookie>>() {
            }.getType());
            pw().getContext().addCookies(cookies);
            log.info("Cookies loaded");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load cookies from: " + filePath, e);
        }
    }

    public static String saveAndLoadCookiesAsString(String filePath) {
        String cookiesForAPI = "";
        BrowserContext context = pw().getContext();
        var cookies = context.storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get(filePath)));

        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode jsonObject = (ObjectNode) mapper.readTree(cookies);
            ArrayNode cookiesArray = (ArrayNode) jsonObject.get("cookies");

            StringBuilder cookieString = new StringBuilder();
            String separator = "";

            for (int i = 0; i < cookiesArray.size(); i++) {
                ObjectNode cookie = (ObjectNode) cookiesArray.get(i);
                String name = cookie.get("name").asText();
                String value = cookie.get("value").asText();
                cookieString.append(separator).append(name).append("=").append(value);
                separator = "; ";
            }
            cookiesForAPI = cookieString.toString();
        } catch (Throwable e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }
        return cookiesForAPI;
    }

    public static Page switchToNewTabByClickOnLink(String clickSelector) {
        Page currentPage = pw().getPage();

        try {
            Page newPage = currentPage.waitForPopup(() -> {
                currentPage.click(clickSelector);
            });

            Reporter.log("Switched to the new tab: " + newPage.url(),true);

            return newPage;
        } catch (Exception e) {
            log.error("Exception when switching to the new tab: " + e.getMessage());
            return null;
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
}
