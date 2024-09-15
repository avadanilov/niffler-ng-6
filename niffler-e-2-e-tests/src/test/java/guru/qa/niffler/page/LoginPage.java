package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitButton = $("button[type='submit']");

    private final SelenideElement errorLoginMessage = $(".form__error-container");

    public MainPage login(String username, String password) {
        inputAndSubmitLoginAndPassword(username, password);
        return new MainPage();
    }

    public LoginPage inputAndSubmitLoginAndPassword(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        submitButton.click();
        return this;
    }

    public void checkErrorMessageIsPresent(String messageText) {
        errorLoginMessage.should(visible);
        errorLoginMessage.should(text(messageText));
    }
}
