import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import type { Router } from 'vue-router';
import router from '@app/router';

interface IHttpClient {
  get<T>(url: string, config?: AxiosRequestConfig): Promise<T>;
  post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>;
  put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>;
  delete<T>(url: string, config?: AxiosRequestConfig): Promise<T>;
}

class HttpClient implements IHttpClient {
  private readonly client: AxiosInstance;

  constructor(
    private readonly baseURL: string,
    private readonly router: Router,
  ) {
    this.router = router;
    this.client = axios.create({
      baseURL,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Credentials': true,
      },
      withCredentials: true,
    });

    this.client.interceptors.response.use(
      (response: AxiosResponse) => response,
      this.handleError,
    );
  }

  private async handleError(error: any): Promise<any> {
    const { response, config } = error;
    if (!response) {
      console.error('**Network/Server error');
      return Promise.reject(error);
    }

    if (response.status === 401 && !config._retry) {
      config._retry = true;
      try {
        await this.post('/api/auth/refresh');
        return this.client(config);
      } catch (e) {
        console.error(e);
        await this.router.push({ name: 'auth-sign-in' });
      }
    }

    if (response.status === 404) {
      await this.router.push('/404');
    } else {
      console.error(response.statusText);
    }

    return Promise.reject(error);
  }

  async get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    const response: AxiosResponse<T> = await this.client.get(url, config);
    return response.data;
  }

  async post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    const response: AxiosResponse<T> = await this.client.post(url, data, config);
    return response.data;
  }

  async put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    const response: AxiosResponse<T> = await this.client.put(url, data, config);
    return response.data;
  }

  async delete<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    const response: AxiosResponse<T> = await this.client.delete(url, config);
    return response.data;
  }
}

export const httpClient = new HttpClient(import.meta.env.VITE_BACKEND_URL, router);