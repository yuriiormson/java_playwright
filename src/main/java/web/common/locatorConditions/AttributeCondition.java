package web.common.locatorConditions;

import com.microsoft.playwright.assertions.LocatorAssertions;
import lombok.AllArgsConstructor;
import web.common.Configuration;
import web.common.LocatorActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@AllArgsConstructor
public class AttributeCondition implements LocatorCondition {
    private final String expectedTypeAttribute;
    private final String expectedAttributeText;

    @Override
    public void verify(LocatorActions locatorActions) {
        assertThat(locatorActions.getLocator()).hasAttribute(expectedTypeAttribute,
                expectedAttributeText,(
                new LocatorAssertions.HasAttributeOptions().setTimeout(Configuration.defaultTimeout)
                ));
    }

}
