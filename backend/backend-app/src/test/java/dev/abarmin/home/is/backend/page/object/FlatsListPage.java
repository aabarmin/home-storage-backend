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

  public ElementsCollection allFlats() {
    return $$x(".//table");
  }
}
