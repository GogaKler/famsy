import { type App, type Plugin } from 'vue';
import { definePreset } from '@primevue/themes';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import Button from 'primevue/button';
import Drawer from 'primevue/drawer';
import Avatar from 'primevue/avatar';

const FamsyPreset = definePreset(Aura, {
  semantic: {
    colorScheme: {
      dark: {
        primary: {
          50:  'var(--primary-950)',
          100: 'var(--primary-900)',
          200: 'var(--primary-800)',
          300: 'var(--primary-700)',
          400: 'var(--primary-600)',
          500: 'var(--primary-500)',
          600: 'var(--primary-400)',
          700: 'var(--primary-300)',
          800: 'var(--primary-200)',
          900: 'var(--primary-100)',
          950: 'var(--primary-50)',
        },
        secondary: {
          50: 'var(--secondary-950)',
          100: 'var(--secondary-900)',
          200: 'var(--secondary-800)',
          300: 'var(--secondary-700)',
          400: 'var(--secondary-600)',
          500: 'var(--secondary-500)',
          600: 'var(--secondary-400)',
          700: 'var(--secondary-300)',
          800: 'var(--secondary-200)',
          900: 'var(--secondary-100)',
          950: 'var(--secondary-50)',
        },
        surface: {
          50: 'var(--surface-950)',
          100: 'var(--surface-900)',
          200: 'var(--surface-800)',
          300: 'var(--surface-700)',
          400: 'var(--surface-600)',
          500: 'var(--surface-500)',
          600: 'var(--surface-400)',
          700: 'var(--surface-300)',
          800: 'var(--surface-200)',
          900: 'var(--surface-100)',
          950: 'var(--surface-50)',
        },
      },
      light: {
        primary: {
          50: 'var(--primary-50)',
          100: 'var(--primary-100)',
          200: 'var(--primary-200)',
          300: 'var(--primary-300)',
          400: 'var(--primary-400)',
          500: 'var(--primary-500)',
          600: 'var(--primary-600)',
          700: 'var(--primary-700)',
          800: 'var(--primary-800)',
          900: 'var(--primary-900)',
          950: 'var(--primary-950)',
        },
        secondary: {
          50: 'var(--secondary-50)',
          100: 'var(--secondary-100)',
          200: 'var(--secondary-200)',
          300: 'var(--secondary-300)',
          400: 'var(--secondary-400)',
          500: 'var(--secondary-500)',
          600: 'var(--secondary-600)',
          700: 'var(--secondary-700)',
          800: 'var(--secondary-800)',
          900: 'var(--secondary-900)',
          950: 'var(--secondary-950)',
        },
        surface: {
          50: 'var(--surface-50)',
          100: 'var(--surface-100)',
          200: 'var(--surface-200)',
          300: 'var(--surface-300)',
          400: 'var(--surface-400)',
          500: 'var(--surface-500)',
          600: 'var(--surface-600)',
          700: 'var(--surface-700)',
          800: 'var(--surface-800)',
          900: 'var(--surface-900)',
          950: 'var(--surface-950)',
        },
      },
    },
  },
});

export const FsPrimeVuePlugin: Plugin = {
  install(app: App): void {
    app.use(PrimeVue, {
      theme: {
        preset: FamsyPreset,
        options: {
          darkModeSelector: '.dark',
        },
      },
    });

    app.component('FamsyButton', Button);
    app.component('FamsyDrawer', Drawer);
    app.component('FamsyAvatar', Avatar);
  },
};
