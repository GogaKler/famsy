import { watch } from 'vue';
import { clearErrors, setErrors } from '@formkit/vue';
import type { BaseService } from '@shared/service';

/**
 * Хук для синхронизации ошибок между BaseService и FormKit
 */
export function useFormKitErrors<
  TEntityName extends string,
  TAction extends string,
>(
  service: BaseService<TEntityName, TAction>,
  entityId: number,
  formId: TAction,
) {
  watch(
    () => service.isLoading(entityId, formId),
    (isLoading) => {
      if (isLoading) {
        clearErrors(formId);
      }
    },
  );
  
  watch(
    [
      () => service.entityActionState.getError(entityId, formId),
      () => service.entityActionState.getFieldErrors(entityId, formId),
    ],
    ([error, fieldErrors]) => {
      if (error) {
        setErrors(formId, [error]);
      } else if (fieldErrors) {
        setErrors(formId, [], fieldErrors);
      }
    },
    { immediate: true },
  );
}