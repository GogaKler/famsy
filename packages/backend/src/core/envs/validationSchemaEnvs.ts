import * as Joi from 'joi';

export const validationSchemaEnvs: Joi.ObjectSchema<any> = Joi.object({
  NODE_ENV: Joi.string().valid('development', 'production', 'test').default('development'),
  APP_PORT: Joi.number().port().default(3000),
  APP_HOST: Joi.string().default('localhost'),
  REDIS_PORT: Joi.number().port().default(3069),
  REDIS_HOST: Joi.string().default('redis'),
  POSTGRES_PORT: Joi.number().port().default(5432),
  POSTGRES_HOST: Joi.string().default('postgres'),
  POSTGRES_DB: Joi.string().default('FAMSY'),
  POSTGRES_USER: Joi.string().default('postgres'),
  POSTGRES_PASSWORD: Joi.string().default('postgres'),
  PG_ADMIN_PORT: Joi.number().port().default(5050),
  PG_ADMIN_EMAIL: Joi.string().default('admin@pgadmin.com'),
  PG_ADMIN_PASSWORD: Joi.string().default('root'),
  DATABASE_URL: Joi.string().required(),
  JWT_ACCESS_TOKEN_SECRET: Joi.string().required(),
  JWT_ACCESS_TOKEN_EXPIRATION_TIME: Joi.string().default('10m'),
  JWT_REFRESH_TOKEN_SECRET: Joi.string().required(),
  JWT_REFRESH_TOKEN_EXPIRATION_TIME: Joi.string().default('7d'),
});
