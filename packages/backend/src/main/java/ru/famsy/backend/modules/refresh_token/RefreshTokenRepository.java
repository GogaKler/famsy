package ru.famsy.backend.modules.refresh_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.famsy.backend.modules.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    List<RefreshTokenEntity> findAllByUser(UserEntity user);
    Optional<RefreshTokenEntity> findByDeviceId(String deviceId);
    void deleteByToken(String token);
    void deleteByUser(UserEntity user);
    void deleteByExpiryDateTime(LocalDateTime expiryDate);

    void deleteByExpiryDateTimeBefore(LocalDateTime expiryDateTimeBefore);

    List<RefreshTokenEntity> user(UserEntity user);

    Optional<RefreshTokenEntity> findByDeviceIdAndUser(String deviceId, UserEntity user);
}
