package web.common.pageCondition;

import lombok.AllArgsConstructor;

import web.common.PageActions;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@AllArgsConstructor
public class ContainsTextInElementsListCondition implements PageCondition {
    private final String locator;
    private final String[] expectedText;

    @Override
    public void verifyPage(PageActions pageActions) {
        assertThat(pageActions.getPage().locator(locator)).containsText(expectedText);
    }

}