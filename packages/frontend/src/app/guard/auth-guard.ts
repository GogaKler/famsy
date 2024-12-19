import { useAuthStore } from '@entities/auth';
import type { Router } from 'vue-router';


export function useAuthGuard(router: Router) {
  router.beforeEach(async (to, from) => {
    const authStore = useAuthStore();

    if (authStore.isAuth && to.name === 'auth-sign-in') {
      return { name: 'main-root' };
    }

    if (!authStore.isAuth && to.name !== 'auth-sign-in') {
      try {
        await authStore.checkAuth();
      } catch (e) {
        return { name: 'auth-sign-in' };
      }
    }
  });
}