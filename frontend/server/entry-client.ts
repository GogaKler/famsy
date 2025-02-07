import { createFamsyApp } from '../src/main';
import { devtools } from '@vue/devtools';

const { app, router } = await createFamsyApp();

router.isReady().then(() => {
  app.mount('#app');
});

if (import.meta.env.NODE_ENV === 'development') {
  devtools.connect();
}