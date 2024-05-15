package web.common.locatorConditions;

import com.microsoft.playwright.assertions.LocatorAssertions;
import lombok.AllArgsConstructor;
import web.common.Configuration;
import web.common.LocatorActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@AllArgsConstructor
public class NotContainsTextCondition implements LocatorCondition {

    private final String notExpectedText;

    @Override
    public void verify(LocatorActions locatorActions) {
        assertThat(locatorActions.getLocator()).not().containsText(
                notExpectedText,
                new LocatorAssertions.ContainsTextOptions().setTimeout(Configuration.defaultTimeout));
    }

}