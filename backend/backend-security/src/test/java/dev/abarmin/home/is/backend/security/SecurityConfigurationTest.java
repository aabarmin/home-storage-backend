package dev.abarmin.home.is.backend.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    SecurityConfiguration.class,
    TestSecurityController.class
})
@WebMvcTest(TestSecurityController.class)
@TestPropertySource(properties = {
    "security.cognito.region=eu-west-2",
    "security.cognito.user-pool-id=dummy",
    "security.cognito.client-id=dummy",
    "security.cognito.client-secret=dummy",
    "security.cognito.target-user-group=user-group"
})
class SecurityConfigurationTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  ClientRegistrationRepository repository;

  @Test
  void check_contextStarts() {
    assertThat(mockMvc).isNotNull();
  }

  @Test
  @WithAnonymousUser
  void check_authorizationRequired() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  @WithMockUser(authorities = "ROLE_DUMMY_AUTHORITY")
  void check_withAuthorizedUser() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @WithMockUser(authorities = "ROLE_HOME_USER")
  void check_withProperAuthority() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
  }
}