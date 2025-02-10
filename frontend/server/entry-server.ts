import { renderToString } from 'vue/server-renderer';
import { createFamsyApp } from '../src/main';

export async function renderPage(url: string): Promise<string> {
  const { app, router } = await createFamsyApp();
  router.push(url);
  await router.isReady();
  return renderToString(app);
}
