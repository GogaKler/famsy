package ru.famsy.backend.modules.user_session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.famsy.backend.modules.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {

  List<UserSessionEntity> findAllByUser(UserEntity user);

  List<UserSessionEntity> findAllByLastActivityAtBefore(LocalDateTime expirationThreshold);

  Optional<UserSessionEntity> findBySessionId(String sessionId);

  void deleteBySessionId(String sessionId);

  List<UserSessionEntity> findAllByUserAndSessionIdNot(UserEntity user, String currentSessionId);

  Optional<UserSessionEntity> findByUserAndDeviceFingerprint(UserEntity user, String deviceFingerprint);
}

