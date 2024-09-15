package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.Test;

public class LoginPageTest {
    private static final String ERROR_MESSAGE_TEXT = "Неверные учетные данные пользователя";
    private static final Config CFG = Config.getInstance();
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin() {
        String username = "test";
        String password = "12345";
        MainPage mainPage =
                Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(username, password);
        mainPage.checkPageLoaded();
    }
    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadLogin() {
        String username = "wrongCred";
        String password = "12345";
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .inputAndSubmitLoginAndPassword(username, password)
                .checkErrorMessageIsPresent(ERROR_MESSAGE_TEXT);
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadPassword() {
        String username = "test";
        String password = "wrongPassword";
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .inputAndSubmitLoginAndPassword(username, password)
                .checkErrorMessageIsPresent(ERROR_MESSAGE_TEXT);
    }
}
