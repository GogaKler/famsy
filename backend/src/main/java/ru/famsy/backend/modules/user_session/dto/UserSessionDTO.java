package ru.famsy.backend.modules.user_session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.famsy.backend.common.dto.BaseDTO;

import java.time.LocalDateTime;

@Schema(description = "DTO для представления данных сессии пользователя")
public class UserSessionDTO extends BaseDTO {

    @Schema(description = "Отпечаток устройства", example = "fingerprint_abc123")
    private String deviceFingerprint;

    @Schema(description = "Тип устройства", example = "Desktop")
    private String deviceType;

    @Schema(description = "Браузер устройства", example = "Chrome")
    private String browser;

    @Schema(description = "Операционная система", example = "Windows 10")
    private String os;

    @Schema(description = "Модель устройства", example = "Dell XPS 15")
    private String deviceModel;

    @Schema(description = "IP адрес", example = "192.168.0.1")
    private String ip;

    @Schema(description = "Город", example = "Москва")
    private String city;

    @Schema(description = "Страна", example = "Россия")
    private String country;

    @Schema(description = "Время последней активности", example = "2023-10-27T12:00:00")
    private LocalDateTime lastActivityAt;

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }
    public void setDeviceFingerprint(String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
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
}
