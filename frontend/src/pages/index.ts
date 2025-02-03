import { createRouter, createWebHistory, type Router } from 'vue-router';
import { getAuthRouter } from '@pages/auth';
import { useAuthGuard } from '@app/guard';
import { getDashboardRouter } from '@pages/dashboard';
import { getProfileRouter } from '@pages/profile';

export const router: Router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...getAuthRouter(),
    {
      path: '/',
      name: 'root',
      redirect: { name: 'dashboard' },
      component: () => import('@app/layouts/RootLayout.vue'),
      children: [...getDashboardRouter(), ...getProfileRouter()],
    },
  ],
});

useAuthGuard(router);