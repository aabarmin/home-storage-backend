package dev.abarmin.home.is.backend.page.object;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

/**
 * @author Aleksandr Barmin
 */
public class FlatsEditPage {
  public SelenideElement setTitle(final String title) {
    return getTitle().setValue(title);
  }

  public SelenideElement getTitle() {
    return $$x(".//input[@name='title']").first();
  }

  public SelenideElement getAlias() {
    return $$x(".//input[@name='alias']").first();
  }

  public SelenideElement setAlias(final String alias) {
    return getAlias().setValue(alias);
  }

  public FlatsListPage save() {
    var saveButton = $$x(".//button[text()='Save']").first();
    saveButton.click();
    return page(FlatsListPage.class);
  }
}
