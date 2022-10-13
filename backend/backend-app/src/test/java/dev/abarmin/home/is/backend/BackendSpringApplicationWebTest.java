package dev.abarmin.home.is.backend;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import dev.abarmin.home.is.backend.page.object.FlatsEditPage;
import dev.abarmin.home.is.backend.page.object.FlatsListPage;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.codeborne.selenide.Selenide.*;

/**
 * @author Aleksandr Barmin
 */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendSpringApplicationWebTest {
  @LocalServerPort
  private int port = 8080;

  @Test
  @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
  void flatsCreate() {
    var values = Map.of(
        "title", UUID.randomUUID().toString(),
        "alias", UUID.randomUUID().toString()
    );

    var flatsPage = open("http://localhost:" + port + "/readings/flats", FlatsListPage.class);
    var createFlatPage = flatsPage.createFlat();

    createFlatPage.title(values.get("title"));
    createFlatPage.alias(values.get("alias"));
    var withNewFlat = createFlatPage.save();

    withNewFlat.allFlats().shouldHave(CollectionCondition.texts(values.get("title")));


  }
}