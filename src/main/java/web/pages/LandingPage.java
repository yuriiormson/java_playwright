package web.pages;

import web.common.LocatorActions;

import static web.common.PlaywrightWrapper.find;

public class LandingPage {
    private static final String HEADER = ".login_logo";

    public LocatorActions getHeader() {
        return find(HEADER).getText();
    }
}
