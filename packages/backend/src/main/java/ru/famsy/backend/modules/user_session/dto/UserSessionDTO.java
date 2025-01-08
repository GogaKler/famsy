package ru.famsy.backend.modules.user_session.dto;

import java.time.LocalDateTime;

public class UserSessionDTO {
  private Long id;
  private String deviceFingerprint;
  private String deviceType;
  private String browser;
  private String os;
  private String deviceModel;
  private String ip;
  private String city;
  private String country;
  private LocalDateTime lastActivityAt;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public String getDeviceType() {
    return deviceType;
  }
  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getBrowser() {
    return browser;
  }
  public void setBrowser(String browser) {
    this.browser = browser;
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

  public LocalDateTime getLastActivityAt() {
    return lastActivityAt;
  }
  public void setLastActivityAt(LocalDateTime lastActivityAt) {
    this.lastActivityAt = lastActivityAt;
  }

  public String getDeviceFingerprint() {
    return deviceFingerprint;
  }
  public void setDeviceFingerprint(String deviceFingerprint) {
    this.deviceFingerprint = deviceFingerprint;
  }
}
