package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProfileTest {
    private static final Config CFG = Config.getInstance();
    private static final String USERNAME = "test";
    private static final String PASSWORD = "12345";
    @Test
    @Category(
            username = "test",
            archived = false
    )
    void archivedCategoryShouldPresentInCategoriesList(CategoryJson category) {
        MainPage mainPage =
                Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(USERNAME, PASSWORD);
        mainPage.checkPageLoaded();
        ProfilePage profilePage = mainPage.openProfilePage();
        profilePage.archiveCategory(category.name());
        assertCategoryInList(category.name(), true);
    }

    @Test
    @Category(
            username = "test",
            archived = true
    )
    void activeCategoryShouldPresentInCategoriesList(CategoryJson category) {
        MainPage mainPage =
                Selenide.open(CFG.frontUrl(), LoginPage.class)
                        .login(USERNAME, PASSWORD);
        mainPage.checkPageLoaded();
        ProfilePage profilePage = mainPage.openProfilePage();
        profilePage.unarchiveCategory(category.name());
        assertCategoryInList(category.name(), false);
    }


    private static void assertCategoryInList(String categoryName, boolean archived) {
        SpendApiClient spendApiClient = new SpendApiClient();
        List<CategoryJson> categoryList = spendApiClient.getAllCategories(USERNAME, false);
        boolean contains = categoryList.stream()
                .anyMatch(cat -> categoryName.equals(cat.name()) && cat.archived() == archived);
        assertTrue(contains, "Для категории " + categoryName + " не установлено свойство archived = " + archived);
    }



}
