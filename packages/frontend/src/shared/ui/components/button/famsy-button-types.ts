import type { Component } from 'vue';

export type ButtonSize = 'sm' | 'md' | 'lg' | 'xl';
export type ButtonVariant = 'primary' | 'secondary' | 'outline' | 'ghost' | 'link' | 'neutral';

export interface ButtonProps {
  size?: ButtonSize
  variant?: ButtonVariant
  iconLeft?: Component
  iconRight?: Component
  loading?: boolean
  disabled?: boolean
}
