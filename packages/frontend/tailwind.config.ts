import type { Config } from 'tailwindcss';

export default {
  darkMode: ['class', '[data-theme="dark"]'],
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
    './formkit.config.ts',
  ],
  theme: {
    extend: {
      colors: {
        // Actions
        'action-default': 'color-mix(in srgb, var(--action-default) calc(<alpha-value> * 100%), transparent)',
        'action-hover': 'color-mix(in srgb, var(--action-hover) calc(<alpha-value> * 100%), transparent)',
        'action-active': 'color-mix(in srgb, var(--action-active) calc(<alpha-value> * 100%), transparent)',
        'action-focus': 'color-mix(in srgb, var(--action-focus) calc(<alpha-value> * 100%), transparent)',
        'action-disabled': 'color-mix(in srgb, var(--action-disabled) calc(<alpha-value> * 100%), transparent)',

        // Neutral Actions
        'action-neutral': 'color-mix(in srgb, var(--action-neutral) calc(<alpha-value> * 100%), transparent)',
        'action-neutral-hover': 'color-mix(in srgb, var(--action-neutral-hover) calc(<alpha-value> * 100%), transparent)',
        'action-neutral-active': 'color-mix(in srgb, var(--action-neutral-active) calc(<alpha-value> * 100%), transparent)',
        'action-neutral-disabled': 'color-mix(in srgb, var(--action-neutral-disabled) calc(<alpha-value> * 100%), transparent)',

        'action-secondary': 'color-mix(in srgb, var(--action-secondary) calc(<alpha-value> * 100%), transparent)',
        'action-secondary-hover': 'color-mix(in srgb, var(--action-secondary-hover) calc(<alpha-value> * 100%), transparent)',
        'action-secondary-active': 'color-mix(in srgb, var(--action-secondary-active) calc(<alpha-value> * 100%), transparent)',
        'action-secondary-disabled': 'color-mix(in srgb, var(--action-secondary-disabled) calc(<alpha-value> * 100%), transparent)',

        // Surfaces
        'surface-primary': 'color-mix(in srgb, var(--surface-primary) calc(<alpha-value> * 100%), transparent)',
        'surface-secondary': 'color-mix(in srgb, var(--surface-secondary) calc(<alpha-value> * 100%), transparent)',
        'surface-tertiary': 'color-mix(in srgb, var(--surface-tertiary) calc(<alpha-value> * 100%), transparent)',
        'surface-elevated': 'color-mix(in srgb, var(--surface-elevated) calc(<alpha-value> * 100%), transparent)',
        'surface-overlay': 'color-mix(in srgb, var(--surface-overlay) calc(<alpha-value> * 100%), transparent)',
        'surface-inverse': 'color-mix(in srgb, var(--surface-inverse) calc(<alpha-value> * 100%), transparent)',

        // Text
        'text-primary': 'color-mix(in srgb, var(--text-primary) calc(<alpha-value> * 100%), transparent)',
        'text-secondary': 'color-mix(in srgb, var(--text-secondary) calc(<alpha-value> * 100%), transparent)',
        'text-tertiary': 'color-mix(in srgb, var(--text-tertiary) calc(<alpha-value> * 100%), transparent)',
        'text-disabled': 'color-mix(in srgb, var(--text-disabled) calc(<alpha-value> * 100%), transparent)',
        'text-inverse': 'color-mix(in srgb, var(--text-inverse) calc(<alpha-value> * 100%), transparent)',

        // Borders
        'border-primary': 'color-mix(in srgb, var(--border-primary) calc(<alpha-value> * 100%), transparent)',
        'border-hover': 'color-mix(in srgb, var(--border-hover) calc(<alpha-value> * 100%), transparent)',
        'border-focus': 'color-mix(in srgb, var(--border-focus) calc(<alpha-value> * 100%), transparent)',
        'border-error': 'color-mix(in srgb, var(--border-error) calc(<alpha-value> * 100%), transparent)',
        'border-success': 'color-mix(in srgb, var(--border-success) calc(<alpha-value> * 100%), transparent)',
        'border-disabled': 'color-mix(in srgb, var(--border-disabled) calc(<alpha-value> * 100%), transparent)',

        // Status
        'status-success': 'color-mix(in srgb, var(--status-success) calc(<alpha-value> * 100%), transparent)',
        'status-warning': 'color-mix(in srgb, var(--status-warning) calc(<alpha-value> * 100%), transparent)',
        'status-error': 'color-mix(in srgb, var(--status-error) calc(<alpha-value> * 100%), transparent)',
        'status-info': 'color-mix(in srgb, var(--status-info) calc(<alpha-value> * 100%), transparent)',

        // Finance specific
        'finance-income': 'color-mix(in srgb, var(--finance-income) calc(<alpha-value> * 100%), transparent)',
        'finance-expense': 'color-mix(in srgb, var(--finance-expense) calc(<alpha-value> * 100%), transparent)',
        'finance-neutral': 'color-mix(in srgb, var(--finance-neutral) calc(<alpha-value> * 100%), transparent)',

        // Charts
        'chart-primary': 'color-mix(in srgb, var(--chart-primary) calc(<alpha-value> * 100%), transparent)',
        'chart-secondary': 'color-mix(in srgb, var(--chart-secondary) calc(<alpha-value> * 100%), transparent)',
        'chart-tertiary': 'color-mix(in srgb, var(--chart-tertiary) calc(<alpha-value> * 100%), transparent)',
      },
      boxShadow: {
        'focus': 'var(--shadow-focus)',
        'elevated': 'var(--shadow-elevated)',
      },
      animation: {
        'spin': 'spin 1s linear infinite',
      },
      keyframes: {
        spin: {
          '0%': { transform: 'rotate(0deg)' },
          '100%': { transform: 'rotate(360deg)' },
        },
      },
    },
  },
  plugins: [],
} satisfies Config;

