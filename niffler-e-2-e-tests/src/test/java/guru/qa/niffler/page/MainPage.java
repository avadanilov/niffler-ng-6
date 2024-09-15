package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
    private final SelenideElement statisticsElement = $x("//h2[text()='Statistics']/..");
    private final SelenideElement historyOfSpendingsElement
            = $x("//h2[text()='History of Spendings']/..");
    private final SelenideElement mainMenuButton = $("[aria-label='Menu']");
    private final SelenideElement mainMenu = $("ul[role='menu']");

    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).$$("td").get(5).click();
        return new EditSpendingPage();
    }

    public void checkThatTableContainsSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).should(visible);
    }

    public void checkPageLoaded() {
        statisticsElement.should(visible);
        historyOfSpendingsElement.should(visible);
    }

    private SelenideElement getMenuItem(String menuItemText) {
        String searchString = ".//li[.//a[text()='" + menuItemText + "']]";
        return mainMenu.$x(searchString);
    }

    public ProfilePage openProfilePage() {
        mainMenuButton.click();
        getMenuItem("Profile").click();
        return new ProfilePage().checkPageLoaded();
    }
}
