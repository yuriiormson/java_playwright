package web.common;

import com.microsoft.playwright.Locator;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.microsoft.playwright.options.WaitForSelectorState.HIDDEN;
import static web.common.PlaywrightWrapper.*;

@UtilityClass
@Slf4j
public class LoadersUtils{

    private static final String LOADER_OVERLAY = "#loader-overlay";

    public void waitUntilLoaderOverlayDisappears() {
        find(LOADER_OVERLAY).waitFor(new Locator.WaitForOptions().setState(HIDDEN));
    }

    public void waitForFunction(String expression, String parameter){
          pw().getPage().
                    waitForFunction(expression, parameter);
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Thread sleep interrupted:", e);
        }
    }
}
