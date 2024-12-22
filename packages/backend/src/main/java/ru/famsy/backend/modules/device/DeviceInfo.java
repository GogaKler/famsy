package ru.famsy.backend.modules.device;

public record DeviceInfo(
        String deviceId,
        String deviceType,
        String os,
        String appVersion,
        String deviceModel
) {}
