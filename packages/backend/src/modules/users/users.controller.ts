import { Controller, Get, Post, Body, Patch, Param, Delete, ParseIntPipe } from '@nestjs/common';
import { UsersService } from './users.service';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { ApiCreatedResponse, ApiOkResponse, ApiTags } from '@nestjs/swagger';
import { UserEntity } from '@/modules/users/entities/user.entity';

@ApiTags('users')
@Controller('users')
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  @ApiCreatedResponse({ type: UserEntity })
  @Post()
  async create(@Body() createUserDto: CreateUserDto) {
    const response = await this.usersService.create(createUserDto);
    return new UserEntity(response);
  }

  @ApiOkResponse({ type: UserEntity, isArray: true })
  @Get()
  async findAll() {
    const users = await this.usersService.findAll();
    return users.map((user) => new UserEntity(user));
  }

  @ApiOkResponse({ type: UserEntity })
  @Get(':id')
  async findOne(@Param('id', ParseIntPipe) id: number) {
    const user = await this.usersService.findOne(id);
    return new UserEntity(user);
  }

  @ApiOkResponse({ type: UserEntity })
  @Patch(':id')
  async update(@Param('id', ParseIntPipe) id: number, @Body() updateUserDto: UpdateUserDto) {
    const updatedUser = await this.usersService.update(id, updateUserDto);
    return new UserEntity(updatedUser);
  }

  @ApiOkResponse({ type: UserEntity })
  @Delete(':id')
  async remove(@Param('id', ParseIntPipe) id: number) {
    const deletedUser = await this.usersService.remove(id);
    return new UserEntity(deletedUser);
  }
}
