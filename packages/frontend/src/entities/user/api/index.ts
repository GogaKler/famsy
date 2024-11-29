import type { DeepReadonly } from 'vue';
import { reactive, readonly, type ToRefs, toRefs } from 'vue';
import { httpClient } from '@shared/api';


interface IFetchState {
  data: undefined
  error: unknown
  isLoading: boolean
  isError: boolean
  reset: () => void
}

const fetchState: IFetchState = {
  data: undefined,
  error: '',
  isLoading: false,
  isError: false,
  reset: () => {},
};

function useApiFetch(asyncCallback: () => Promise<any>): ToRefs<DeepReadonly<IFetchState>> {
  const state = reactive<IFetchState>(fetchState);

  const call = async (): Promise<void> => {
    try {
      state.isLoading = true;
      const response = await asyncCallback();
      if (response.error) {
        throw new Error(response.error.message);
      }
      state.data = response;
    } catch (e: unknown) {
      state.isError = true;
      state.error = e;
    } finally {
      state.isLoading = false;
    }
  };
  
  state.reset = call;
  
  void call();
  
  return toRefs(readonly(state));
}

// function useApiMutation(asyncCallback: () => Promise<any>): ToRefs<DeepReadonly<IFetchState>> {
//   const state = reactive<IFetchState>(fetchState);
//
//   const call = async (): Promise<void> => {
//     try {
//       state.isLoading = true;
//       const response = await asyncCallback();
//       if (response.error) {
//         throw new Error(response.error.message);
//       }
//       state.data = response;
//     } catch (e: unknown) {
//       state.isError = true;
//       state.error = e;
//     } finally {
//       state.isLoading = false;
//     }
//   };
//
//   state.reset = call;
//
//   void call();
//
//   return toRefs(readonly(state));
// }

export function useGetUsers() {
  return useApiFetch(async () => await httpClient.get('/api/users', {}));
}

export function useGetMe() {
  return useApiFetch(async () => await httpClient.get('/api/auth/me', {}));
}