const LoginPage = () => import('./login/LoginPage.vue');
const RegisterPage = () => import('./register/RegisterPage.vue');
const AuthLayout = () => import('@app/layouts/AuthLayout.vue');

export const getAuthRouter = () => [
  {
    path: '/auth',
    name: 'auth-root',
    redirect: { name: 'auth-login' },
    component: AuthLayout,
    children: [
      {
        path: 'login',
        name: 'auth-login',
        component: LoginPage,
      },
      {
        path: 'register',
        name: 'auth-register',
        component: RegisterPage,
      },
    ],
  },
];