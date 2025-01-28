<script setup lang="ts">
import { computed, ref } from 'vue';
import { type ButtonProps } from './famsy-button-types';
import { FamsySpinnerIcon } from '@shared/ui';

withDefaults(defineProps<ButtonProps>(), {
  size: 'md',
  variant: 'primary',
  loading: false,
  disabled: false,
});

const isHovered = ref(false);
const focused = ref(false);

const sizeClasses = {
  sm: 'px-3 py-3 text-sm',
  md: 'px-4 py-3.5 text-base',
  lg: 'px-5 py-4 text-lg',
  xl: 'px-6 py-5 text-xl',
};

const iconSizeClasses = {
  sm: 'w-4 h-4',
  md: 'w-5 h-5',
  lg: 'w-6 h-6',
  xl: 'w-7 h-7',
};

const variantClasses = computed(() => ({
  primary: `
    bg-action-default
    text-text-inverse
    hover:bg-action-hover
    active:bg-action-active
    disabled:bg-action-disabled
  `,
  secondary: `
    bg-action-secondary
    text-text-inverse
    hover:bg-action-secondary-hover
    active:bg-action-secondary-active
    disabled:bg-action-secondary-disabled
  `,
  neutral: `
    bg-action-neutral
    text-text-inverse
    hover:bg-action-neutral-hover
    active:bg-action-neutral-active
    disabled:bg-action-neutral-disabled
  `,
  outline: `
    border border-solid border-action-default/40
    text-action-default
    hover:bg-action-default
    hover:text-text-inverse
    active:bg-action-active
    active:border-action-active
    disabled:border-action-disabled
    disabled:text-action-disabled
  `,
  ghost: `
    text-action-default
    hover:bg-action-default/10
    active:bg-action-active/20
    disabled:text-action-disabled
  `,
  link: `
    text-action-default
    underline
    hover:text-action-hover
    active:text-action-active
    disabled:text-action-disabled
  `,
}));
</script>


<template>
  <button
    :class="[
      'relative inline-flex items-center justify-center rounded-2xl font-medium',
      sizeClasses[size],
      variantClasses[variant],
      {
        'opacity-50 cursor-not-allowed': disabled,
        'ring-2 ring-action-focus': focused,
      }
    ]"
    :disabled="disabled"
    @mouseenter="isHovered = true"
    @mouseleave="isHovered = false"
    @focus="focused = true"
    @blur="focused = false"
  >
    <slot name="icon-left">
      <component 
        :is="iconLeft" 
        v-if="iconLeft" 
        :class="iconSizeClasses[size]"
      />
    </slot>

    <span
      :class="[
        { 'ml-2': iconLeft },
        { 'mr-2': iconRight }
      ]"
    >
      <slot />
    </span>

    <slot name="icon-right">
      <component 
        :is="iconRight" 
        v-if="iconRight" 
        :class="iconSizeClasses[size]"
      />
    </slot>

    <span
      v-if="loading"
      class="absolute inset-0 flex items-center justify-center bg-inherit rounded-lg"
    >
      <FamsySpinnerIcon :class="iconSizeClasses[size]" />
    </span>
  </button>
</template>