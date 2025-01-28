import { createRouter, createWebHistory, type Router } from 'vue-router';
import { getAuthRouter } from '@pages/auth';
import { getMainRouter } from '@pages/main';
import { useAuthGuard } from '@app/guard';


const router: Router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'root',
      redirect: { name: 'main-root' },
      children: [...getMainRouter(), ...getAuthRouter()],
    },
  ],
});

useAuthGuard(router);

export default router;
