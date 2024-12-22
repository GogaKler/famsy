import { getCurrentInstance, nextTick, onMounted, ref, type Ref } from 'vue';

const tryOnMounted = (fn: () => void, sync = true): void => {
    if (getCurrentInstance()) onMounted(fn);
    else if (sync) fn();
    else nextTick(fn);
};

export const useSupported = (callback: () => unknown, sync = false): Ref<boolean> => {
    const isSupported = ref<boolean>(false);

    const update = () => (isSupported.value = Boolean(callback()));

    update();

    tryOnMounted(update, sync);
    return isSupported;
};
