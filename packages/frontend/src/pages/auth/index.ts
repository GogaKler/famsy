import { signInRouter } from '@pages/auth/sign-in';


export const authRouter = [
  {
    path: '/auth',
    name: 'auth-root',
    redirect: { name: 'auth-sign-in' },
    children: [...signInRouter],
  },
];