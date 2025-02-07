import 'reflect-metadata';
import { router } from '@pages';
import { App } from '@app/ui';
import { AuthService } from '@entities/auth';
import { defaultConfig, plugin } from '@formkit/vue';
import { FsPluginFontawesome, FsPrimeVuePlugin } from '@shared/plugins';
import '@shared/ui/styles/index.scss';
import { devtools } from '@vue/devtools';
import { createPinia } from 'pinia';
import { container } from 'tsyringe';
import { createApp } from 'vue';
import formkitConfig from '../formkit.config';


async function main() {
  const app = createApp(App);
  app.use(FsPluginFontawesome);
  app.use(createPinia());
  app.use(plugin, defaultConfig(formkitConfig));
  app.use(FsPrimeVuePlugin);

  try {
    const authService: AuthService = container.resolve(AuthService);
    await authService.checkAuth();
  } finally {
    app.use(router);
    app.mount('#app');
  }
}

await main();

if (import.meta.env.NODE_ENV === 'development') {
  devtools.connect();
}