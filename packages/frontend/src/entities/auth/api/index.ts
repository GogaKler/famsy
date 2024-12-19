import type { UserEntity } from '@entities/user';
import { httpClient } from '@shared/api';
import type { TCheckAuthResponse, TLoginDto, TRegisterDto } from '@entities/auth';


export class AuthAPI {
  static async login(payload: TLoginDto): Promise<UserEntity> {
    return await httpClient.post<UserEntity>('auth/login', payload);
  }
  
  static async register(payload: TRegisterDto): Promise<UserEntity> {
    return await httpClient.post<UserEntity>('auth/register', payload);
  }

  static async logout(): Promise<void> {
    await httpClient.post<void>('auth/logout');
  }

  static async checkAuth(): Promise<TCheckAuthResponse> {
    return await httpClient.get<TCheckAuthResponse>('/auth/checkAuth');
  }
}