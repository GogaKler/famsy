package ru.famsy.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Базовый DTO, содержащий стандартные поля идентификации и аудита")
public class BaseDTO {

  @Schema(description = "Идентификатор записи", example = "1")
  private Long id;

  @Schema(description = "Дата и время создания записи", example = "2023-10-27T12:00:00")
  private LocalDateTime createdAt;

  @Schema(description = "Дата и время последнего изменения записи", example = "2023-10-28T15:30:00")
  private LocalDateTime modifiedAt;

  @Schema(description = "Идентификатор пользователя, создавшего запись", example = "4")
  private String createdBy;

  @Schema(description = "Идентификатор пользователя, в последний раз изменившего запись", example = "10")
  private String modifiedBy;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }
}
