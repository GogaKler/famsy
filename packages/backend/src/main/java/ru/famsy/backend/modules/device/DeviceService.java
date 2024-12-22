package ru.famsy.backend.modules.device;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

  private static final String DEVICE_ID_COOKIE = "device_id";
  private static final int DEVICE_ID_LENGTH = 32;

  /**
   * Получаем или создаем информацию об устройстве, игнорируя различия в браузерах
   */
  public DeviceInfo getOrCreateDeviceInfo(HttpServletRequest request, HttpServletResponse response) {
    String deviceId = getDeviceIdFromCookie(request);

    if (deviceId == null) {
      deviceId = generateDeviceId(request);
      setDeviceIdCookie(response, deviceId);
    }

    String userAgent = request.getHeader("User-Agent");
    String appVersion = request.getHeader("X-App-Version");
    String deviceModel = request.getHeader("X-Device-Model");

    return parseDeviceInfo(deviceId, userAgent, appVersion, deviceModel);
  }

  /**
   * Генерирует уникальный ID устройства на основе характеристик устройства
   */
  private String generateDeviceId(HttpServletRequest request) {
    StringBuilder deviceCharacteristics = new StringBuilder();

    deviceCharacteristics.append(request.getRemoteAddr());

    String userAgent = request.getHeader("User-Agent");
    if (userAgent != null) {
      // Извлекаем только информацию об устройстве/ОС, игнорируя браузер
      if (userAgent.contains("Windows")) {
        deviceCharacteristics.append("_Windows");
      } else if (userAgent.contains("Mac OS")) {
        deviceCharacteristics.append("_MacOS");
      } else if (userAgent.contains("Linux")) {
        deviceCharacteristics.append("_Linux");
      } else if (userAgent.contains("Android")) {
        deviceCharacteristics.append("_Android");
      } else if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
        deviceCharacteristics.append("_iOS");
      }
    }

    String deviceModel = request.getHeader("X-Device-Model");
    if (deviceModel != null) {
      deviceCharacteristics.append("_").append(deviceModel);
    }

    return DigestUtils.sha256Hex(deviceCharacteristics.toString()).substring(0, DEVICE_ID_LENGTH);
  }

  private DeviceInfo parseDeviceInfo(String deviceId, String userAgent,
                                     String appVersion, String deviceModel) {
    String deviceType = "unknown";
    String os = "unknown";

    if (userAgent != null) {
      userAgent = userAgent.toLowerCase();

      if (userAgent.contains("windows")) {
        os = "Windows";
      } else if (userAgent.contains("macintosh") || userAgent.contains("mac os")) {
        os = "MacOS";
      } else if (userAgent.contains("linux")) {
        os = "Linux";
      } else if (userAgent.contains("android")) {
        os = "Android";
      } else if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
        os = "iOS";
      }

      if (userAgent.contains("mobile")) {
        deviceType = "Mobile";
      } else if (userAgent.contains("tablet")) {
        deviceType = "Tablet";
      } else {
        deviceType = "Desktop";
      }
    }

    return new DeviceInfo(
            deviceId,
            deviceType,
            os,
            appVersion != null ? appVersion : "unknown",
            deviceModel != null ? deviceModel : "unknown"
    );
  }

  private void setDeviceIdCookie(HttpServletResponse response, String deviceId) {
    Cookie cookie = new Cookie(DEVICE_ID_COOKIE, deviceId);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * 24 * 365); // 1 год
    cookie.setHttpOnly(true);
    cookie.setSecure(true); // для HTTPS
    response.addCookie(cookie);
  }

  private String getDeviceIdFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (DEVICE_ID_COOKIE.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
