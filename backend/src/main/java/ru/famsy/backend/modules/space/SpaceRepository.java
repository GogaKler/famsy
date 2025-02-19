package ru.famsy.backend.modules.space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.famsy.backend.modules.space.constants.SpaceStatus;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberStatus;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository extends JpaRepository<SpaceEntity, Long> {

  @Query("""
    SELECT s FROM SpaceEntity s
    JOIN s.members sm
    WHERE s.id = :spaceId
    AND s.status = SpaceStatus.ACTIVE
    AND sm.user.id = :spaceMemberId
    AND sm.status = SpaceMemberStatus.ACTIVE
  """)
  Optional<SpaceEntity> findByIdAndMemberId(
          @Param("spaceId") Long id,
          @Param("spaceMemberId") Long spaceMemberId
  );

  @Query("""
    SELECT DISTINCT s FROM SpaceEntity s
    JOIN s.members sm
    WHERE s.status = SpaceStatus.ACTIVE
    AND sm.user.id = :spaceMemberId
    AND sm.status = SpaceMemberStatus.ACTIVE
  """)
  List<SpaceEntity> findAllSpacesByMemberId(
          @Param("spaceMemberId") Long spaceMemberId
  );
}
