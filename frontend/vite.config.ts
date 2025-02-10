import { fileURLToPath, URL } from 'node:url';
import { defineConfig, loadEnv, UserConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

const setPath = (path: string): string => fileURLToPath(new URL(path, import.meta.url));

export default defineConfig((config): UserConfig => {
  const env = { ...process.env, ...loadEnv(config.mode, process.cwd(), '') };
  const isDev = env.NODE_ENV === 'development';
  const port = Number(env.VITE_PORT) || 8069;

  return {
    server: {
      port: port,
      host: '0.0.0.0',
    },
    plugins: [vue()],
    resolve: {
      alias: [
        { find: '@', replacement: setPath('./src') },
        { find: '@app', replacement: setPath('./src/app') },
        { find: '@pages', replacement: setPath('./src/pages') },
        { find: '@features', replacement: setPath('./src/features') },
        { find: '@entities', replacement: setPath('./src/entities') },
        { find: '@widgets', replacement: setPath('./src/widgets') },
        { find: '@shared', replacement: setPath('./src/shared') },
      ],
    },
    css: {
      devSourcemap: isDev,
      preprocessorOptions: {
        scss: {
          quietDeps: true,
          additionalData: `
            @use "@/shared/ui/mixins" as *;
          `,
        },
      },  
    },
  };
});
