import { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import type { Router } from 'vue-router';
import router from '@app/router';
import { useAuthStore } from '@entities/auth';
import { axiosInstance } from '@shared/api/axios-instance';

interface IHttpClient {
  get<T>(url: string, config?: AxiosRequestConfig): Promise<T>;
  post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>;
  put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>;
  delete<T>(url: string, config?: AxiosRequestConfig): Promise<T>;
}

class HttpClient implements IHttpClient {
  private isRefreshing = false;

  constructor(
    private readonly axiosInstance: AxiosInstance,
    private readonly router: Router,
  ) {
    this.router = router;

    this.axiosInstance.interceptors.response.use(
      (response: AxiosResponse) => response,
      this.handleError.bind(this),
    );
  }

  private async handleError(error: any): Promise<any> {
    const { response, config } = error;
    const authStore = useAuthStore();

    if (!response) {
      console.error('**Network/Server error');
      return Promise.reject(error);
    }

    if (response.status === 401 && !this.isRefreshing) {
      this.isRefreshing = true;
      try {
        await this.post('auth/refresh');
        return this.axiosInstance(config);
      } catch (e) {
        console.error(e);
        authStore.setAuth(false);
        await this.router.push({ name: 'auth-sign-in' });
      } finally {
        this.isRefreshing = false;
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
    const response: AxiosResponse<T> = await this.axiosInstance.get(url, config);
    return response.data;
  }

  async post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    const response: AxiosResponse<T> = await this.axiosInstance.post(url, data, config);
    return response.data;
  }

  async put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    const response: AxiosResponse<T> = await this.axiosInstance.put(url, data, config);
    return response.data;
  }

  async delete<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    const response: AxiosResponse<T> = await this.axiosInstance.delete(url, config);
    return response.data;
  }
}

export const httpClient = new HttpClient(axiosInstance, router);