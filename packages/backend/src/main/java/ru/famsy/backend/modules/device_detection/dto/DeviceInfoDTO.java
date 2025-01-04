package ru.famsy.backend.modules.device_detection.dto;

public class DeviceInfoDTO {
  private String type;      // MOBILE, DESKTOP, TABLET
  private String os;        // iOS, Android, Windows, etc.
  private String browser;   // Chrome, Safari, etc.
  private String model;     // iPhone, Samsung, etc.

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public String getOs() {
    return os;
  }
  public void setOs(String os) {
    this.os = os;
  }

  public String getBrowser() {
    return browser;
  }
  public void setBrowser(String browser) {
    this.browser = browser;
  }

  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
}
