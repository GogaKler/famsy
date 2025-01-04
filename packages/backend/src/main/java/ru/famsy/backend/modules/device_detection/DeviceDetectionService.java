package ru.famsy.backend.modules.device_detection;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import ru.famsy.backend.modules.device_detection.dto.DeviceInfoDTO;

@Service
public class DeviceDetectionService {
  public DeviceInfoDTO detectDevice(HttpServletRequest request) {
    String userAgentString = request.getHeader("User-Agent");
    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

    DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO();

    deviceInfoDTO.setType(detectDeviceType(userAgent));
    deviceInfoDTO.setOs(userAgent.getOperatingSystem().getName());
    deviceInfoDTO.setBrowser(userAgent.getBrowser().getName());
    deviceInfoDTO.setModel(detectDeviceModel(request));

    return deviceInfoDTO;
  }

  private String detectDeviceType(UserAgent userAgent) {
    if (userAgent.getOperatingSystem().getDeviceType().equals(DeviceType.MOBILE)) {
      return "MOBILE";
    } else if (userAgent.getOperatingSystem().getName().contains("Tablet")) {
      return "TABLET";
    } else {
      return "DESKTOP";
    }
  }

  private String detectDeviceModel(HttpServletRequest request) {
    String model = request.getHeader("X-Device-Model");
    if (model != null) {
      return model;
    }

    String userAgent = request.getHeader("User-Agent").toLowerCase();
    if (userAgent.contains("iphone")) {
      return "iPhone";
    } else if (userAgent.contains("ipad")) {
      return "iPad";
    } else if (userAgent.contains("samsung")) {
      return "Samsung";
    }

    return "Unknown";
  }
}
