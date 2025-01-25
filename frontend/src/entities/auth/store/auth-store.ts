import { defineStore } from 'pinia';
import { type UserEntity } from '@entities/user';

interface AuthState {
  user: UserEntity | null;
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    user: null,
  }),

  getters: {
    isAuth: (state) => !!state.user,
    currentUser: (state) => state.user,
  },

  actions: {
    setUser(user: UserEntity | null) {
      console.log('setUser', user);
      this.user = user;
    },

    clear() {
      this.user = null;
    },
  },
});
