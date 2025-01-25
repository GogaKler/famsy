package ru.famsy.backend.modules.geo_location;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.famsy.backend.modules.geo_location.dto.GeoLocationDTO;
import ru.famsy.backend.modules.geo_location.dto.IpApiResponseDTO;

@Service
public class GeoLocationService {
  private static final Logger logger = LoggerFactory.getLogger(GeoLocationService.class);
  private static final String IP_API_URL = "http://ip-api.com/json/{ip}";

  public GeoLocationDTO getLocation(HttpServletRequest request) {
    String ip = extractIp(request);

    if (isLocalIp(ip)) {
      return buildLocalLocation(ip);
    }

    try {
      IpApiResponseDTO response = new RestTemplate().getForObject(
              IP_API_URL,
              IpApiResponseDTO.class,
              ip
      );

      if (response != null && "success".equals(response.getStatus())) {
        GeoLocationDTO geoLocationDTO = new GeoLocationDTO();
        geoLocationDTO.setIp(ip);
        geoLocationDTO.setCountry(response.getCountry());
        geoLocationDTO.setCity(response.getCity());
        geoLocationDTO.setRegion(response.getRegionName());
        geoLocationDTO.setTimezone(response.getTimezone());
        return geoLocationDTO;
      }

      logger.warn("Failed to get location for IP: {}", ip);
      return buildUnknownLocation(ip);

    } catch (Exception e) {
      logger.error("Error getting location for IP: " + ip, e);
      return buildUnknownLocation(ip);
    }
  }

  private String extractIp(HttpServletRequest request) {
    String[] headers = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };

    for (String header : headers) {
      String ip = request.getHeader(header);
      if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
        if (ip.contains(",")) {
          return ip.split(",")[0].trim();
        }
        return ip;
      }
    }

    return request.getRemoteAddr();
  }

  private boolean isLocalIp(String ip) {
    return ip == null
            || ip.startsWith("127.")
            || ip.startsWith("192.168.")
            || ip.startsWith("10.")
            || ip.equals("::1")
            || ip.equals("0:0:0:0:0:0:0:1")
            || ip.equals("localhost");
  }

  private GeoLocationDTO buildLocalLocation(String ip) {
    GeoLocationDTO geoLocationDTO = new GeoLocationDTO();
    geoLocationDTO.setIp(ip);
    geoLocationDTO.setCountry("Local");
    geoLocationDTO.setCity("Local");
    geoLocationDTO.setRegion("Local");
    geoLocationDTO.setTimezone("Local");
    return geoLocationDTO;
  }

  private GeoLocationDTO buildUnknownLocation(String ip) {
    GeoLocationDTO geoLocationDTO = new GeoLocationDTO();
    geoLocationDTO.setIp(ip);
    geoLocationDTO.setCountry("Unknown");
    geoLocationDTO.setCity("Unknown");
    geoLocationDTO.setRegion("Unknown");
    geoLocationDTO.setTimezone("Unknown");
    return geoLocationDTO;
  }
}
