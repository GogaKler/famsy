import { fileURLToPath } from 'node:url';
import { mergeConfig, defineConfig, configDefaults, ConfigEnv } from 'vitest/config';
import viteConfig from './vite.config';
import { UserConfig } from 'vite';


export default defineConfig((env: ConfigEnv): UserConfig => mergeConfig(viteConfig(env),
  defineConfig({
    test: {
      environment: 'jsdom',
      exclude: [...configDefaults.exclude, 'e2e/*'],
      root: fileURLToPath(new URL('./', import.meta.url)),
    },
  }),
));