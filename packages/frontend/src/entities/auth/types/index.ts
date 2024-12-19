export type TLoginDto = {
  login: string,
  password: string
};

export type TRegisterDto = {
  email: string,
  password: string
};

export type TCheckAuthResponse = {
  isAuthenticated: boolean;
};