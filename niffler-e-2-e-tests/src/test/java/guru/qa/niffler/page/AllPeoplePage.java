package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class AllPeoplePage {
    private final ElementsCollection allPeople = $("#all").$$("tr");

    public SelenideElement getTableItemWithName(String name) {
        return allPeople.filterBy(text(name)).first();
    }
}
