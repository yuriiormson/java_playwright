package web.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import lombok.Data;
import web.common.locatorConditions.LocatorCondition;

@Data
public class LocatorActions {

    private final Locator locator;


    public void fill(String text) {
        locator.fill(text, new Locator.FillOptions().setTimeout(Configuration.locatorTimeout));
    }

    public LocatorActions fillAfterClear(String text) {
        clear();
        locator.fill(text, new Locator.FillOptions().setTimeout(Configuration.locatorTimeout));
        return this;
    }

    public void press(String key) {
        locator.press(key);
    }

    public LocatorActions click() {
        locator.click(new Locator.ClickOptions().setTimeout(Configuration.locatorTimeout));
        return this;
    }

    public LocatorActions dbClick() {
        locator.dblclick(new Locator.DblclickOptions().setTimeout(Configuration.locatorTimeout));
        return this;
    }

    public LocatorActions getText() {
        locator.textContent(new Locator.TextContentOptions().setTimeout(Configuration.locatorTimeout));
        return this;
    }

    public String getTextAsString() {
        return locator.textContent(new Locator.TextContentOptions().setTimeout(Configuration.locatorTimeout));
    }

    public String getValueAsString() {
        return locator.inputValue();
    }

    public LocatorActions clear() {
        locator.clear();
        return this;
    }

    public LocatorActions check() {
        locator.check(new Locator.CheckOptions().setTimeout(Configuration.locatorTimeout));
        return this;
    }

    public LocatorActions uncheck() {
        locator.uncheck(new Locator.UncheckOptions().setTimeout(Configuration.locatorTimeout));
        return this;
    }

    public LocatorActions selectByValue(String value) {
        locator.selectOption(value, new Locator.SelectOptionOptions().setTimeout(Configuration.locatorTimeout));
        return this;
    }

    public void selectByLabel(String label) {
        locator.selectOption(new SelectOption().setLabel(label),
                new Locator.SelectOptionOptions().setTimeout(Configuration.locatorTimeout));
    }

    public void selectByIndex(int index) {
        locator.selectOption(new SelectOption().setIndex(index),
                new Locator.SelectOptionOptions().setTimeout(Configuration.locatorTimeout));
    }

    public LocatorActions getAttribute(String name) {
        locator.getAttribute(name, new Locator.GetAttributeOptions().setTimeout(Configuration.defaultTimeout));
        return this;
    }

    public LocatorActions getInputValue() {
        locator.inputValue();
        return this;
    }

    public void pressSequentially(String text) {
        locator.pressSequentially(text);
    }

    public boolean isEditable() {
        return locator.isEditable();
    }

    public boolean isVisible() {
        return locator.isVisible();
    }

    public void waitFor(Locator.WaitForOptions options) {
        locator.waitFor(options);
    }

    public LocatorActions shouldBe(LocatorCondition locatorCondition) {
        locatorCondition.verify(this);
        return this;
    }

    public LocatorActions should(LocatorCondition locatorCondition) {
        locatorCondition.verify(this);
        return this;
    }

    public LocatorActions shouldHave(LocatorCondition locatorCondition) {
        locatorCondition.verify(this);
        return this;
    }

//    public LocationActions shouldHas(Condition condition) {
//        condition.verify(this);
//        return this;
//    }

}
