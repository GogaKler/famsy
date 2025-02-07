import 'reflect-metadata';
import { router } from '@pages';
import { App } from '@app/ui';
import { AuthService } from '@entities/auth';
import { defaultConfig, plugin } from '@formkit/vue';
import { FsPluginFontawesome } from '@shared/plugins';
import '@shared/ui/styles/index.scss';
import { createPinia } from 'pinia';
import { container } from 'tsyringe';
import { createApp } from 'vue';
import formkitConfig from '../formkit.config';


export async function createFamsyApp() {
  const app = createApp(App);
  app.use(FsPluginFontawesome);
  app.use(createPinia());
  app.use(plugin, defaultConfig(formkitConfig));

  try {
    const authService: AuthService = container.resolve(AuthService);
    await authService.checkAuth();
  } finally {
    app.use(router);
  }

  return { app, router };
}