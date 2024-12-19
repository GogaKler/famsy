import { createRouter, createWebHistory, type Router } from 'vue-router';
import { authRouter } from '@pages/auth';
import { getMainRouter } from '@pages/main';


const router: Router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'root',
      redirect: { name: 'main-root' },
      children: [...getMainRouter(), ...authRouter],
    },
  ],
});

export default router;
