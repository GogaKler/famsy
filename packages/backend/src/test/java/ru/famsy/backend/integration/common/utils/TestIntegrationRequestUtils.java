package ru.famsy.backend.integration.common.utils;

import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


public class TestIntegrationRequestUtils {
    private final MockMvc mockMvc;
    private Cookie[] lastResponseCookies;

    public TestIntegrationRequestUtils(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public RequestBuilder createRequest(MockHttpServletRequestBuilder request) {
        return new RequestBuilder(mockMvc, request);
    }

    public Cookie[] getLastResponseCookies() {
        return lastResponseCookies;
    }

    public void setLastResponseCookies(Cookie[] lastResponseCookies) {
        if (lastResponseCookies != null && lastResponseCookies.length > 0) {
            this.lastResponseCookies = lastResponseCookies;
        }
    }

    public class RequestBuilder {
        private final MockMvc mockMvc;
        private final MockHttpServletRequestBuilder request;
        private final HttpHeaders headers;

        private RequestBuilder(MockMvc mockMvc, MockHttpServletRequestBuilder request) {
            this.mockMvc = mockMvc;
            this.request = request;
            this.headers = new HttpHeaders();
        }

        public RequestBuilder withContent(String content) {
            if (content != null && !content.isEmpty()) {
                request.content(content);
                request.contentType(MediaType.APPLICATION_JSON);
            }
            return this;
        }

        public RequestBuilder withCookies(Cookie[] cookies) {
            if (cookies != null) {
                request.cookie(cookies);
            }
            return this;
        }

        public RequestBuilder withHeader(String name, String value) {
            headers.set(name, value);
            return this;
        }

        public RequestBuilder withUserAgent(String userAgent) {
            return withHeader("User-Agent", userAgent);
        }

        public ResultActions perform() throws Exception {
            request.headers(headers);

            if (lastResponseCookies != null) {
                request.cookie(lastResponseCookies);
            }

            ResultActions result = mockMvc.perform(request);

            setLastResponseCookies(result.andReturn().getResponse().getCookies());
            return result;
        }
    }
} 