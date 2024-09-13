package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {
  private final SelenideElement usernameInput = $("#username");
  private final SelenideElement passwordInput = $("#password");
  private final SelenideElement submitPasswordInput = $("#passwordSubmit");
  private final SelenideElement submitButton = $("button[type='submit']");

  public MainPage signUp(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitPasswordInput.setValue(password);
    submitButton.click();
    return new MainPage();
  }
}
