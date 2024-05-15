package web.tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import web.api.APIRequestHandler;
import web.base.BaseTest;
import web.common.locatorConditions.LocatorCondition;
import web.common.pageCondition.PageCondition;
import web.configutils.ConfProperties;
import web.configutils.LoggingConfProperties;
import web.listeners.TestStatusListener;
import web.pages.LandingPage;

import java.nio.file.Paths;
import java.util.Map;

import static web.common.Configuration.apiRequestTimeout;
import static web.common.PlaywrightWrapper.*;

@Listeners(TestStatusListener.class)
public class TestTest extends BaseTest {
    LandingPage landingPage = new LandingPage();
    protected ConfProperties properties = new LoggingConfProperties(new ConfProperties());

    @Test
    public void test_LoginPageSuccessfulLoad() {
        String expectedLandingPageUrl = "https://www.saucedemo.com/";
        String expectedLandingPageHeader = "Swag Labs";
        String expectedLandingPageTitle = "Swag Labs";

        openUrlWithDefaultBase(BASE_URL, "");

        getPageActions().getUrl().shouldHave(PageCondition.url(expectedLandingPageUrl));
        landingPage.getHeader().shouldHave(LocatorCondition.text(expectedLandingPageHeader));
        getPageActions().getTitle().shouldHave(PageCondition.title(expectedLandingPageTitle));
    }

    @Test
    public void test_LoginPageSuccessfulLoad2() {
        openUrlWithDefaultBase(BASE_URL, "");

        find("#user-name").fill(properties.getProperty("Username"));
        find("#password").fill(properties.getProperty("Password"));
        find("#login-button").click();
        find(".header_secondary_container span.title")
                .getText().shouldHave(LocatorCondition.text("Products"));
    }

//    @Test()
//    public void test_API() {
//        APIRequest
//                .NewContextOptions newContextOptions =
//                new APIRequest
//                        .NewContextOptions()
//                        .setStorageStatePath(Paths.get("AuthenticationCookies"))
//                        .setExtraHTTPHeaders(Map.of("Cookie", "AuthenticationCookies"))
//                        .setTimeout(apiRequestTimeout)
//                        .setIgnoreHTTPSErrors(true);
//
//        APIRequestContext apiRequestContext = pw().getPlaywright().request().newContext(newContextOptions);
//
//        APIRequestHandler.sendApiRequestToSyncTheApp(
//                apiRequestContext,
//                "PROJECTS_SYNCHRONIZATION_URL",
//                "id");
//    }

}
