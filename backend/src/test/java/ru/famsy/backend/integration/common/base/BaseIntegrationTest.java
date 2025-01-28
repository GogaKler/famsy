package ru.famsy.backend.integration.common.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.famsy.backend.common.config.SecurityConfigProperties;
import ru.famsy.backend.integration.common.config.TestIntegrationConfig;
import ru.famsy.backend.integration.common.utils.TestIntegrationRequestUtils;
import ru.famsy.backend.integration.common.utils.TestIntegrationResponseUtils;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TestIntegrationConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@Tag("integration")
@Testcontainers
public abstract class BaseIntegrationTest {
  private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected SecurityConfigProperties securityConfigProperties;

  protected TestIntegrationRequestUtils requestUtils;

  @BeforeEach
  void setUp() {
    requestUtils = new TestIntegrationRequestUtils(mockMvc);
  }

  protected TestIntegrationRequestUtils.RequestBuilder performGet(String url) throws Exception {
    return requestUtils.createRequest(get(url))
            .withHeader("Accept-Language", "en-US,en;q=0.9")
            .withHeader("Accept", "application/json")
            .withHeader("X-Device-Fingerprint", "test-device-fingerprint")
            .withUserAgent(DEFAULT_USER_AGENT);
  }

  protected TestIntegrationRequestUtils.RequestBuilder performPost(String url, Object body) throws Exception {
    return requestUtils.createRequest(post(url))
            .withContent(body != null ? objectMapper.writeValueAsString(body) : "")
            .withHeader("Accept-Language", "en-US,en;q=0.9")
            .withHeader("Accept", "application/json")
            .withHeader("X-Device-Fingerprint", "test-device-fingerprint")
            .withUserAgent(DEFAULT_USER_AGENT);
  }

  protected TestIntegrationRequestUtils.RequestBuilder performDelete(String url) throws Exception {
    return requestUtils.createRequest(delete(url))
            .withHeader("Accept-Language", "en-US,en;q=0.9")
            .withHeader("Accept", "application/json")
            .withHeader("X-Device-Fingerprint", "test-device-fingerprint")
            .withUserAgent(DEFAULT_USER_AGENT);
  }

  protected MvcResult loginUser(@Valid LoginDTO loginDTO) throws Exception {
    MvcResult result = performPost("/auth/login", loginDTO)
            .perform()
            .andExpect(status().isOk())
            .andReturn();

    TestIntegrationResponseUtils.assertHasAuthCookies(requestUtils.getLastResponseCookies());
    return result;
  }

  protected MvcResult registerAndLoginUser(@Valid RegisterDTO registerDTO) throws Exception {
    MvcResult result = performPost("/auth/register", registerDTO)
            .perform()
            .andExpect(status().isOk())
            .andReturn();

    TestIntegrationResponseUtils.assertHasAuthCookies(requestUtils.getLastResponseCookies());
    return result;
  }

  protected MvcResult logoutUser() throws Exception {
    MvcResult result = performPost("/auth/logout", "")
            .perform()
            .andExpect(status().isOk())
            .andReturn();

    TestIntegrationResponseUtils.assertCookiesCleared(requestUtils.getLastResponseCookies());
    return result;
  }

  protected void clearAuthCookies() {
    Cookie clearedAccessToken = new Cookie("access_token", "");
    clearedAccessToken.setMaxAge(0);
    Cookie clearedRefreshToken = new Cookie("refresh_token", "");
    clearedRefreshToken.setMaxAge(0);
    requestUtils.setLastResponseCookies(new Cookie[]{clearedAccessToken, clearedRefreshToken});
    TestIntegrationResponseUtils.assertCookiesCleared(requestUtils.getLastResponseCookies());
  }

  protected void awaitWhenAccessTokenExpired() throws Exception {
    Thread.sleep(securityConfigProperties.getAccessTokenExpiration().toMillis() + 5);
  }
}
