import { ExtractJwt, Strategy } from 'passport-jwt';
import { PassportStrategy } from '@nestjs/passport';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { Request } from 'express';
import { AuthService } from '@/modules/auth/auth.service';
import { UsersService } from '@/modules/users/users.service';

@Injectable()
export class JwtRefreshTokenStrategy extends PassportStrategy(Strategy, 'jwt-refresh-token') {
  constructor(
    private readonly configService: ConfigService,
    private readonly usersService: UsersService,
    private readonly authService: AuthService,
  ) {
    super({
      jwtFromRequest: ExtractJwt.fromExtractors([
        (request: Request) => {
          return request?.cookies?.Refresh;
        },
      ]),
      secretOrKey: configService.get<string>('JWT_REFRESH_TOKEN_SECRET'),
      passReqToCallback: true,
    });
  }

  async validate(request: Request, payload: { userId: number }) {
    const auth = await this.authService.findOne(payload.userId);
    const refreshToken = request.cookies?.Refresh;
    const refreshTokenDatabase = auth.refreshToken;
    const user = await this.usersService.findOne(payload.userId);
    const isValid = this.authService.compareHash(refreshToken, refreshTokenDatabase);

    if (!isValid) {
      const { accessToken, refreshToken } = await this.authService.logOut(payload.userId);
      request.res.setHeader('Set-Cookie', [accessToken, refreshToken]);
      return user;
    }

    return user;
  }
}
