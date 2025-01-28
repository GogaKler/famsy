import { BaseEntity } from '@shared/entity';
import type { UserDTO } from '../dto/user-dto';

export class UserEntity extends BaseEntity {
  public username: string;
  public email: string;

  constructor(dto: UserDTO) {
    super(dto);
    this.username = dto.username;
    this.email = dto.email;
  }
}
