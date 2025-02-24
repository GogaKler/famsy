package ru.famsy.backend.modules.space;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import ru.famsy.backend.modules.space.constants.SpaceStatus;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberStatus;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<SpaceEntity, Long>, QuerydslPredicateExecutor<SpaceEntity>, QuerydslBinderCustomizer<QSpaceEntity> {

  @Query("""
    SELECT s FROM SpaceEntity s
    JOIN s.members sm
    WHERE s.id = :spaceId
    AND s.status = "ACTIVE"
    AND sm.user.id = :spaceMemberId
    AND sm.status = "ACTIVE"
  """)
  Optional<SpaceEntity> findByIdAndMemberId(
          @Param("spaceId") Long id,
          @Param("spaceMemberId") Long spaceMemberId
  );

  default Page<SpaceEntity> findAllSpacesByMemberId(
          Long spaceMemberId,
          Predicate predicate,
          Pageable pageable
  ) {
    QSpaceEntity qSpaceEntity = QSpaceEntity.spaceEntity;

    BooleanExpression spaceMemberPredicate = qSpaceEntity.status.eq(SpaceStatus.ACTIVE)
            .and(qSpaceEntity.members.any().user.id.eq(spaceMemberId))
            .and(qSpaceEntity.members.any().status.eq(SpaceMemberStatus.ACTIVE))
            .and(predicate);

    return findAll(spaceMemberPredicate, pageable);
  }

  @Override
  default void customize(QuerydslBindings bindings, QSpaceEntity root) {
    bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
    bindings.bind(root.description).first(StringExpression::containsIgnoreCase);
    bindings.bind(root.type).first(SimpleExpression::eq);
    bindings.bind(root.status).first(SimpleExpression::eq);
  }
}
