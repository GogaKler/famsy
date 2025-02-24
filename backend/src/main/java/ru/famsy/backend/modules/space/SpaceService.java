package ru.famsy.backend.modules.space;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.famsy.backend.common.exception.base.NotFoundException;
import ru.famsy.backend.modules.space.constants.SpaceStatus;
import ru.famsy.backend.modules.space.dto.SpaceCreateDTO;
import ru.famsy.backend.modules.space.dto.SpaceUpdateDTO;
import ru.famsy.backend.modules.space.exception.SpaceForbiddenException;
import ru.famsy.backend.modules.space.mapper.SpaceMapper;
import ru.famsy.backend.modules.space_member.SpaceMemberEntity;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberRole;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberStatus;
import ru.famsy.backend.modules.user.UserEntity;

@Service
public class SpaceService {
  private final SpaceMapper spaceMapper;
  private final SpaceRepository spaceRepository;

  public SpaceService(SpaceMapper spaceMapper, SpaceRepository spaceRepository) {
    this.spaceMapper = spaceMapper;
    this.spaceRepository = spaceRepository;
  }

  @Transactional
  public SpaceEntity createSpace(SpaceCreateDTO spaceCreateDTO, UserEntity owner) {
    SpaceEntity spaceEntity = spaceMapper.toEntity(spaceCreateDTO);
    spaceEntity.setOwner(owner);
    spaceEntity.setStatus(SpaceStatus.ACTIVE);

    SpaceMemberEntity spaceMemberEntity = new SpaceMemberEntity();
    spaceMemberEntity.setSpace(spaceEntity);
    spaceMemberEntity.setUser(owner);
    spaceMemberEntity.setRole(SpaceMemberRole.OWNER);
    spaceMemberEntity.setStatus(SpaceMemberStatus.ACTIVE);

    spaceEntity.getMembers().add(spaceMemberEntity);

    return spaceRepository.save(spaceEntity);
  }

  public SpaceEntity findSpaceByIdAndCurrentUser(Long id, UserEntity currentUser) {
    return spaceRepository.findByIdAndMemberId(id, currentUser.getId())
            .orElseThrow(() -> new NotFoundException("Пространство не найдено"));
  }

  @Transactional
  public SpaceEntity patchSpace(Long id, SpaceUpdateDTO spaceUpdateDTO, UserEntity currentUser) {
    SpaceEntity spaceEntity = findSpaceByIdAndCurrentUser(id, currentUser);

    boolean canEdit = spaceEntity.getMembers().stream()
            .filter(member -> member.getUser().getId().equals(currentUser.getId()))
            .anyMatch(member ->
                    member.getRole() == SpaceMemberRole.OWNER || member.getRole() == SpaceMemberRole.ADMIN
            );

    if (!canEdit) {
      throw SpaceForbiddenException.notAccessUpdate();
    }

    spaceMapper.patchEntity(spaceUpdateDTO, spaceEntity);
    return spaceRepository.save(spaceEntity);
  }

  @Transactional
  public void deleteSpace(Long id, UserEntity currentUser) {
    SpaceEntity spaceEntity = findSpaceByIdAndCurrentUser(id, currentUser);

    if (!spaceEntity.getOwner().getId().equals(currentUser.getId())) {
      throw SpaceForbiddenException.notAccess();
    }

    spaceEntity.setStatus(SpaceStatus.DELETED);
    spaceRepository.save(spaceEntity);
  }
}
