package dev.abarmin.home.is.backend;

import dev.abarmin.home.is.backend.page.object.FlatsListPage;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author Aleksandr Barmin
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendSpringApplicationWebTest {
  @LocalServerPort
  private int port = 8080;

  @Test
  void flatsCreate() {
    var values = Map.of(
        "title", UUID.randomUUID().toString(),
        "alias", UUID.randomUUID().toString()
    );

    var flatsPage = open("http://localhost:" + port + "/readings/flats", FlatsListPage.class);
    var createFlatPage = flatsPage.createFlat();

    createFlatPage.setTitle(values.get("title"));
    createFlatPage.setAlias(values.get("alias"));
    var withNewFlat = createFlatPage.save();

    var editFlatPage = withNewFlat.edithFlatWithName(values.get("title"));

    editFlatPage.getTitle().shouldHave(value(values.get("title")));
    editFlatPage.getAlias().shouldHave(value(values.get("alias")));
  }
}