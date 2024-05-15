package web.common.pageCondition;

import lombok.AllArgsConstructor;
import web.common.PageActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@AllArgsConstructor
public class NotHaveTitleCondition implements PageCondition {

    private final String expectedPageTitle;

    @Override
    public void verifyPage(PageActions pageActions) {
        assertThat(pageActions.getPage()).not().hasTitle(expectedPageTitle);
    }
}