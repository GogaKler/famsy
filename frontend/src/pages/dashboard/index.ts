import { HeaderTabItem, HeaderTabList } from '@entities/header-tabs';
import type { RouteRecordRaw } from 'vue-router';

export function getDashboardRouter(): RouteRecordRaw[] {
  return [
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('./ui/DashboardPage.vue'),
      meta: {
        headerTabs: new HeaderTabList([
          new HeaderTabItem({
            routeName: 'dashboard',
            name: 'Обзор',
            icon: 'table-columns',
          }),
          new HeaderTabItem({
            routeName: 'profile',
            name: 'Профиль',
            icon: 'user',
          }),
        ]),
      },
    },
  ];
}