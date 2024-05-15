package web.common.locatorConditions;

import com.microsoft.playwright.assertions.LocatorAssertions;
import lombok.AllArgsConstructor;
import web.common.Configuration;
import web.common.LocatorActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@AllArgsConstructor
public class ClassCondition implements LocatorCondition {
    private final String expectedClass;

    @Override
    public void verify(LocatorActions locatorActions) {
        assertThat(locatorActions.getLocator()).hasClass(expectedClass,(
                new LocatorAssertions.HasClassOptions().setTimeout(Configuration.defaultTimeout)
                ));
    }

}
