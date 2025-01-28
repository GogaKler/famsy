package ru.famsy.backend.integration.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.Cookie;
import org.springframework.test.web.servlet.MvcResult;
import ru.famsy.backend.common.exception.dto.ErrorResponse;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntegrationResponseUtils {

  private static final ObjectMapper objectMapper = getObjectMapper();

  public static <T> T parseResponse(MvcResult result, Class<T> responseType) throws Exception {
    String content = result.getResponse().getContentAsString();
    return objectMapper.readValue(content, responseType);
  }

  public static <T> List<T> parseResponseList(MvcResult result, Class<T> elementType) throws Exception {
    String content = result.getResponse().getContentAsString();
    return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
  }

  public static ErrorResponse parseErrorResponse(MvcResult result) throws Exception {
    return parseResponse(result, ErrorResponse.class);
  }

  public static void assertHasAuthCookies(Cookie[] cookies) {
    assertNotNull(cookies, "Cookies should not be null");
    assertTrue(cookies.length >= 2, "Should have at least access and refresh tokens");

    boolean hasAccessToken = false;
    boolean hasRefreshToken = false;

    for (Cookie cookie : cookies) {
      if ("access_token".equals(cookie.getName())) {
        hasAccessToken = true;
        assertTrue(cookie.isHttpOnly(), "Access token should be httpOnly");
        assertTrue(cookie.getSecure(), "Access token should be secure");
      }
      if ("refresh_token".equals(cookie.getName())) {
        hasRefreshToken = true;
        assertTrue(cookie.isHttpOnly(), "Refresh token should be httpOnly");
        assertTrue(cookie.getSecure(), "Refresh token should be secure");
      }
    }

    assertTrue(hasAccessToken, "Should have access token cookie");
    assertTrue(hasRefreshToken, "Should have refresh token cookie");
  }

  public static void assertErrorResponse(ErrorResponse errorResponse, String error, String message) {
    assertNotNull(errorResponse, "Error response should not be null");
    assertEquals(error, errorResponse.getError(), "Error type should match");
    assertEquals(message, errorResponse.getMessage(), "Error message should match");
  }

  public static void assertCookiesCleared(Cookie[] cookies) {
    assertNotNull(cookies, "Cookies should not be null");
    assertTrue(cookies.length >= 2, "Should have at least access and refresh tokens");

    for (Cookie cookie : cookies) {
      if ("access_token".equals(cookie.getName()) || "refresh_token".equals(cookie.getName())) {
        assertEquals("", cookie.getValue(), "Cookie value should be empty");
        assertEquals(0, cookie.getMaxAge(), "Cookie max age should be 0");
      }
    }
  }

  /*
  * Проверка поля с текстом
  * */
  public static void assertFieldValidationError(MvcResult result, String field, String expectedMessage) throws Exception {
    Map<String, String> errors = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>() {}
    );
    assertTrue(errors.containsKey(field), "Should contain error for field: " + field);
    assertEquals(expectedMessage, errors.get(field), "Error message should match for field: " + field);
  }

  public static void assertHasFieldError(MvcResult result, String field) throws Exception {
    Map<String, String> errors = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>() {}
    );
    assertTrue(errors.containsKey(field), "Should contain error for field: " + field);
    assertNotNull(errors.get(field), "Error message should not be null for field: " + field);
  }

  static private ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }
} 