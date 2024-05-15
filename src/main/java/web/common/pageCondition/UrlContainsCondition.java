package web.common.pageCondition;

import lombok.AllArgsConstructor;
import web.common.PageActions;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@AllArgsConstructor
public class UrlContainsCondition implements PageCondition {

    private final Pattern expectedPageUrl;

    @Override
    public void verifyPage(PageActions pageActions) {
        assertThat(pageActions.getPage()).hasURL(expectedPageUrl);
    }
}