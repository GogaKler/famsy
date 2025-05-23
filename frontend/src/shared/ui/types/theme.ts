export type ColorToken = 
  | 'action-default'
  | 'action-hover'
  | 'action-active'
  | 'action-focus'
  | 'action-disabled'
  | 'action-secondary'
  | 'action-secondary-hover'
  | 'action-secondary-active'
  | 'action-secondary-disabled'
  | 'surface-primary'
  | 'surface-secondary'
  | 'surface-tertiary'
  | 'surface-elevated'
  | 'surface-overlay'
  | 'surface-inverse'
  | 'text-primary'
  | 'text-secondary'
  | 'text-tertiary'
  | 'text-disabled'
  | 'text-inverse'
  | 'text-on-dark'
  | 'text-on-light'
  | 'border-primary'
  | 'border-hover'
  | 'border-focus'
  | 'border-error'
  | 'border-success'
  | 'border-disabled'
  | 'status-success'
  | 'status-warning'
  | 'status-error'
  | 'status-info';

export enum FamsyTheme {
  LIGHT = 'light',
  DARK = 'dark',
  CONTRAST = 'contrast',
}