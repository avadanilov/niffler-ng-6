package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {
    private final SelenideElement pageHeader = $x("//h2[text()='Profile']");
    private final SelenideElement uploadNewPictureButton = $(".image__input-label");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement saveChangesButton = $(withText("Save changes"));
    private final SelenideElement showArchivedCheckbox = $(".PrivateSwitchBase-input");
    private final SelenideElement addNewCategoryInput = $("#category");
    private final SelenideElement archiveConfirmationDialog = $(".MuiDialog-container");
    private final SelenideElement closeButton = archiveConfirmationDialog.$(withText("Close"));
    private final SelenideElement archiveButton = archiveConfirmationDialog.$x(".//button[text()='Archive']");
    private final SelenideElement unarchiveButton = archiveConfirmationDialog.$x(".//button[text()='Unarchive']");
    private final ElementsCollection categories = addNewCategoryInput.$$x(".//../../form/following-sibling::div");
    private final String categoryChipLocator = ".MuiChip-labelMedium";

    private static void editCategoryInputField(String newCategoryName, SelenideElement category) {
        category.$("#category")
                .setValue(newCategoryName)
                .unfocus();
        category.$("button").click();
    }

    private SelenideElement getCategory(String categoryName) {
        categories.shouldHave(sizeGreaterThan(0));
        return categories.findBy(text(categoryName));
    }

    public ProfilePage editCategory(String categoryName, String newCategoryName) {
        SelenideElement category = getCategory(categoryName);
        category.$(categoryChipLocator).click();
        editCategoryInputField(newCategoryName, category);
        return this;
    }

    public ProfilePage editCategoryByClickEditButton(String categoryName, String newCategoryName) {
        SelenideElement category = getCategory(categoryName);
        category.$("[aria-label='Edit category']")
                .click();
        editCategoryInputField(newCategoryName, category);
        return this;
    }

    public ProfilePage archiveCategory(String categoryName) {
        SelenideElement category = getCategory(categoryName);
        category.$("[aria-label='Archive category']")
                .click();
        archiveConfirmationDialog.should(visible);
        archiveButton.click();
        return this;
    }

    public ProfilePage unarchiveCategory(String categoryName) {
        if (!showArchivedCheckbox.isSelected())
            showArchivedCheckbox.parent().click();
        SelenideElement category = getCategory(categoryName);
        category.$("[aria-label='Unarchive category']")
                .click();
        archiveConfirmationDialog.should(visible);
        unarchiveButton.click();
        return this;
    }

    public ProfilePage checkPageLoaded() {
        pageHeader.should(visible);
        return this;
    }


}
