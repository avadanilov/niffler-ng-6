package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.RegisterPage;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Для успешного прохождения тестов должно быть выполнено предусловие:
 * в системе есть пользователь с кредами "test"/"12345"
 */
public class RegisterPageTest {
    private static final String REGISTER_PAGE_URL = Config.getInstance().authUrl() + "register";

    @Test
    void shouldRegisterNewUser() {
        String username = "user_" + UUID.randomUUID();
        String password = "12345";
        LoginPage loginPage = Selenide.open(REGISTER_PAGE_URL, RegisterPage.class)
                .signUp(username, password);
        loginPage.checkPageLoaded();
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        String username = "test";
        String password = "123456";
        Selenide.open(REGISTER_PAGE_URL, RegisterPage.class)
                .inputAndSubmitUsernameAndPassword(username, password)
                .checkErrorMessage("Username `" + username + "` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        String username = "user_" + UUID.randomUUID();
        String password = "123456";
        String confirmPassword = "wrongConfirmPassword";
        Selenide.open(REGISTER_PAGE_URL, RegisterPage.class)
                .inputAndSubmitUsernameAndPassword(username, password, confirmPassword)
                .checkErrorMessage("Passwords should be equal");
    }

}
