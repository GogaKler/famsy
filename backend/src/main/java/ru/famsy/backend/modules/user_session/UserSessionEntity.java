package ru.famsy.backend.modules.user_session;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import ru.famsy.backend.common.entity.BaseEntity;
import ru.famsy.backend.modules.user.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class UserSessionEntity extends BaseEntity {

  @Schema(description = "Пользователь, которому принадлежит сессия", required = true)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Schema(description = "Идентификатор сессии", example = "session_123456", required = true)
  @Column(nullable = false, unique = true)
  private String sessionId;

  @Schema(description = "Уникальный отпечаток устройства", example = "fingerprint_abc123", required = true)
  @Column(nullable = false)
  private String deviceFingerprint;

  @Schema(description = "Время последней активности", example = "2023-10-27T12:00:00", required = true)
  @Column(nullable = false)
  private LocalDateTime lastActivityAt;

  @Schema(description = "Браузер устройства", example = "Chrome", required = true)
  @Column(nullable = false)
  private String browser;

  @Schema(description = "Тип устройства", example = "Desktop", required = true)
  @Column(nullable = false)
  private String deviceType;

  @Schema(description = "Операционная система", example = "Windows 10", required = true)
  @Column(nullable = false)
  private String os;

  @Schema(description = "Модель устройства", example = "Dell XPS 15")
  @Column
  private String deviceModel;

  @Schema(description = "IP адрес", example = "192.168.0.1", required = true)
  @Column(nullable = false)
  private String ip;

  @Schema(description = "Страна", example = "Россия")
  @Column
  private String country;

  @Schema(description = "Город", example = "Москва")
  @Column
  private String city;

  @Schema(description = "Регион", example = "Центральный")
  @Column
  private String region;

  @Schema(description = "Часовой пояс", example = "MSK")
  @Column
  private String timezone;

  public UserEntity getUser() {
    return user;
  }
  public void setUser(UserEntity user) {
    this.user = user;
  }

  public String getSessionId() {
    return sessionId;
  }
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getDeviceFingerprint() {
    return deviceFingerprint;
  }
  public void setDeviceFingerprint(String deviceFingerprint) {
    this.deviceFingerprint = deviceFingerprint;
  }

  public LocalDateTime getLastActivityAt() {
    return lastActivityAt;
  }
  public void setLastActivityAt(LocalDateTime lastActivityAt) {
    this.lastActivityAt = lastActivityAt;
  }

  public String getBrowser() {
    return browser;
  }
  public void setBrowser(String browser) {
    this.browser = browser;
  }

  public String getDeviceType() {
    return deviceType;
  }
  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getOs() {
    return os;
  }
  public void setOs(String os) {
    this.os = os;
  }

  public String getDeviceModel() {
    return deviceModel;
  }
  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }

  public String getRegion() {
    return region;
  }
  public void setRegion(String region) {
    this.region = region;
  }

  public String getTimezone() {
    return timezone;
  }
  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }
}