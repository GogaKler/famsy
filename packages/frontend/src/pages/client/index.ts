import { clientMainRouter } from '@pages/client/main';


export const clientRouter = [
  {
    path: '/client',
    name: 'client-root',
    redirect: { name: 'client-main' },
    children: [...clientMainRouter],
  },
]; 