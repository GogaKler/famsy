import { BaseApi } from '@shared/api';
import { type LoginDTO, type RegisterDTO } from '@entities/auth';
import type { UserDTO } from '@entities/user';
import { injectable } from 'tsyringe';
import type { AxiosResponse } from 'axios';

@injectable()
export class AuthApi extends BaseApi {
  constructor() {
    super('auth/');
  }

  async login(payload: LoginDTO): Promise<AxiosResponse<UserDTO>> {
    return this.post<UserDTO>('login', payload);
  }

  async register(payload: RegisterDTO): Promise<AxiosResponse<UserDTO>> {
    return this.post<UserDTO>('register', payload);
  }

  async logout(): Promise<void> {
    await this.post<void>('logout');
  }

  async checkAuth(): Promise<AxiosResponse<UserDTO>> {
    return this.get<UserDTO>('check-auth');
  }
}
