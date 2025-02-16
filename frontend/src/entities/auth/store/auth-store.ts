import { defineStore } from 'pinia';
import { type UserEntity } from '@entities/user';

interface AuthState {
  user: UserEntity | null;
}

const unAuthUserId = 0;

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    user: null,
  }),

  getters: {
    isAuth: (state) => !!state.user,
    currentUser: (state) => state.user,
    currentUserId: (state) => state.user?.id || unAuthUserId,
  },

  actions: {
    setUser(user: UserEntity | null) {
      this.user = user;
    },

    clear() {
      this.user = null;
    },
  },
});
