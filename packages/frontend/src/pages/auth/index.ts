const SignInPage = () => import('./sign-in/SignInPage.vue');


export const authRouter = [
  {
    path: '/auth',
    name: 'auth-root',
    redirect: { name: 'auth-sign-in' },
    children: [
      {
        path: 'sign-in',
        name: 'auth-sign-in',
        component: SignInPage,
      },
    ],
  },
];