import { Body, Controller, Get, Post, Req, UseGuards } from '@nestjs/common';
import { AuthService } from './auth.service';
import { LoginDto } from '@/modules/auth/dto/login.dto';
import { ApiOkResponse, ApiTags } from '@nestjs/swagger';
import { UserEntity } from '@/modules/users/entities/user.entity';
import { Public } from '@/core/decorators/pubilc';
import { RegisterDto } from '@/modules/auth/dto/register.dto';
import { Request } from 'express';
import { JwtRefreshGuard } from '@/modules/auth/guards/jwt-auth-refresh.guard';
import { UsersService } from '@/modules/users/users.service';

@ApiTags('auth')
@Controller('auth')
export class AuthController {
  constructor(
    private readonly authService: AuthService,
    private readonly userService: UsersService,
  ) {}

  @ApiOkResponse({ type: UserEntity })
  @Public()
  @Post('login')
  async login(@Req() request: Request, @Body() loginDto: LoginDto): Promise<UserEntity> {
    const response = await this.authService.login(loginDto);
    const accessTokenCookie = this.authService.generateCookieAccessToken(response.accessToken);
    const refreshTokenCookie = this.authService.generateCookieRefreshToken(response.refreshToken);
    request.res.setHeader('Set-Cookie', [accessTokenCookie, refreshTokenCookie]);
    return new UserEntity(response.user);
  }

  @ApiOkResponse({ type: UserEntity })
  @Public()
  @Post('register')
  async register(@Req() request: Request, @Body() registerDto: RegisterDto): Promise<UserEntity> {
    const response = await this.authService.register(registerDto);
    const accessTokenCookie = this.authService.generateCookieAccessToken(response.accessToken);
    const refreshTokenCookie = this.authService.generateCookieRefreshToken(response.refreshToken);
    request.res.setHeader('Set-Cookie', [accessTokenCookie, refreshTokenCookie]);
    return new UserEntity(response.user);
  }

  @Post('logOut')
  async logOut(@Req() request): Promise<void> {
    const userId = request.user.id;
    const { accessToken, refreshToken } = await this.authService.logOut(userId);
    request.res.setHeader('Set-Cookie', [accessToken, refreshToken]);
  }

  @ApiOkResponse({ type: Boolean })
  @Get('checkAuth')
  async checkAuth(): Promise<{ isAuthenticated: boolean }> {
    return { isAuthenticated: true };
  }

  @UseGuards(JwtRefreshGuard)
  @Public()
  @Post('refresh')
  async refresh(@Req() request): Promise<void> {
    const accessToken = await this.authService.updateAccessToken(request.user.id);
    const accessTokenCookie = this.authService.generateCookieAccessToken(accessToken);
    request.res.setHeader('Set-Cookie', accessTokenCookie);
  }

  @ApiOkResponse({ type: UserEntity })
  @Get('me')
  async me(@Req() req) {
    const user = await this.userService.findOne(req.user.id);
    return new UserEntity(user);
  }
}
