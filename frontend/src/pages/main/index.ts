import type { RouteRecordRaw } from 'vue-router';
const MainPage = () => import('./UI/MainPage.vue');

export function getMainRouter(): RouteRecordRaw[] {
  return [
    {
      path: 'main',
      name: 'main-root',
      component: MainPage,
    },
  ];
}