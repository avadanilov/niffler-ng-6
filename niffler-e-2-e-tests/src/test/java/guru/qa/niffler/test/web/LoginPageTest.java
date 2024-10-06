package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.Test;

/**
 * Для успешного прохождения тестов должно быть выполнено предусловие:
 * в системе есть пользователь с кредами "test"/"12345"
 */
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
                .login(username, password);
        new LoginPage().checkErrorMessageIsPresent(ERROR_MESSAGE_TEXT);
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadPassword() {
        String username = "test";
        String password = "wrongPassword";
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(username, password);
        new LoginPage().checkErrorMessageIsPresent(ERROR_MESSAGE_TEXT);
    }
}
