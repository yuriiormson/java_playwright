package web.common.locatorConditions;

import com.microsoft.playwright.assertions.LocatorAssertions;
import web.common.Configuration;
import web.common.LocatorActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class IsHiddenCondition implements LocatorCondition {

    @Override
    public void verify(LocatorActions locatorActions) {
        assertThat(locatorActions.getLocator()).isHidden(new LocatorAssertions.IsHiddenOptions().setTimeout(
                Configuration.defaultTimeout)
        );
    }

}
