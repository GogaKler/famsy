import { UserEntity } from '@/modules/users/entities/user.entity';

export interface IAuthToken {
  accessToken?: string;
  refreshToken?: string;
}

export interface IAuthTokenWithUser extends IAuthToken {
  user?: UserEntity;
}
