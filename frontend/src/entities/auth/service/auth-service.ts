import { AuthStateActions, type LoginDTO, type RegisterDTO, useAuthStore } from '@entities/auth';
import { type UserDTO, UserEntity } from '@entities/user';
import { BaseService } from '@shared/service';
import type { AxiosResponse } from 'axios';
import { inject, injectable } from 'tsyringe';
import { AuthApi } from '../api/auth-api';

type TAuthEntityName = 'auth';

@injectable()
export class AuthService extends BaseService<TAuthEntityName, AuthStateActions> {
  public readonly unAuthUserIdentifier: number = 0;
  public readonly authStore: ReturnType<typeof useAuthStore>;

  constructor(
    @inject(AuthApi) private readonly authApi: AuthApi,
  ) {
    super('auth');
    this.authStore = useAuthStore();
  }

  async login(loginDTO: LoginDTO): Promise<UserEntity> {
    return this.executeAction(
      this.unAuthUserIdentifier,
      AuthStateActions.LOGIN,
      async () => {
        const responseUser: AxiosResponse<UserDTO> = await this.authApi.login(loginDTO);
        const userDTO: UserDTO = responseUser.data;
        const userEntity: UserEntity = new UserEntity(userDTO);
        this.authStore.setUser(userEntity);
        return userEntity;
      },
    );
  }

  async register(registerDTO: RegisterDTO): Promise<UserEntity> {
    return this.executeAction(
      this.unAuthUserIdentifier,
      AuthStateActions.REGISTER,
      async () => {
        const responseUser: AxiosResponse<UserDTO> = await this.authApi.register(registerDTO);
        const userDTO: UserDTO = responseUser.data;
        const userEntity: UserEntity = new UserEntity(userDTO);
        this.authStore.setUser(userEntity);
        return userEntity;
      },
    );
  }

  async logout(id: number): Promise<void> {
    return this.executeAction(
      id,
      AuthStateActions.LOGOUT,
      async () => {
        await this.authApi.logout();
        this.authStore.setUser(null);
      },
    );
  }

  async checkAuth(): Promise<UserEntity | null> {
    return this.executeAction(
      this.unAuthUserIdentifier,
      AuthStateActions.CHECK_AUTH,
      async () => {
        const userResponse: AxiosResponse<UserDTO> = await this.authApi.checkAuth();
        const userDTO: UserDTO = userResponse.data;
        if (userDTO) {
          const userEntity: UserEntity = new UserEntity(userDTO);
          this.authStore.setUser(userEntity);
          return userEntity;
        }
        this.authStore.setUser(null);
        return null;
      },
    );
  }
  
  
  get isLoginLoading() {
    return this.entityActionStateStore.isLoading(this.unAuthUserIdentifier, AuthStateActions.LOGIN);
  }
  isLogoutLoading(id: number) {
    return this.entityActionStateStore.isLoading(id, AuthStateActions.LOGOUT);
  }
}
