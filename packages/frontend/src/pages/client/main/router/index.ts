
const clientMainPage = () => import('../UI/ClientMainPage.vue');

export const clientMainRouter = [
  {
    path: 'main',
    name: 'client-main',
    component: clientMainPage,
  },
];