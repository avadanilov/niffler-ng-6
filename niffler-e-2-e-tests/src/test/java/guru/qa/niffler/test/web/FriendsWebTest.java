package guru.qa.niffler.test.web;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.StaticUser;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType.Type.*;

@ExtendWith(BrowserExtension.class)
public class FriendsWebTest {
    private static final Config CFG = Config.getInstance();

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {
        open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .checkPageLoaded();
        FriendsPage friendsPage =
                open(CFG.frontUrl() + "people/friends", FriendsPage.class);
        friendsPage.getFriendsNames()
                .should(itemWithText(user.friend()));
    }


    @Test
    @ExtendWith(UsersQueueExtension.class)
    void friendsTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
        open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .checkPageLoaded();
        FriendsPage friendsPage =
                open(CFG.frontUrl() + "people/friends", FriendsPage.class);
        friendsPage.getFriendsNames()
                .should(empty);
        friendsPage.getEmptyListPanel()
                .should(text("There are no users yet"));
    }


    @Test
    @ExtendWith(UsersQueueExtension.class)
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
        open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .checkPageLoaded();
        FriendsPage friendsPage =
                open(CFG.frontUrl() + "people/friends", FriendsPage.class);
        friendsPage.getRequestNames()
                .should(itemWithText(user.income()));
    }

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
        open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .checkPageLoaded();
        AllPeoplePage allPeoplePage =
                open(CFG.frontUrl() + "people/all", AllPeoplePage.class);
        allPeoplePage.getTableItemWithName(user.outcome())
                .should(text("Waiting..."));
    }

}
