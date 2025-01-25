import type { ApiFieldErrorsResponse } from '@shared/api';
import { type ActionState, type EntityState } from './types/state-action-types';
import { defineStore } from 'pinia';


export const useEntityActionStateStore = defineStore('entityActionStateStore', {
  state: () => ({
    entities: {} as Record<string, Record<number, EntityState>>,
  }),

  getters: {
    isLoading: state => (entityName: string, id: number, action: string) => 
      state.entities[entityName]?.[id]?.actions[action]?.loading || false,

    getError: state => (entityName: string, id: number, action: string) =>
      state.entities[entityName]?.[id]?.actions[action]?.error || null,

    getFieldErrors: state => (entityName: string, id: number, action: string) =>
      state.entities[entityName]?.[id]?.actions[action]?.fieldErrors || null,

    isAnyLoading: state => (entityName: string, id: number) =>
      Object.values(state.entities[entityName]?.[id]?.actions || {})
        .some((actionState) => (actionState as ActionState).loading),
  },
  
  actions: {
    startAction(entityName: string, id: number, action: string) {
      if (!this.entities[entityName]) {
        this.entities[entityName] = {};
      }
      if (!this.entities[entityName][id]) {
        this.entities[entityName][id] = { actions: {} };
      }

      this.entities[entityName][id].actions[action] = {
        loading: true,
        error: null,
        fieldErrors: null,
        timestamp: Date.now(),
      };
    },

    finishAction(entityName: string, id: number, action: string) {
      if (this.entities[entityName]?.[id]?.actions[action]) {
        this.entities[entityName][id].actions[action]!.loading = false;
      }
    },

    setError(entityName: string, id: number, action: string, error: string) {
      if (!this.entities[entityName]?.[id]?.actions[action]) {
        this.startAction(entityName, id, action);
      }
      
      this.entities[entityName][id].actions[action]!.error = error;
    },

    setFieldErrors(entityName: string, id: number, action: string, fieldErrors: ApiFieldErrorsResponse) {
      if (!this.entities[entityName]?.[id]?.actions[action]) {
        this.startAction(entityName, id, action);
      }

      this.entities[entityName][id].actions[action]!.fieldErrors = fieldErrors;
    },

    startBulkAction(entityName: string, ids: number[], action: string) {
      ids.forEach(id => this.startAction(entityName, id, action));
    },

    finishBulkAction(entityName: string, ids: number[], action: string) {
      ids.forEach(id => this.finishAction(entityName, id, action));
    },

    setBulkError(entityName: string, ids: number[], action: string, error: string) {
      ids.forEach(id => this.setError(entityName, id, action, error));
    },

    clearState(entityName: string, id: number) {
      if (this.entities[entityName]) {
        delete this.entities[entityName][id];
      }
    },
  },
});


export function createEntityActionStateStore<
  TEntityName extends string,
  TAction extends string,
>(entityName: TEntityName) {
  const store = useEntityActionStateStore();
  
  return {
    startAction: (id: number, action: TAction) => 
      store.startAction(entityName, id, action),
      
    finishAction: (id: number, action: TAction) =>
      store.finishAction(entityName, id, action),

    getError: (id: number, action: TAction): string | null =>
      store.getError(entityName, id, action),

    getFieldErrors: (id: number, action: TAction): ApiFieldErrorsResponse | null =>
      store.getFieldErrors(entityName, id, action),
      
    setError: (id: number, action: TAction, error: string) =>
      store.setError(entityName, id, action, error),
      
    setFieldErrors: (id: number, action: TAction, fieldErrors: ApiFieldErrorsResponse) =>
      store.setFieldErrors(entityName, id, action, fieldErrors),
      
    startBulkAction: (ids: number[], action: TAction) =>
      store.startBulkAction(entityName, ids, action),
      
    finishBulkAction: (ids: number[], action: TAction) =>
      store.finishBulkAction(entityName, ids, action),
      
    setBulkError: (ids: number[], action: TAction, error: string) =>
      store.setBulkError(entityName, ids, action, error),
      
    isLoading: (id: number, action: TAction) =>
      store.isLoading(entityName, id, action),
      
    isAnyLoading: (id: number) =>
      store.isAnyLoading(entityName, id),
      
    clearState: (id: number) =>
      store.clearState(entityName, id),
  };
}