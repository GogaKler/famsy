import type { ApiFieldErrorsResponse } from '@shared/api';

export interface ActionState {
  loading: boolean;
  error: string | null;
  fieldErrors: ApiFieldErrorsResponse | null;
  timestamp: number;
  meta?: Record<string, unknown>;
}

export interface EntityState<TAction extends string = string> {
  actions: Partial<Record<TAction, ActionState>>;
}