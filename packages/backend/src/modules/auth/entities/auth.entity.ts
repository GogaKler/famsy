import { ApiProperty } from '@nestjs/swagger';
import { UserEntity } from '@/modules/users/entities/user.entity';
import { Auth } from '@prisma/client';
import { Exclude } from 'class-transformer';

export class AuthEntity implements Auth {
  @ApiProperty()
  id: number;

  @Exclude()
  accessToken: string | null;

  @Exclude()
  refreshToken: string | null;

  @ApiProperty()
  createdAt: Date;

  @ApiProperty()
  updatedAt: Date;

  @ApiProperty()
  user?: UserEntity;

  @ApiProperty()
  userId: number;

  constructor(partial: Partial<AuthEntity>) {
    Object.assign(this, partial);
  }
}
