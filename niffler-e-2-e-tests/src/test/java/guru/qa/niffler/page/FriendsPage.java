package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class FriendsPage {
    private final ElementsCollection friendsNames = $("#friends").$$("div .MuiBox-root");

}
