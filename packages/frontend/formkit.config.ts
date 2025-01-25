import { defaultConfig } from '@formkit/vue';
import { generateClasses } from '@formkit/themes';
import { ru } from '@formkit/i18n';
import { genesisIcons } from '@formkit/icons';

export default defaultConfig({
  locales: { ru },
  locale: 'ru',
  icons: {
    ...genesisIcons,
  },
  config: {
    classes: generateClasses({
      global: {
        outer: '',
        wrapper: '',
        label: 'text-base block text-text-primary font-medium',
        inner: 'relative',
        help: 'text-text-tertiary text-xs',
        messages: 'list-none text-center',
        message: 'text-status-error text-xs',
        prefixIcon: 'absolute left-3 top-1/2 -translate-y-1/2 text-text-tertiary',
        suffixIcon: 'absolute right-3 top-1/2 -translate-y-1/2 text-text-tertiary',
        prefix: 'absolute left-3 top-1/2 -translate-y-1/2',
        suffix: 'absolute right-3 top-1/2 -translate-y-1/2',
        input: `
          bg-surface-primary rounded-lg
          border border-border-primary
          shadow-sm transition-all duration-200
          hover:border-border-hover
          focus:border-action-focus focus:ring-1 focus:ring-action-focus focus:outline-none
          disabled:bg-surface-secondary disabled:cursor-not-allowed disabled:border-border-disabled
          placeholder:text-text-tertiary placeholder:text-base
          text-base
          touch-manipulation
        `,
      },
      text: {
        input: 'w-full px-3 py-3',
        label: 'mb-1.5',
      },
      email: {
        input: 'w-full px-3 py-3',
        label: 'mb-1.5',
      },
      password: {
        input: 'w-full px-3 py-3',
        label: 'mb-1.5',
      },
      textarea: {
        input: 'w-full px-3 py-2.5 min-h-[120px] resize-y',
        label: 'mb-1.5',
      },
      submit: {
        input: `
          bg-action-default text-text-inverse w-full px-6 py-2.5 rounded-lg
          text-base font-medium
          hover:bg-action-hover active:bg-action-active
          disabled:bg-action-disabled disabled:cursor-not-allowed
          focus:ring-2 focus:ring-action-focus/25 focus:outline-none
          shadow-sm hover:shadow transition-all duration-200
        `,
      },
      checkbox: {
        wrapper: 'flex items-center gap-2 min-h-[40px] sm:min-h-[auto] cursor-pointer',
        inner: 'relative inline-flex',
        label: 'text-text-primary text-base select-none cursor-pointer mb-0',
        help: 'text-text-tertiary text-xs mt-1',
        input: `
          px-2 py-0.5
          appearance-none 
          w-[18px] h-[18px] shrink-0
          rounded-md
          bg-surface-primary
          border border-border-primary
          hover:border-border-hover
          focus:border-action-focus focus:ring-1 focus:ring-action-focus focus:outline-none
          disabled:bg-surface-secondary disabled:cursor-not-allowed
          checked:bg-action-default checked:border-action-default
          checked:hover:bg-action-hover
          transition-all duration-200
          [&:checked~.formkit-decorator_.formkit-decorator-icon]:visible
        `,
        decorator: 'absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2',
        decoratorIcon: `
          w-3 h-3 text-white invisible transition-opacity duration-200
          [&>svg]:w-full [&>svg]:h-full
        `,
      },
      radio: {
        wrapper: 'flex items-center gap-2 min-h-[40px] sm:min-h-[auto] cursor-pointer',
        inner: 'relative inline-flex',
        label: 'text-text-primary text-base select-none cursor-pointer ml-2',
        help: 'text-text-tertiary text-xs mt-1 ml-7',
        options: 'flex flex-col gap-2',
        input: `
          appearance-none relative shrink-0
          w-[18px] h-[18px] rounded-full
          bg-surface-primary
          border border-border-primary
          hover:border-border-hover
          focus:border-action-focus focus:ring-1 focus:ring-action-focus focus:outline-none
          disabled:bg-surface-secondary disabled:cursor-not-allowed
          checked:bg-action-default checked:border-action-default
          checked:hover:bg-action-hover
          transition-all duration-200
        `,
      },
      select: {
        inner: 'relative',
        input: `
          appearance-none cursor-pointer
          pr-12 py-3 sm:py-2.5
          min-h-[44px] sm:min-h-[auto]
        `,
        selectIcon: 'pointer-events-none w-5 h-5',
        option: 'text-text-primary',
      },
      number: {
        input: `
          [appearance:textfield]
          [&::-webkit-outer-spin-button]:appearance-none
          [&::-webkit-inner-spin-button]:appearance-none
        `,
      },
      date: {
        inner: 'relative flex items-center border border-border-primary rounded-lg overflow-hidden focus-within:border-action-focus focus-within:ring-1 focus-within:ring-action-focus',
        input: 'border-none focus:outline-none cursor-pointer',
      },
      search: {
        inner: 'relative flex items-center border border-border-primary rounded-lg overflow-hidden focus-within:border-action-focus focus-within:ring-1 focus-within:ring-action-focus',
        input: 'border-none focus:outline-none pl-10',
      },
    }),
  },
});