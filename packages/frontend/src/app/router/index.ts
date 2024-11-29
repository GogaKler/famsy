import { createRouter, createWebHistory, type Router } from 'vue-router';
import { authRouter } from '@pages/auth';
import { clientRouter } from '@pages/client';


const router: Router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'root',
      redirect: { name: 'client-root' },
      children: [...clientRouter, ...authRouter],
    },
  ],
});

export default router;
