package ru.famsy.backend.common.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Configuration
public class ErrorConfig {

  @Bean
  public ErrorAttributes errorAttributes() {
    return new DefaultErrorAttributes() {

      @Override
      public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        errorAttributes.put("error", errorAttributes.get("error"));
        errorAttributes.put("message", errorAttributes.get("message"));
        errorAttributes.put("path", errorAttributes.get("path"));

        errorAttributes.remove("timestamp");
        errorAttributes.remove("status");
        errorAttributes.remove("trace");

        return errorAttributes;
      }
    };
  }
}
