const LoginPage = () => import('./login/LoginPage.vue');


export const getAuthRouter = () => [
  {
    path: '/auth',
    name: 'auth-root',
    redirect: { name: 'auth-login' },
    children: [
      {
        path: 'login',
        name: 'auth-login',
        component: LoginPage,
      },
    ],
  },
];