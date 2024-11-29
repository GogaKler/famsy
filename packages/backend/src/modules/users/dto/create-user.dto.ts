import { ApiProperty } from '@nestjs/swagger';
import { IsEmail, IsNotEmpty, IsString, MaxLength, MinLength } from 'class-validator';

export class CreateUserDto {
  @IsString()
  @IsNotEmpty()
  @ApiProperty()
  firstName: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty()
  lastName: string;

  @IsString()
  @ApiProperty()
  patronymic?: string;

  @IsString()
  @ApiProperty()
  status?: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty()
  userName: string;

  @IsEmail()
  @IsNotEmpty()
  @ApiProperty()
  email: string;

  @MinLength(6)
  @MaxLength(100)
  @ApiProperty()
  password: string;
}
