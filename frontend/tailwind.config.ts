import type { Config } from 'tailwindcss';


function prepareColor(color: string) {
  return `color-mix(in srgb, var(${color}) calc(<alpha-value> * 100%), transparent)`
}

export default {
  darkMode: 'class',
  // plugins: [require('tailwindcss-primeui')],
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
    './formkit.config.ts',
  ],
  theme: {
    colors: {
      // Семантическая палитра для PRIMARY (оттенки определяются в теме)
      primary: {
        DEFAULT: prepareColor('--primary-500'),
        50: prepareColor('--primary-50'),
        100: prepareColor('--primary-100'),
        200: prepareColor('--primary-200'),
        300: prepareColor('--primary-300'),
        400: prepareColor('--primary-400'),
        500: prepareColor('--primary-500'),
        600: prepareColor('--primary-600'),
        700: prepareColor('--primary-700'),
        800: prepareColor('--primary-800'),
        900: prepareColor('--primary-900'),
        950: prepareColor('--primary-950'),
      },
      // Семантическая палитра для SECONDARY
      secondary: {
        DEFAULT: prepareColor('--secondary-500'),
        50: prepareColor('--secondary-50'),
        100: prepareColor('--secondary-100'),
        200: prepareColor('--secondary-200'),
        300: prepareColor('--secondary-300'),
        400: prepareColor('--secondary-400'),
        500: prepareColor('--secondary-500'),
        600: prepareColor('--secondary-600'),
        700: prepareColor('--secondary-700'),
        800: prepareColor('--secondary-800'),
        900: prepareColor('--secondary-900'),
        950: prepareColor('--secondary-950'),
      },
      // Семантическая палитра для TERTIARY
      tertiary: {
        DEFAULT: prepareColor('--tertiary-500'),
        50: prepareColor('--tertiary-50'),
        100: prepareColor('--tertiary-100'),
        200: prepareColor('--tertiary-200'),
        300: prepareColor('--tertiary-300'),
        400: prepareColor('--tertiary-400'),
        500: prepareColor('--tertiary-500'),
        600: prepareColor('--tertiary-600'),
        700: prepareColor('--tertiary-700'),
        800: prepareColor('--tertiary-800'),
        900: prepareColor('--tertiary-900'),
        950: prepareColor('--tertiary-950'),
      },
      surface: {
        DEFAULT: prepareColor('--surface-500'),
        50: prepareColor('--surface-50'),
        100: prepareColor('--surface-100'),
        200: prepareColor('--surface-200'),
        300: prepareColor('--surface-300'),
        400: prepareColor('--surface-400'),
        500: prepareColor('--surface-500'),
        600: prepareColor('--surface-600'),
        700: prepareColor('--surface-700'),
        800: prepareColor('--surface-800'),
        900: prepareColor('--surface-900'),
        950: prepareColor('--surface-950'),
        //
        primary: prepareColor('--surface-50'),
        secondary: prepareColor('--surface-100'),
        tertiary: prepareColor('--surface-200'),
        elevated: prepareColor('--surface-50'),
        overlay: prepareColor('--surface-900'),
        inverse: prepareColor('--surface-950'),
      },
      neutral: {
        DEFAULT: prepareColor('--neutral-500'),
        50: prepareColor('--neutral-50'),
        100: prepareColor('--neutral-100'),
        200: prepareColor('--neutral-200'),
        300: prepareColor('--neutral-300'),
        400: prepareColor('--neutral-400'),
        500: prepareColor('--neutral-500'),
        600: prepareColor('--neutral-600'),
        700: prepareColor('--neutral-700'),
        800: prepareColor('--neutral-800'),
        900: prepareColor('--neutral-900'),
        950: prepareColor('--neutral-950'),
      },
      // Токены для текста
      text: {
        primary: prepareColor('--text-900'),
        secondary: prepareColor('--text-700'),
        tertiary: prepareColor('--text-500'),
        disabled: prepareColor('--text-400'),
        inverse: prepareColor('--text-50'),
        success: prepareColor('--success-500'),
        error: prepareColor('--error-500'),
        warning: prepareColor('--warning-500'),
      },
      // Токены для бордеров
      border: {
        primary: prepareColor('--border-200'),
        hover: prepareColor('--border-300'),
        focus: prepareColor('--primary-500'),
        disabled: prepareColor('--border-200'),
        success: prepareColor('--success-500'),
        error: prepareColor('--error-500'),
        warning: prepareColor('--warning-500'),
      },
      // Токены для интерактивных элементов (Actions)
      action: {
        default: prepareColor('--primary-500'),
        hover: prepareColor('--primary-300'),
        active: prepareColor('--primary-200'),
        focus: prepareColor('--primary-700'),
        disabled: prepareColor('--neutral-700'),
        neutral: prepareColor('--neutral-500'),
        'neutral-hover': prepareColor('--neutral-300'),
        'neutral-active': prepareColor('--neutral-200'),
        'neutral-disabled': prepareColor('--neutral-700'),
        secondary: prepareColor('--secondary-500'),
        'secondary-hover': prepareColor('--secondary-300'),
        'secondary-active': prepareColor('--secondary-200'),
        'secondary-disabled': prepareColor('--secondary-700'),
      },
    },
    boxShadow: {
      focus: 'var(--shadow-focus)',
      elevated: 'var(--shadow-elevated)',
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
} satisfies Config;

