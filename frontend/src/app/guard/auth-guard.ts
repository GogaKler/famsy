import { AuthService } from '@entities/auth';
import { container } from 'tsyringe';
import type { Router } from 'vue-router';


export function useAuthGuard(router: Router) {
  router.beforeEach(async (to) => {
    const authService: AuthService = container.resolve(AuthService);
    
    if (authService.authStore.isAuth && (to.name === 'auth-login' || to.name === 'auth-register')) {
      return { name: 'dashboard' };
    }

    if (!authService.authStore.isAuth && (to.name !== 'auth-login' && to.name !== 'auth-register')) {
      await authService.checkAuth();
      if (!authService.authStore.isAuth) {
        return { name: 'auth-login' };
      }
      return to;
    }
  });
}


