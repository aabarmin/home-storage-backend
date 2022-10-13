package dev.abarmin.home.is.backend.page.object;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

/**
 * @author Aleksandr Barmin
 */
public class FlatsEditPage {
  public SelenideElement title(final String title) {
    var titleElement = $$x(".//input[@name='title']").first();
    titleElement.setValue(title);
    return titleElement;
  }

  public SelenideElement alias(final String alias) {
    var aliasElement = $$x(".//input[@name='alias']").first();
    aliasElement.setValue(alias);
    return aliasElement;
  }

  public FlatsListPage save() {
    var saveButton = $$x(".//button[text()='Save']").first();
    saveButton.click();
    return page(FlatsListPage.class);
  }
}
