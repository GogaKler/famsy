import { HeaderTabItem, HeaderTabList } from '@entities/header-tabs';
import type { RouteRecordRaw } from 'vue-router';

export function getProfileRouter(): RouteRecordRaw[] {
  return [
    {
      path: '/profile',
      name: 'profile',
      redirect: { name: 'profile-detail' },
      meta: {
        title: 'Profile',
        headerTabs: new HeaderTabList([
          new HeaderTabItem({
            routeName: 'dashboard',
            name: 'Обзор',
            icon: 'table-columns',
          }),
          new HeaderTabItem({
            routeName: 'profile-detail',
            name: 'Профиль',
            icon: 'user',
          }),
          new HeaderTabItem({
            routeName: 'profile-edit',
            name: 'Редактировать профиль',
            icon: 'pencil',
          }),
        ]),
      },
      children: [
        {
          path: '',
          name: 'profile-detail',
          component: () => import('./ui/ProfileDetailPage.vue'),
        },
        {
          path: 'edit',
          name: 'profile-edit',
          component: () => import('./ui/ProfileEditPage.vue'),
        },
      ],
    },
  ];
}