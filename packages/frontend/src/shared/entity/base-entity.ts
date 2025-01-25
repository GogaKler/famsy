import type { BaseDTO } from '@shared/dto';

export class BaseEntity {
  public id: number;
  public createdAt: Date;
  public updatedAt: Date;
  public createdBy: string | number;
  public updatedBy: string | number;

  constructor(dto: BaseDTO) {
    this.id = dto.id;
    this.createdAt = new Date(dto.createdAt);
    this.updatedAt = new Date(dto.updatedAt);
    this.createdBy = dto.createdBy;
    this.updatedBy = dto.updatedBy;
  }
}