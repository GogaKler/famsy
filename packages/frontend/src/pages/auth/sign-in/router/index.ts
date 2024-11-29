const SignInPage = () => import('../UI/SignInPage.vue');

export const signInRouter = [
  {
    path: 'sign-in',
    name: 'auth-sign-in',
    component: SignInPage,
  },
];