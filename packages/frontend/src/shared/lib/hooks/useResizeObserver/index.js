import { useSupported } from '@use';
import { unref, watch } from 'vue';
const defaultWindow = window;

/**
 * @param { $ref | $computed | function } elRef Элемент, который проходит проверку
 *
 * Всего лишь синтаксический сахар для проверки элемента
 * */
const unrefElement = (elRef) => {
    const plain = typeof elRef === 'function' ? elRef() : unref(elRef);
    return plain?.$el ?? plain;
};

/**
 * @param { $ref | $computed | function } element Элемент за которым нужно следить
 * @param {function} callback Callback функция которая будет выполняться при изменении element
 * @param {Object} options Дополнительные настройки для ResizeObserver
 * */

export const useResizeObserver = (element, callback, options = {}) => {
    const { ...observerOptions } = options;
    let observer;
    const isSupported = useSupported(() => defaultWindow && 'ResizeObserver' in defaultWindow);

    const cleanup = () => {
        if (observer) {
            observer.disconnect();
            observer = undefined;
        }
    };

    const stopWatch = watch(
        () => unrefElement(element),
        (el) => {
            cleanup();

            if (isSupported.value && window && el) {
                observer = new ResizeObserver((entries) => {
                    const entry = entries[0];
                    const { offsetWidth: width, offsetHeight: height } = entry.target;

                    callback({ width, height, entry });
                });
                observer.observe(el, observerOptions);
            }
        },
        { immediate: true, flush: 'post' }
    );

    const stop = () => {
        cleanup();
        stopWatch();
    };

    return {
        isSupported,
        stop,
        observer
    };
};
