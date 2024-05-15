package web.common.locatorConditions;

import web.common.LocatorActions;

public interface LocatorCondition {

    LocatorCondition visible = new IsVisibleCondition();
    LocatorCondition hidden = new IsHiddenCondition();
    LocatorCondition checked = new IsCheckedCondition();
    LocatorCondition editable = new IsEditableCondition();
    LocatorCondition readOnly = new IsReadOnlyCondition();
    LocatorCondition empty = new IsEmptyCondition();

    static LocatorCondition text(String expectedText) {
        return new TextCondition(expectedText);
    }

    static LocatorCondition texts(String[] expectedText) {
        return new TextsCondition(expectedText);
    }

    static LocatorCondition containsText(String expectedText) {
        return new ContainsTextCondition(expectedText);
    }

    static LocatorCondition notContainsText(String expectedText) {
        return new NotContainsTextCondition(expectedText);
    }

    static LocatorCondition value(String expectedValue) {
        return new ValueCondition(expectedValue);
    }

    static LocatorCondition attribute(String expectedTypeAttribute,String expectedAttributeText) {
        return new AttributeCondition(expectedTypeAttribute,expectedAttributeText);
    }

    static LocatorCondition hasClass(String expectedClass) {
        return new ClassCondition(expectedClass);
    }

    static LocatorCondition notHaveAttribute(String expectedTypeAttribute,String expectedAttributeText) {
        return new NotHaveAttributeCondition(expectedTypeAttribute,expectedAttributeText);
    }

    static LocatorCondition hasCount(int expectedCount) {
        return new HasCountCondition(expectedCount);
    }

    void verify(LocatorActions locatorActions);

}
