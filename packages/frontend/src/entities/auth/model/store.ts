import { defineStore } from 'pinia';
import { AuthAPI } from '../api';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isAuth: localStorage.getItem('isAuth') === 'true',
  }),
  actions: {
    async logOut() {
      await AuthAPI.logout();
      this.setAuth(false);
    },
    async checkAuth() {
      try {
        const response = await AuthAPI.checkAuth();
        this.setAuth(response.isAuthenticated);
      } catch (error) {
        this.setAuth(false);
      }
    },
    setAuth(isAuth: boolean) {
      this.isAuth = isAuth;
      localStorage.setItem('isAuth', isAuth.toString());
    },
  },
});