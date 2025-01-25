import { HttpClient } from './http-client';
import type { BaseQueryParams } from './types/request-types';
import type { AxiosResponse } from 'axios';

export abstract class BaseApi {
  private readonly httpClient: HttpClient;

  constructor(
    protected readonly basePath: string,
  ) {
    this.httpClient = HttpClient.getInstance(import.meta.env.VITE_BACKEND_URL || '');
  }

  protected getUrl(path: string = ''): string {
    return `${this.basePath}${path}`;
  }

  protected async get<T>(path: string = '', params?: BaseQueryParams): Promise<AxiosResponse<T>> {
    return this.httpClient.get<T>(this.getUrl(path), { params });
  }

  protected async post<T>(path: string = '', data?: unknown): Promise<AxiosResponse<T>> {
    return this.httpClient.post<T>(this.getUrl(path), data);
  }

  protected async put<T>(path: string = '', data?: unknown): Promise<AxiosResponse<T>> {
    return this.httpClient.put<T>(this.getUrl(path), data);
  }

  protected async delete<T>(path: string = ''): Promise<AxiosResponse<T>> {
    return this.httpClient.delete<T>(this.getUrl(path));
  }
}