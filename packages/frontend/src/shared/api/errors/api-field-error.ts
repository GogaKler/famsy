import type { ApiErrorResponse } from '@shared/api';

export type ApiFieldErrorsResponse = Record<string, string>;

export class ApiFieldError extends Error {
  private readonly errorFieldErrors: ApiFieldErrorsResponse;

  constructor(errorFieldErrors: ApiFieldErrorsResponse) {
    super('Ошибка валидации полей');
    this.name = ApiFieldError.name;
    this.errorFieldErrors = errorFieldErrors;
  }

  get fields(): ApiFieldErrorsResponse {
    return this.errorFieldErrors;
  }

  static isFieldErrors(
    error: ApiFieldErrorsResponse | ApiErrorResponse | undefined,
  ): error is ApiFieldErrorsResponse {
    if (!error) return false;
    return typeof Object.values(error)[0] === 'string' 
      && !('error' in error) 
      && !('message' in error);
  }
}
