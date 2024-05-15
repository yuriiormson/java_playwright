package web.common.locatorConditions;

import com.microsoft.playwright.assertions.LocatorAssertions;
import web.common.Configuration;
import web.common.LocatorActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class IsEditableCondition implements LocatorCondition {

    @Override
    public void verify(LocatorActions locatorActions) {
        assertThat(locatorActions.getLocator()).isEditable(
                new LocatorAssertions.IsEditableOptions().setTimeout(Configuration.defaultTimeout)
        );
    }

}