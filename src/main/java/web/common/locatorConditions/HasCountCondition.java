package web.common.locatorConditions;

import com.microsoft.playwright.assertions.LocatorAssertions;
import lombok.AllArgsConstructor;
import web.common.Configuration;
import web.common.LocatorActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@AllArgsConstructor
public class HasCountCondition implements LocatorCondition {

    private final int expectedCount;

    @Override
    public void verify(LocatorActions locatorActions) {
        assertThat(locatorActions.getLocator()).hasCount(
                expectedCount,
                new LocatorAssertions.HasCountOptions().setTimeout(Configuration.defaultTimeout)
        );
    }

}
