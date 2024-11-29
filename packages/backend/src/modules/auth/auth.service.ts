import { Injectable, NotFoundException, UnauthorizedException } from '@nestjs/common';
import { UsersService } from '@/modules/users/users.service';
import { PrismaService } from 'nestjs-prisma';
import { JwtService } from '@nestjs/jwt';
import { AuthEntity } from '@/modules/auth/entities/auth.entity';
import { ConfigService } from '@nestjs/config';
import * as bcrypt from 'bcrypt';
import { JwtSignOptions } from '@nestjs/jwt/dist/interfaces';
import { LoginDto } from '@/modules/auth/dto/login.dto';
import { IAuthToken, IAuthTokenWithUser } from '@/modules/auth/types/interfaces';
import { RegisterDto } from '@/modules/auth/dto/register.dto';

@Injectable()
export class AuthService {
  constructor(
    private configService: ConfigService,
    private userService: UsersService,
    private prisma: PrismaService,
    private jwtService: JwtService,
  ) {}

  public async login(loginDto: LoginDto): Promise<IAuthTokenWithUser> | never {
    const { login, password } = loginDto;
    const userByEmail = await this.userService.findByEmail(login);
    const userByUserName = await this.userService.findByUserName(login);

    const user = userByEmail || userByUserName;
    if (!user) {
      throw new NotFoundException(`Неверный логин или пароль`);
    }

    const isPasswordValid = await this.compareHash(password, user.password);
    if (!isPasswordValid) {
      throw new UnauthorizedException('Неверный логин или пароль');
    }

    let accessToken: string;
    let refreshToken: string;
    const auth = this.prisma.auth.findUnique({ where: { userId: user.id } });
    if (!auth) {
      accessToken = this.createAccessToken(user.id);
      refreshToken = this.createRefreshToken(user.id);
      await this.create(user.id, { accessToken, refreshToken });
    } else {
      accessToken = await this.updateAccessToken(user.id);
      refreshToken = await this.updateRefreshToken(user.id);
    }

    return {
      accessToken,
      refreshToken,
      user,
    };
  }

  public async register(registerDto: RegisterDto): Promise<IAuthTokenWithUser> {
    const createdUser = await this.userService.create(registerDto);
    const accessToken = this.createAccessToken(createdUser.id);
    const refreshToken = this.createRefreshToken(createdUser.id);
    await this.create(createdUser.id, { accessToken, refreshToken });
    return {
      accessToken,
      refreshToken,
      user: createdUser,
    };
  }

  public async logOut(userId: number): Promise<IAuthToken> {
    const { accessToken, refreshToken } = this.generateCookieNullableTokens();
    await this.update(userId, { refreshToken: null, accessToken: null });
    return {
      accessToken,
      refreshToken,
    };
  }

  public generateCookieAccessToken(accessToken: string): string {
    return `Authentication=${accessToken}; Path=/; Max-Age=${this.configService.get<string>('JWT_ACCESS_TOKEN_EXPIRATION_TIME')}; httpOnly`;
  }

  public generateCookieRefreshToken(refreshToken: string): string {
    return `Refresh=${refreshToken}; Path=/; Max-Age=${this.configService.get<string>('JWT_REFRESH_TOKEN_EXPIRATION_TIME')}; httpOnly`;
  }

  public generateCookieNullableTokens(): IAuthToken {
    const accessToken = 'Authentication=; Path=/; Max-Age=0';
    const refreshToken = 'Refresh=; Path=/; Max-Age=0';
    return {
      accessToken,
      refreshToken,
    };
  }

  public async findOne(userId: number): Promise<AuthEntity> {
    return this.prisma.auth.findUnique({ where: { userId } });
  }

  private async create(userId: number, data: IAuthToken): Promise<AuthEntity> {
    return this.prisma.auth.create({
      data: {
        accessToken: await this.hash(data.accessToken),
        refreshToken: await this.hash(data.refreshToken),
        userId: userId,
      },
    });
  }

  private async update(userId: number, data: IAuthToken): Promise<AuthEntity> {
    return this.prisma.auth.update({
      where: { userId },
      data,
    });
  }

  private createAccessToken(userId: number): string {
    return this.generateToken(userId, {
      secret: this.configService.get<string>('JWT_ACCESS_TOKEN_SECRET'),
      expiresIn: `${this.configService.get<string>('JWT_ACCESS_TOKEN_EXPIRATION_TIME')}`,
    });
  }

  public async updateAccessToken(userId: number): Promise<string> {
    const accessToken = this.createAccessToken(userId);
    const accessTokenHashed = await this.hash(accessToken);
    await this.update(userId, { accessToken: accessTokenHashed });
    return accessToken;
  }

  private createRefreshToken(userId: number): string {
    return this.generateToken(userId, {
      secret: this.configService.get<string>('JWT_REFRESH_TOKEN_SECRET'),
      expiresIn: `${this.configService.get<string>('JWT_REFRESH_TOKEN_EXPIRATION_TIME')}`,
    });
  }

  public async updateRefreshToken(userId: number): Promise<string> {
    const refreshToken = this.createRefreshToken(userId);
    const refreshTokenHashed = await this.hash(refreshToken);
    await this.update(userId, { refreshToken: refreshTokenHashed });
    return refreshToken;
  }

  private generateToken(userId: number, options: JwtSignOptions): string {
    return this.jwtService.sign({ userId }, options);
  }

  public async hash(element: string): Promise<string> {
    return await bcrypt.hash(element, 10);
  }

  public async compareHash(enteredValue: string, databaseValue: string): Promise<boolean> {
    return await bcrypt.compare(enteredValue, databaseValue);
  }
}
