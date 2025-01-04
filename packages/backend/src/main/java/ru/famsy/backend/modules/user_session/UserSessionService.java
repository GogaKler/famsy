package ru.famsy.backend.modules.user_session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import ru.famsy.backend.common.exception.base.ForbiddenException;
import ru.famsy.backend.common.exception.base.NotFoundException;
import ru.famsy.backend.modules.auth.constants.SecurityConstants;
import ru.famsy.backend.modules.device_detection.DeviceDetectionService;
import ru.famsy.backend.modules.device_detection.dto.DeviceInfoDTO;
import ru.famsy.backend.modules.geo_location.GeoLocationService;
import ru.famsy.backend.modules.geo_location.dto.GeoLocationDTO;
import ru.famsy.backend.modules.user.UserEntity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserSessionService {
  private final UserSessionRepository userSessionRepository;
  private final DeviceDetectionService deviceDetectionService;
  private final GeoLocationService geoLocationService;

  public UserSessionService(
          UserSessionRepository userSessionRepository,
          DeviceDetectionService deviceDetectionService,
          GeoLocationService geoLocationService
  ) {
    this.userSessionRepository = userSessionRepository;
    this.deviceDetectionService = deviceDetectionService;
    this.geoLocationService = geoLocationService;
  }

  public UserSessionEntity createOrUpdateSession(UserEntity user, HttpServletRequest request) {
    String sessionId = generateSessionId();
    String deviceFingerprint = generateDeviceFingerprint(request);

    Optional<UserSessionEntity> existingSession = userSessionRepository.findByUserAndDeviceFingerprint(user, deviceFingerprint);

    if (existingSession.isPresent()) {
      UserSessionEntity userSession = existingSession.get();
      userSession.setSessionId(sessionId);
      return updateSession(userSession, request);
    }

    checkSessionsLimit(user);

    return createSession(user, sessionId, deviceFingerprint, request);
  }

  public UserSessionEntity getUserSessionById(Long id) {
    return userSessionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Сессия не найдена"));
  }

  public List<UserSessionEntity> getUserSessions(UserEntity user) {
    return userSessionRepository.findAllByUser(user);
  }

  public void deleteUserSession(Long id, UserEntity user) {
    UserSessionEntity userSession = getUserSessionById(id);

    if (!userSession.getUser().equals(user)) {
      throw new ForbiddenException("Вы не можете удалять чужие сессии");
    }

    deleteUserSessionBySessionId(userSession.getSessionId());
  }

  public UserSessionEntity getUserSessionBySessionId(String sessionId) {
    return userSessionRepository.findBySessionId(sessionId)
            .orElseThrow(() -> new NotFoundException("Сессия не существует"));
  }

  public void deleteUserSessionBySessionId(String sessionId) {
    userSessionRepository.deleteBySessionId(sessionId);
  }

  private UserSessionEntity updateSession(UserSessionEntity session, HttpServletRequest request) {
    updateSessionMetadata(session, request);
    session.setLastActivityAt(LocalDateTime.now());
    return userSessionRepository.save(session);
  }

  private UserSessionEntity createSession(
          UserEntity user,
          String sessionId,
          String deviceFingerprint,
          HttpServletRequest request
  ) {
    UserSessionEntity session = new UserSessionEntity();
    session.setUser(user);
    session.setDeviceFingerprint(deviceFingerprint);
    session.setSessionId(sessionId);
    session.setLastActivityAt(LocalDateTime.now());

    updateSessionMetadata(session, request);

    return userSessionRepository.save(session);
  }

  private void updateSessionMetadata(UserSessionEntity session, HttpServletRequest request) {
    DeviceInfoDTO deviceInfoDTO = deviceDetectionService.detectDevice(request);
    session.setDeviceType(deviceInfoDTO.getType());
    session.setOs(deviceInfoDTO.getOs());
    session.setBrowser(deviceInfoDTO.getBrowser());
    session.setDeviceModel(deviceInfoDTO.getModel());

    GeoLocationDTO geoLocationDTO = geoLocationService.getLocation(request);
    session.setIp(geoLocationDTO.getIp());
    session.setCity(geoLocationDTO.getCity());
    session.setCountry(geoLocationDTO.getCountry());
    session.setRegion(geoLocationDTO.getRegion());
    session.setTimezone(geoLocationDTO.getTimezone());
  }

  private void checkSessionsLimit(UserEntity user) {
    List<UserSessionEntity> sessions = userSessionRepository.findAllByUser(user);

    if (sessions.size() >= SecurityConstants.MAX_SESSIONS_PER_USER) {
      UserSessionEntity oldestSession = sessions.stream()
              .min(Comparator.comparing(UserSessionEntity::getLastActivityAt))
              .orElseThrow();

      deleteUserSessionBySessionId(oldestSession.getSessionId());
    }
  }

  @Transactional
  public void terminateOtherSessions(UserEntity user, String sessionId) {
    List<UserSessionEntity> sessions = userSessionRepository.findAllByUserAndSessionIdNot(user, sessionId);
    userSessionRepository.deleteAll(sessions);
  }

  /*
   * Генерация информации о девайсе
   * TODO: Сделать более стабильным.
   * */
  public String generateDeviceFingerprint(HttpServletRequest request) {
    StringBuilder fingerprint = new StringBuilder();

    // Основные заголовки, которые обычно стабильны
    String userAgent = request.getHeader("User-Agent");
    String platform = request.getHeader("Sec-Ch-Ua-Platform");

    fingerprint.append(userAgent != null ? userAgent : "unknown")
            .append("|")
            .append(platform != null ? platform : "unknown");

    // Для мобильных устройств
    String deviceModel = request.getHeader("X-Device-Model");
    if (deviceModel != null) {
      fingerprint.append("|").append(deviceModel);
    }

    // Добавляем IP адрес для большей точности
    String ipAddress = request.getRemoteAddr();
    if (ipAddress != null) {
      fingerprint.append("|").append(ipAddress);
    }

    return DigestUtils.sha256Hex(fingerprint.toString());
  }

  private String generateSessionId() {
    return UUID.randomUUID().toString();
  }
}
