import { type BaseDTO } from '@shared/dto';

export interface UserDTO extends BaseDTO {
  username: string;
  email: string;
}
