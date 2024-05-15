package web.common;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Data;
import web.common.pageCondition.PageCondition;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageActions {

    private final Page page;

    public PageActions shouldHave(PageCondition pageCondition) {
        pageCondition.verifyPage(this);
        return this;
    }

    public PageActions should(PageCondition pageCondition) {
        pageCondition.verifyPage(this);
        return this;
    }

    public PageActions getTitle() {
        page.title();
        return this;
    }

    public PageActions getUrl() {
        page.url();
        return this;
    }

    public void clickByRoleLinkWithText(String linkText) {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(linkText)).click();
    }

    public void clickByRoleButtonWithText(String buttonText) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(buttonText)).click();
    }

    public void setInputFilesByLabel(String labelText, String fileName) {
        page.getByLabel(labelText).setInputFiles(Paths.get("src/test/resources/" + fileName));
    }

    public void setInputFilesByLabelWithFullFilePath(String labelText, String filePath) {
        page.getByLabel(labelText).setInputFiles(Paths.get(filePath));
    }

    public List<String> getAllTextsBySelector(String cssSelector) {
        List<ElementHandle> elements = page.querySelectorAll(cssSelector);
        return elements.stream()
                .map(element -> element.textContent().trim())
                .collect(Collectors.toList());
    }

    public String getBeforePseudoElementColor(String selector) {

        return page.locator(selector)
                .evaluate("element => window.getComputedStyle(element, ':before').getPropertyValue('color')")
                .toString();
    }

    public String getBeforePseudoElementProperty(String selector, String typeOfProperty) {
        JSHandle styleHandle = page.evaluateHandle(
                "(element) => window.getComputedStyle(element, ':before')",
                page.locator(selector).first());

        return styleHandle.getProperty(typeOfProperty).jsonValue().toString();
    }
 }
