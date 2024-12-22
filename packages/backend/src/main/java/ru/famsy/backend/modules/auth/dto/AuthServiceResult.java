package ru.famsy.backend.modules.auth.dto;

import ru.famsy.backend.modules.user.UserEntity;

public record AuthServiceResult(UserEntity userEntity, TokenPairDTO tokenPairDTO) {}
