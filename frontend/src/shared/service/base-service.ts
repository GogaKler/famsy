import { ApiError, ApiFieldError } from '@shared/api';
import { type BaseEntityAction } from '@shared/entity';
import { createEntityActionStateStore } from '@shared/store';

export abstract class BaseService<
  TEntityName extends string,
  TAction extends string = BaseEntityAction,
> {
  protected readonly entityActionStateStore: ReturnType<typeof createEntityActionStateStore<TEntityName, TAction>>;

  protected constructor(
    protected readonly entityName: TEntityName,
  ) {
    this.entityActionStateStore = createEntityActionStateStore<TEntityName, TAction>(entityName);
  }

  protected async executeAction<T>(
    entityId: number,
    action: TAction,
    operation: () => Promise<T>,
  ): Promise<T> {
    this.entityActionStateStore.startAction(entityId, action);

    try {
      return await operation();
    } catch (error: unknown) {
      if (error instanceof ApiFieldError) {
        this.entityActionStateStore.setFieldErrors(
          entityId,
          action,
          error.fields,
        );
      } else if (error instanceof ApiError) {
        this.entityActionStateStore.setError(
          entityId,
          action,
          error.message,
        );
      } else {
        this.entityActionStateStore.setError(
          entityId,
          action,
          error instanceof Error ? error.message : String(error),
        );
      }
      return Promise.reject(error);
    } finally {
      this.entityActionStateStore.finishAction(entityId, action);
    }
  }

  protected async executeBulkAction<T>(
    entityIds: number[],
    action: TAction,
    operation: () => Promise<T>,
  ): Promise<T> {
    this.entityActionStateStore.startBulkAction(entityIds, action);

    try {
      const result = await operation();
      this.entityActionStateStore.finishBulkAction(entityIds, action);
      return result;
    } catch (error: unknown) {
      this.entityActionStateStore.setBulkError(
        entityIds,
        action,
        error instanceof Error ? error.message : String(error),
      );
      throw error;
    }
  }

  get entityActionState() {
    return this.entityActionStateStore;
  }

  isLoading(id: number, action: TAction): boolean {
    return this.entityActionStateStore.isLoading(id, action);
  }

  getError(id: number, action: TAction): string | null {
    return this.entityActionStateStore.getError(id, action);
  }

  isAnyLoading(id: number): boolean {
    return this.entityActionStateStore.isAnyLoading(id);
  }
}