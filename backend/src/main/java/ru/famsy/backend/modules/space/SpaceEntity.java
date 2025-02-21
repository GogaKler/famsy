package ru.famsy.backend.modules.space;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import ru.famsy.backend.common.entity.BaseEntity;
import ru.famsy.backend.modules.space.constants.SpaceStatus;
import ru.famsy.backend.modules.space.constants.SpaceType;
import ru.famsy.backend.modules.space_member.SpaceMemberEntity;
import ru.famsy.backend.modules.user.UserEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "spaces")
public class SpaceEntity extends BaseEntity {

  @Schema(
      description = "Название пространства",
      example = "Рабочее пространство",
      requiredMode = Schema.RequiredMode.REQUIRED
  )
  @Column(nullable = false)
  private String name;

  @Schema(
      description = "Описание пространства",
      example = "Пространство для совместной работы команды"
  )
  @Column
  private String description;

  @Schema(
          description = "Владелец пространства (создатель)",
          requiredMode = Schema.RequiredMode.REQUIRED
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private UserEntity owner;

  @Schema(
          description = "Тип пространства",
          example = "PERSONAL или FAMILY",
          requiredMode = Schema.RequiredMode.REQUIRED
  )
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SpaceType type;

  @Schema(
          description = "Статус пространства",
          example = "ACTIVE",
          requiredMode = Schema.RequiredMode.REQUIRED
  )
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SpaceStatus status = SpaceStatus.ACTIVE;

  @Schema(
          description = "Список участников пространства",
          hidden = true
  )
  @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SpaceMemberEntity> members = new HashSet<>();

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public UserEntity getOwner() {
    return owner;
  }
  public void setOwner(UserEntity owner) {
    this.owner = owner;
  }

  public SpaceType getType() {
    return type;
  }
  public void setType(SpaceType type) {
    this.type = type;
  }

  public SpaceStatus getStatus() {
    return status;
  }
  public void setStatus(SpaceStatus status) {
    this.status = status;
  }

  public Set<SpaceMemberEntity> getMembers() {
    return members;
  }
  public void setMembers(Set<SpaceMemberEntity> members) {
    this.members = members;
  }

  @Transient
  @Schema(
      description = "Идентификатор владельца пространства",
      example = "1"
  )
  public Long getOwnerId() {
    return owner != null ? owner.getId() : null;
  }
}
