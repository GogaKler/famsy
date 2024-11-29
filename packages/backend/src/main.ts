import { HttpAdapterHost, NestFactory, Reflector } from '@nestjs/core';
import { AppModule } from './app.module';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import {
  BadRequestException,
  ClassSerializerInterceptor,
  INestApplication,
  ValidationError,
  ValidationPipe,
} from '@nestjs/common';
import { PrismaClientExceptionFilter } from 'nestjs-prisma';
import * as cookieParser from 'cookie-parser';

const GLOBAL_PREFIX = 'api';

async function bootstrap() {
  const port = Number(process.env.APP_PORT) || 3000;
  const app: INestApplication = await NestFactory.create(AppModule);

  app.useGlobalInterceptors(new ClassSerializerInterceptor(app.get(Reflector)));
  app.useGlobalPipes(
    new ValidationPipe({
      exceptionFactory: (validationErrors: ValidationError[] = []) => {
        return new BadRequestException(
          validationErrors.map((error) => ({
            field: error.property,
            error: Object.values(error.constraints).join(', '),
          })),
        );
      },
      whitelist: true,
    }),
  );

  app.setGlobalPrefix(GLOBAL_PREFIX);

  app.use(cookieParser());

  app.enableCors({
    credentials: true,
    origin: ['http://localhost:8069'],
  });

  await createPrismaClientException(app);
  await createSwagger(app);

  await app.listen(port);
}

async function createPrismaClientException(app: INestApplication): Promise<void> {
  const { httpAdapter } = app.get(HttpAdapterHost);
  app.useGlobalFilters(new PrismaClientExceptionFilter(httpAdapter));
}

async function createSwagger(app: INestApplication): Promise<void> {
  const version = '0.1';

  const options = new DocumentBuilder().setTitle('FAMSY').setDescription('FAMSY API').setVersion(version).build();

  const document = SwaggerModule.createDocument(app, options);
  SwaggerModule.setup(`${GLOBAL_PREFIX}/docs`, app, document);
}

bootstrap();
