export type ApiErrorResponse = {
  error: string;
  message: string;
};

export class ApiError extends Error {
  private readonly errorResponse: ApiErrorResponse;

  constructor(errorResponse: ApiErrorResponse) {
    super(errorResponse.message);
    this.name = ApiError.name;
    this.errorResponse = errorResponse;
  }

  get error(): ApiErrorResponse {
    return this.errorResponse;
  }

  get message(): string {
    return this.errorResponse.message;
  }
}
