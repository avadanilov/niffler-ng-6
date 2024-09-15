package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
  private final SelenideElement usernameInput = $("#username");
  private final SelenideElement passwordInput = $("#password");
  private final SelenideElement errorMessage = $("span.form__error");
  private final SelenideElement submitPasswordInput = $("#passwordSubmit");
  private final SelenideElement submitButton = $("button[type='submit']");
  private final SelenideElement successRegisterTitle = $(".form__paragraph_success");
  private final SelenideElement signInButton = $(".form_sign-in");

  public LoginPage signUp(String username, String password) {
    inputAndSubmitUsernameAndPassword(username, password);
    successRegisterTitle.should(text("Congratulations! You've registered!"));
    signInButton.click();
    return new LoginPage();
  }

  public RegisterPage inputAndSubmitUsernameAndPassword(String username, String password) {
    return inputAndSubmitUsernameAndPassword(username, password, password);
  }

  public RegisterPage inputAndSubmitUsernameAndPassword(String username, String password, String submitPassword) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitPasswordInput.setValue(submitPassword);
    submitButton.click();
    return this;
  }

  public void checkErrorMessage(String errorText) {
    errorMessage
            .should(visible)
            .should(text(errorText));
  }
}
