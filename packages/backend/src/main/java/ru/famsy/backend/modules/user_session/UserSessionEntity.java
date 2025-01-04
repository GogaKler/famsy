package ru.famsy.backend.modules.user_session;

import jakarta.persistence.*;
import ru.famsy.backend.common.entity.BaseEntity;
import ru.famsy.backend.modules.user.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class UserSessionEntity extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  // Пользователь
  private UserEntity user;

  // Основная сессия пользователя
  @Column(nullable = false, unique = true)
  private String sessionId;

  @Column(nullable = false)
  // Уникальный отпечаток устройства
  private String deviceFingerprint;

  @Column(nullable = false)
  // Время последней активности
  private LocalDateTime lastActivityAt;

  // Информация об устройстве
  @Column(nullable = false)
  private String browser;

  @Column(nullable = false)
  private String deviceType;

  @Column(nullable = false)
  private String os;

  @Column
  private String deviceModel;

  // Геолокация
  @Column(nullable = false)
  private String ip;

  @Column
  private String country;

  @Column
  private String city;

  @Column
  private String region;

  @Column
  private String timezone;


  public UserEntity getUser() {
    return user;
  }
  public void setUser(UserEntity user) {
    this.user = user;
  }

  public String getDeviceFingerprint() {
    return deviceFingerprint;
  }
  public void setDeviceFingerprint(String deviceFingerprint) {
    this.deviceFingerprint = deviceFingerprint;
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

  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
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

  public LocalDateTime getLastActivityAt() {
    return lastActivityAt;
  }
  public void setLastActivityAt(LocalDateTime lastActivityAt) {
    this.lastActivityAt = lastActivityAt;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
}