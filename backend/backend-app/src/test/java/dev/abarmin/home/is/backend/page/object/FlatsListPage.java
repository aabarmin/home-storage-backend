package dev.abarmin.home.is.backend.page.object;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

/**
 * @author Aleksandr Barmin
 */
public class FlatsListPage {
  public FlatsEditPage createFlat() {
    var createButton = $$x(".//a[contains(text(), 'Create new')]").first();
    createButton.click();
    return page(FlatsEditPage.class);
  }

  public FlatsEditPage edithFlatWithName(final String name) {
    var editButtonXPath = ".//tr[.//td[text()='" + name + ("']]//a[.//i[contains(@class, 'bi-pencil')]]");
    var editButton = $$x(editButtonXPath).first();
    editButton.click();
    return page(FlatsEditPage.class);
  }
}
