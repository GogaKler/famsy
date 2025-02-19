package ru.famsy.backend.modules.space_member;

import jakarta.persistence.*;
import ru.famsy.backend.common.entity.BaseEntity;
import ru.famsy.backend.modules.space.SpaceEntity;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberRole;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberStatus;
import ru.famsy.backend.modules.user.UserEntity;

@Entity
@Table(name = "space_members")
public class SpaceMemberEntity extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "space_id", nullable = false)
  private SpaceEntity space;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SpaceMemberRole role;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SpaceMemberStatus status = SpaceMemberStatus.PENDING;

  public SpaceEntity getSpace() {
    return space;
  }
  public void setSpace(SpaceEntity space) {
    this.space = space;
  }

  public UserEntity getUser() {
    return user;
  }
  public void setUser(UserEntity user) {
    this.user = user;
  }

  public SpaceMemberRole getRole() {
    return role;
  }
  public void setRole(SpaceMemberRole role) {
    this.role = role;
  }

  public SpaceMemberStatus getStatus() {
    return status;
  }
  public void setStatus(SpaceMemberStatus status) {
    this.status = status;
  }

  @Transient
  public Long getUserId() {
    return user.getId();
  }
}
