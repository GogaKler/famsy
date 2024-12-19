import { getCurrentInstance, nextTick, onMounted, ref } from 'vue';

const tryOnMounted = (fn, sync = true) => {
    if (getCurrentInstance()) onMounted(fn);
    else if (sync) fn();
    else nextTick(fn);
};

export const useSupported = (callback, sync = false) => {
    const isSupported = ref();

    const update = () => (isSupported.value = Boolean(callback()));

    update();

    tryOnMounted(update, sync);
    return isSupported;
};
