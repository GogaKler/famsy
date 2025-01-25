import axios, { AxiosError, type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import { injectable } from 'tsyringe';
import { ApiError, type ApiErrorResponse, ApiFieldError, type ApiFieldErrorsResponse } from '@shared/api';

interface IHttpClient {
  get<T>(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<T>>;
  post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse<T>>;
  put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse<T>>;
  delete<T>(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<T>>;
}

@injectable()
export class HttpClient implements IHttpClient {
  private static instance: HttpClient;
  private readonly axiosInstance: AxiosInstance;

  constructor(
    private readonly backendUrl: string,
  ) {
    this.axiosInstance = axios.create({
      baseURL: this.backendUrl,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Credentials': true,
      },
      withCredentials: true,
    });
    this.setupInterceptors();
  }

  public static getInstance(baseURL: string): HttpClient {
    if (!HttpClient.instance) {
      HttpClient.instance = new HttpClient(baseURL);
    }
    return HttpClient.instance;
  }

  private setupInterceptors() {
    this.axiosInstance.interceptors.response.use(
      (response: AxiosResponse) => response,
      (error: AxiosError<ApiErrorResponse | ApiFieldErrorsResponse>) => {
        throw this.handleError(error);
      },
    );
  }

  private handleError(error: AxiosError<ApiErrorResponse | ApiFieldErrorsResponse>) {
    const { response } = error;

    if (!response) {
      return new Error('Ошибка соединения с сервером');
    }

    if (ApiFieldError.isFieldErrors(response.data)) {
      return new ApiFieldError(response.data);
    }

    switch (response.status) {
      case 401:
        localStorage.removeItem('isAuth');
        window.location.href = '/auth/login';
        return new ApiError(response.data);
      case 404:
        // window.location.href = '/404';
        return new ApiError(response.data);
      default:
        return new ApiError(response.data);
    }
  }

  async get<T>(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<T>> {
    return this.axiosInstance.get(url, config);
  }

  async post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse<T>> {
    return this.axiosInstance.post(url, data, config);
  }

  async put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse<T>> {
    return this.axiosInstance.put(url, data, config);
  }

  async delete<T>(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<T>> {
    return this.axiosInstance.delete(url, config);
  }
}

